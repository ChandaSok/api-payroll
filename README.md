# Payroll API

Spring Boot 4 / Java 26 / PostgreSQL backend with feature packages under
`src/main/java/com/istad/chanda/payroll/features`.

## Run locally

1. Start PostgreSQL using the existing `../deploy-payroll/docker-compose.yaml` setup.
   The application connects to `localhost:1303/payroll_db` as configured in
   `application.properties`. Existing database settings and HTTP port `1305` are retained.
2. Run `./gradlew bootRun` with Java 26 available. No local API username or password is required.
3. Start Keycloak and use the existing `ecommerce` realm at
   `http://localhost:1304/realms/ecommerce`. Create the realm if it does not exist, create
   the realm role `PAYROLL_ADMIN`, and assign it to the users who manage payroll.
4. Obtain a Keycloak access token through your client's login flow. Its
   `realm_access.roles` claim must contain `PAYROLL_ADMIN` (realm role, not client role).
5. Open `/swagger-ui.html` on `http://localhost:1305`, click Authorize, and paste the access
   token. API documentation is public; payroll endpoints require an authorized bearer token.

Authentication is Keycloak-only and stateless. Local users, HTTP Basic, login sessions,
and the former CSRF endpoint are removed. Send `Authorization: Bearer <access-token>` on
all API requests; no CSRF cookie/header is needed. JWT signatures, expiry, and issuer are
validated by Spring Security. Missing/invalid tokens return 401; a valid token without
`PAYROLL_ADMIN` returns 403.

The issuer and JWK URL default to the existing Compose realm. Override both when using
another realm or host:

```sh
export KEYCLOAK_ISSUER_URI=http://localhost:1304/realms/ecommerce
export KEYCLOAK_JWK_SET_URI=http://localhost:1304/realms/ecommerce/protocol/openid-connect/certs
```

The issuer must exactly match the token's `iss` claim. For a containerized API, Compose
uses `keycloak-payroll:8080` for fetching signing keys and the external URL for the issuer.
An explicit JWK URL permits API startup without Keycloak discovery; Keycloak must be
reachable when signing keys are fetched to validate tokens. This change does not create
Keycloak realms, users, clients, or roles automatically.

After setting `KEYCLOAK_ACCESS_TOKEN` to an access token from your client:

```sh
curl --fail -H "Authorization: Bearer $KEYCLOAK_ACCESS_TOKEN" \
  -H 'Content-Type: application/json' \
  -d '{"employeeCode":"EMP-001","firstName":"Sok","lastName":"Chan","email":"sok@example.com","department":"Engineering","jobTitle":"Developer","hireDate":"2026-01-01","active":true}' \
  http://localhost:1305/api/v1/employees
```

## Modules

Each resource supports `POST /api/v1/{resource}`, `GET /api/v1/{resource}`,
`GET /api/v1/{resource}/{id}`, `PUT /api/v1/{resource}/{id}`, and
`DELETE /api/v1/{resource}/{id}`. PUT replaces the editable fields; it is not a partial update.
POST returns 201, successful deletion returns 204, and reads/updates return 200.
IDs are generated UUID strings. Responses include `id`, `createdAt`, and `updatedAt`.

| Module | Resource | Request fields |
| --- | --- | --- |
| Employee | `employees` | employeeCode, firstName, lastName, email, department, jobTitle, hireDate, terminationDate?, active |
| Component Type | `component-types` | code, name, kind (EARNING/DEDUCTION), taxable, active |
| Salary Tax | `salary-taxes` | name, currency, lowerBound, upperBound?, rate, deduction, effectiveFrom, effectiveTo? |
| NSSF | `nssf` | name, currency, minimumBase, maximumBase, employeeRate, employerRate, effectiveFrom, effectiveTo? |
| Seniority | `seniorities` | name, minimumServiceMonths, daysPerYear, effectiveFrom, effectiveTo?, active |
| Exchange Rate | `exchange-rates` | baseCurrency, quoteCurrency, rate, effectiveDate |
| Payroll Setup | `payroll-setups` | employeeId, baseSalary, currency, standardDaysPerMonth, effectiveFrom, effectiveTo?, active |
| Time Attendance | `time-attendances` | employeeId, workDate, workedHours, overtimeHours, status |
| Pay Adjustment | `pay-adjustments` | employeeId, componentTypeId, amount, currency, effectiveDate, reason |
| Payroll Run | `payroll-runs` | name, periodStart, periodEnd, paymentDate, currency |
| Payslip | `payslips` | employeeId, payrollRunId, baseSalary, additionalEarnings, seniorityAmount, otherDeductions, salaryTax, employeeNssf, employerNssf |

`?` means optional. The old `/api/v1/component_type` route remains an alias.
Attendance statuses are PRESENT, ABSENT, PAID_LEAVE, UNPAID_LEAVE, and HOLIDAY.
`workedHours` excludes `overtimeHours`; the sum cannot exceed 24. Non-present statuses
must have zero hours. Monetary inputs are nonnegative and support up to two decimal places.
Rates for tax/NSSF are fractions from 0 to 1 (for example `0.02` means 2%); exchange
rates are positive with up to eight decimal places. Currencies are three uppercase letters.
Dates use `YYYY-MM-DD`; range end dates cannot precede their start dates.

Lists accept zero-based `pageNumber` (default 0) and `pageSize` (default 25, maximum 100),
sorted by ID descending. Controllers return Spring Data `Page<Response>` objects. Response shape:

```json
{"content":[],"number":0,"size":25,"totalElements":0,"totalPages":0}
```

Invalid inputs return 400 with a Problem Detail body and field errors. Missing records
or referenced IDs return 404. Duplicate records, invalid workflow transitions, and
attempts to delete referenced records return 409. Authentication failures return 401;
missing roles return 403.

Database foreign keys preserve employee, component, and run references. Unique constraints
cover employee codes/emails, component codes, employee/date attendance, employee/setup start
dates, currency pair/date exchange rates, and employee/run payslips. There are no cascading
employee deletions. Use `active: false` to retain historical employee references.

## Payroll run workflow

1. Create employees, components, and payroll configuration records. Configuration is managed
   independently; no jurisdiction-specific tax, NSSF, seniority, attendance-proration, or
   currency-conversion formula is assumed. Effective-date records are stored but overlapping
   policies are not resolved automatically.
2. Create a payroll run with its dates and currency. Its status starts as `DRAFT` and is
   controlled by the server.
3. Submit one payslip per employee/run using explicitly calculated amounts in the run's
   currency. All payslip monetary fields are required; send zero where an item does not apply.
   No automatic employee selection, adjustment consumption, or bulk payslip generation occurs.
4. The API computes `grossPay = baseSalary + additionalEarnings + seniorityAmount` and
   `netPay = grossPay - otherDeductions - salaryTax - employeeNssf`. Employer NSSF is tracked
   separately and does not reduce employee net pay. Negative net pay is rejected. Employee
   code and name are copied onto the payslip to preserve its identity snapshot.
5. Inspect the draft payslips, then send `POST /api/v1/payroll-runs/{id}/finalize` with no body.
   A run must contain at least one payslip. Finalization marks it `FINALIZED` and prevents
   further run or payslip writes. Database row locks serialize finalization and payslip changes.
   Finalization records approval of the supplied figures; it does not certify statutory
   calculations or perform a payment.

Draft payslips can be updated/deleted. They cannot move to another run. Remove a run's draft
payslips before changing its dates/currency or deleting it. Finalized runs cannot be reopened
or deleted through these APIs. Payslips are JSON resources; PDF export is not included.

Example payslip request (replace IDs with values returned by creation):

```json
{
  "employeeId": "employee-uuid",
  "payrollRunId": "run-uuid",
  "baseSalary": 1000,
  "additionalEarnings": 100,
  "seniorityAmount": 50,
  "otherDeductions": 20,
  "salaryTax": 30,
  "employeeNssf": 10,
  "employerNssf": 15
}
```

This produces gross pay 1150 and net pay 1090. The amounts are illustrative API inputs,
not statutory rates or calculation guidance.

## Verification and schema

Run `./gradlew test`. Tests use isolated H2 in PostgreSQL compatibility mode with schema
creation/drop; they do not use the development PostgreSQL database. Coverage includes all
11 CRUD routes, validation, references, duplicates, Keycloak bearer authentication and role mapping, payslip arithmetic,
and finalization. PostgreSQL-specific concurrency behavior is not covered by H2 tests.

Development retains the project's existing Hibernate `ddl-auto=update` setting. Existing
populated `component_type` tables need a planned data migration to populate the new required
fields before using this schema. No production database migration is applied automatically
by these changes beyond that existing Hibernate configuration.

## Feature structure

Every module has its own entity, repository, mapper, service interface, `ServiceImpl`,
and explicit controller methods (`findById`, `deleteById`, `updateById`, `createNew`,
`findAll`, `search`). Each DTO directory contains `Create<Module>Request`,
`Update<Module>Request`, and `<Module>Response`. Create and update currently require the
same fields; PUT continues to replace all editable fields. Existing UUID IDs are preserved.

All modules expose `GET /api/v1/{resource}/search?name=...`, returning a list with
case-insensitive substring matches. An empty name returns all matching records.
Named configurations search their name; employees search first name, last name, and code;
exchange rates search currency codes; payroll setups/attendance search the linked
employee's name/code; adjustments search reason and employee name; payslips search
snapshot employee name/code. Search results are unpaginated, as in the controller example.
