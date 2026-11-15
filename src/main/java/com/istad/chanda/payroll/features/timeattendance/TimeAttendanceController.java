package com.istad.chanda.payroll.features.timeattendance;

import com.istad.chanda.payroll.features.timeattendance.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/time-attendances")
@RequiredArgsConstructor
public class TimeAttendanceController {
    private final TimeAttendanceService timeAttendanceService;

    @GetMapping("/{id}")
    public TimeAttendanceResponse findById(@PathVariable String id) { return timeAttendanceService.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) { timeAttendanceService.deleteById(id); }

    @PutMapping("/{id}")
    public TimeAttendanceResponse updateById(@PathVariable String id, @Valid @RequestBody UpdateTimeAttendanceRequest request) {
        return timeAttendanceService.updateById(id, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimeAttendanceResponse createNew(@Valid @RequestBody CreateTimeAttendanceRequest request) {
        return timeAttendanceService.createNew(request);
    }

    @GetMapping
    public Page<TimeAttendanceResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize) {
        if (pageNumber < 0 || pageSize < 1 || pageSize > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pageNumber must be nonnegative and pageSize must be between 1 and 100");
        return timeAttendanceService.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
    }

    @GetMapping("/search")
    public List<TimeAttendanceResponse> search(@RequestParam(required = false, defaultValue = "") String name) {
        return timeAttendanceService.search(name);
    }
}
