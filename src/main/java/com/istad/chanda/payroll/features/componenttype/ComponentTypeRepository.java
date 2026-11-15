package com.istad.chanda.payroll.features.componenttype;

import com.istad.chanda.payroll.common.CrudRepository;

public interface ComponentTypeRepository extends CrudRepository<ComponentType> {
    java.util.List<ComponentType> findByNameContainingIgnoreCase(String term0);
}
