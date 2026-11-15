package com.istad.chanda.payroll.features.seniority;

import com.istad.chanda.payroll.common.CrudRepository;

public interface SeniorityRepository extends CrudRepository<Seniority> {
    java.util.List<Seniority> findByNameContainingIgnoreCase(String term0);
}
