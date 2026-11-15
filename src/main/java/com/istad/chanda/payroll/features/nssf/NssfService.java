package com.istad.chanda.payroll.features.nssf;

import com.istad.chanda.payroll.features.nssf.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface NssfService {
    NssfResponse findById(String id);
    Page<NssfResponse> findAll(Pageable pageable);
    List<NssfResponse> search(String name);
    NssfResponse createNew(CreateNssfRequest request);
    NssfResponse updateById(String id, UpdateNssfRequest request);
    void deleteById(String id);
}
