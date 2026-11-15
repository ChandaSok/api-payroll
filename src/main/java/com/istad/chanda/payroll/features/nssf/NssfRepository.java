package com.istad.chanda.payroll.features.nssf;

import com.istad.chanda.payroll.common.CrudRepository;

public interface NssfRepository extends CrudRepository<Nssf> {
    java.util.List<Nssf> findByNameContainingIgnoreCase(String term0);
}
