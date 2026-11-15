package com.istad.chanda.payroll.features.payadjustment;

import com.istad.chanda.payroll.common.CrudRepository;

public interface PayAdjustmentRepository extends CrudRepository<PayAdjustment> {
    java.util.List<PayAdjustment> findByReasonContainingIgnoreCaseOrEmployee_FirstNameContainingIgnoreCaseOrEmployee_LastNameContainingIgnoreCase(String term0, String term1, String term2);
}
