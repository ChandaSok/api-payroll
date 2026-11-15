package com.istad.chanda.payroll;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@WithMockUser(roles = "PAYROLL_ADMIN")
class PayrollApiTests {
    @Autowired WebApplicationContext context;
    @org.springframework.test.context.bean.override.mockito.MockitoBean
    org.springframework.security.oauth2.jwt.JwtDecoder jwtDecoder;
    MockMvc mvc;
    String employeeId;
    String componentId;
    String runId;
    static final String EMPLOYEE = """
            {"employeeCode":"EMP-001","firstName":"Sok","lastName":"Chan",
            "email":"sok@example.com","department":"Engineering","jobTitle":"Developer",
            "hireDate":"2026-01-01","active":true}
            """;
    static final String COMPONENT = """
            {"code":"BONUS","name":"Bonus","kind":"EARNING","taxable":true,"active":true}
            """;
    static final String RUN = """
            {"name":"September payroll","periodStart":"2026-09-01","periodEnd":"2026-09-30",
            "paymentDate":"2026-09-30","currency":"USD"}
            """;

    @BeforeEach
    void setup() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        employeeId = create("employees", EMPLOYEE);
        componentId = create("component-types", COMPONENT);
        runId = create("payroll-runs", RUN);
    }

    String create(String route, String body) throws Exception {
        MvcResult result = mvc.perform(post("/api/v1/" + route)
                        .contentType("application/json").content(body))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNotEmpty()).andReturn();
        return JsonPath.read(result.getResponse().getContentAsString(), "$.id");
    }

    String body(String route) {
        return switch (route) {
            case "employees" -> EMPLOYEE.replace("EMP-001", "EMP-002").replace("sok@example.com", "other@example.com");
            case "component-types" -> COMPONENT.replace("BONUS", "ALLOWANCE");
            case "salary-taxes" -> """
                    {"name":"Configured bracket","currency":"USD","lowerBound":0,"upperBound":1000,
                    "rate":0.1,"deduction":0,"effectiveFrom":"2026-01-01"}
                    """;
            case "nssf" -> """
                    {"name":"Configured contributions","currency":"USD","minimumBase":0,"maximumBase":1000,
                    "employeeRate":0.02,"employerRate":0.03,"effectiveFrom":"2026-01-01"}
                    """;
            case "seniorities" -> """
                    {"name":"Configured seniority","minimumServiceMonths":6,"daysPerYear":15,
                    "effectiveFrom":"2026-01-01","active":true}
                    """;
            case "exchange-rates" -> """
                    {"baseCurrency":"USD","quoteCurrency":"KHR","rate":4000,"effectiveDate":"2026-09-01"}
                    """;
            case "payroll-setups" -> """
                    {"employeeId":"%s","baseSalary":1000,"currency":"USD","standardDaysPerMonth":22,
                    "effectiveFrom":"2026-01-01","active":true}
                    """.formatted(employeeId);
            case "time-attendances" -> """
                    {"employeeId":"%s","workDate":"2026-09-01","workedHours":8,"overtimeHours":2,"status":"PRESENT"}
                    """.formatted(employeeId);
            case "pay-adjustments" -> """
                    {"employeeId":"%s","componentTypeId":"%s","amount":100,"currency":"USD",
                    "effectiveDate":"2026-09-01","reason":"Performance bonus"}
                    """.formatted(employeeId, componentId);
            case "payroll-runs" -> RUN;
            case "payslips" -> """
                    {"employeeId":"%s","payrollRunId":"%s","baseSalary":1000,"additionalEarnings":100,
                    "seniorityAmount":50,"otherDeductions":20,"salaryTax":30,"employeeNssf":10,"employerNssf":15}
                    """.formatted(employeeId, runId);
            default -> throw new IllegalArgumentException(route);
        };
    }

    @ParameterizedTest
    @ValueSource(strings = {"employees", "component-types", "salary-taxes", "nssf", "seniorities",
            "exchange-rates", "payroll-setups", "time-attendances", "pay-adjustments", "payroll-runs", "payslips"})
    void supportsCrudForEveryModule(String route) throws Exception {
        String payload = body(route);
        String id = create(route, payload);
        String url = "/api/v1/" + route + "/" + id;
        mvc.perform(get(url)).andExpect(status().isOk()).andExpect(jsonPath("$.createdAt").isNotEmpty());
        mvc.perform(get("/api/v1/" + route).param("pageSize", "10"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.content").isArray());
        mvc.perform(put(url).contentType("application/json").content(payload))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id));
        mvc.perform(get("/api/v1/" + route + "/search").param("name", ""))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").isNotEmpty());
        mvc.perform(get("/api/v1/" + route + "/search").param("name", "no-match-xyz"))
                .andExpect(status().isOk()).andExpect(jsonPath("$").isEmpty());
        mvc.perform(delete(url)).andExpect(status().isNoContent());
        mvc.perform(get(url)).andExpect(status().isNotFound());
    }

    @Test
    void searchesEmployeesCaseInsensitivelyAndUsesRequestedPagination() throws Exception {
        mvc.perform(get("/api/v1/employees/search").param("name", "sOK"))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(employeeId));
        mvc.perform(get("/api/v1/employees").param("pageNumber", "1").param("pageSize", "1"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.content").isEmpty());
        mvc.perform(put("/api/v1/employees/" + employeeId).contentType("application/json").content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsInvalidPayloadAndPagination() throws Exception {
        mvc.perform(post("/api/v1/employees").contentType("application/json").content("{}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors").isArray());
        mvc.perform(get("/api/v1/employees").param("pageSize", "101")).andExpect(status().isBadRequest());
        mvc.perform(get("/api/v1/employees").param("pageNumber", "-1")).andExpect(status().isBadRequest());
        mvc.perform(post("/api/v1/salary-taxes").contentType("application/json")
                .content(body("salary-taxes").replace("\"upperBound\":1000", "\"upperBound\":0")))
                .andExpect(status().isBadRequest());
        mvc.perform(post("/api/v1/payroll-runs").contentType("application/json")
                .content(RUN.replace("2026-09-30", "2026-08-30"))).andExpect(status().isBadRequest());
        mvc.perform(post("/api/v1/time-attendances").contentType("application/json")
                .content(body("time-attendances").replace("\"workedHours\":8", "\"workedHours\":24")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsUnknownEmployeeReference() throws Exception {
        mvc.perform(post("/api/v1/payroll-setups").contentType("application/json")
                .content(body("payroll-setups").replace(employeeId, "missing"))).andExpect(status().isNotFound());
    }

    @Test
    void rejectsDuplicateEmployee() throws Exception {
        mvc.perform(post("/api/v1/employees").contentType("application/json").content(EMPLOYEE))
                .andExpect(status().isConflict());
    }

    @Test
    void cannotDeleteReferencedEmployee() throws Exception {
        create("payroll-setups", body("payroll-setups"));
        mvc.perform(delete("/api/v1/employees/" + employeeId)).andExpect(status().isConflict());
    }

    @Test
    void finalizationFreezesPayslipsAndRun() throws Exception {
        String slipId = create("payslips", body("payslips"));
        String slipUrl = "/api/v1/payslips/" + slipId;
        String runUrl = "/api/v1/payroll-runs/" + runId;
        mvc.perform(get(slipUrl)).andExpect(jsonPath("$.grossPay").value(1150))
                .andExpect(jsonPath("$.netPay").value(1090)).andExpect(jsonPath("$.employeeCode").value("EMP-001"));
        mvc.perform(post(runUrl + "/finalize")).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("FINALIZED"));
        mvc.perform(put(slipUrl).contentType("application/json").content(body("payslips")))
                .andExpect(status().isConflict());
        mvc.perform(delete(slipUrl)).andExpect(status().isConflict());
        mvc.perform(put(runUrl).contentType("application/json").content(RUN))
                .andExpect(status().isConflict());
        mvc.perform(delete(runUrl)).andExpect(status().isConflict());
        mvc.perform(post(runUrl + "/finalize")).andExpect(status().isConflict());
        mvc.perform(post("/api/v1/payslips").contentType("application/json").content(body("payslips")))
                .andExpect(status().isConflict());
    }

    @Test
    void cannotFinalizeEmptyRunOrChangeRunWithPayslips() throws Exception {
        mvc.perform(post("/api/v1/payroll-runs/" + runId + "/finalize"))
                .andExpect(status().isConflict());
        create("payslips", body("payslips"));
        mvc.perform(put("/api/v1/payroll-runs/" + runId).contentType("application/json").content(RUN))
                .andExpect(status().isConflict());
    }

    @Test
    void rejectsNegativeNetPay() throws Exception {
        mvc.perform(post("/api/v1/payslips").contentType("application/json")
                .content(body("payslips").replace("\"otherDeductions\":20", "\"otherDeductions\":2000")))
                .andExpect(status().isConflict());
    }

    @Test
    void rejectsMissingBearerAndBasicAuthentication() throws Exception {
        mvc.perform(get("/api/v1/employees").with(anonymous())).andExpect(status().isUnauthorized());
        mvc.perform(get("/api/v1/employees").with(anonymous()).with(httpBasic("payroll-admin", "password")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticatesKeycloakRealmRoleWithoutCsrf() throws Exception {
        var token = org.springframework.security.oauth2.jwt.Jwt.withTokenValue("admin-token")
                .header("alg", "RS256").subject("keycloak-user-id")
                .claim("realm_access", java.util.Map.of("roles", java.util.List.of("PAYROLL_ADMIN"))).build();
        org.mockito.Mockito.when(jwtDecoder.decode("admin-token")).thenReturn(token);
        mvc.perform(get("/api/v1/employees").with(anonymous()).header("Authorization", "Bearer admin-token"))
                .andExpect(status().isOk());
        mvc.perform(post("/api/v1/employees").with(anonymous()).header("Authorization", "Bearer admin-token")
                .contentType("application/json").content(body("employees")))
                .andExpect(status().isCreated());
    }

    @Test
    void rejectsTokenWithoutPayrollRole() throws Exception {
        var token = org.springframework.security.oauth2.jwt.Jwt.withTokenValue("user-token")
                .header("alg", "RS256").subject("ordinary-user")
                .claim("realm_access", java.util.Map.of("roles", java.util.List.of("USER"))).build();
        org.mockito.Mockito.when(jwtDecoder.decode("user-token")).thenReturn(token);
        mvc.perform(get("/api/v1/employees").with(anonymous()).header("Authorization", "Bearer user-token"))
                .andExpect(status().isForbidden());
    }

    @Test
    void rejectsInvalidBearerToken() throws Exception {
        org.mockito.Mockito.when(jwtDecoder.decode("invalid-token"))
                .thenThrow(new org.springframework.security.oauth2.jwt.BadJwtException("Invalid token"));
        mvc.perform(get("/api/v1/employees").with(anonymous()).header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized());
    }
}
