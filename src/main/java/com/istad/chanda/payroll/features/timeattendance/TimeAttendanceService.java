package com.istad.chanda.payroll.features.timeattendance;

import com.istad.chanda.payroll.features.timeattendance.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TimeAttendanceService {
    TimeAttendanceResponse findById(String id);
    Page<TimeAttendanceResponse> findAll(Pageable pageable);
    List<TimeAttendanceResponse> search(String name);
    TimeAttendanceResponse createNew(CreateTimeAttendanceRequest request);
    TimeAttendanceResponse updateById(String id, UpdateTimeAttendanceRequest request);
    void deleteById(String id);
}
