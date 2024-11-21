package com.damoralesr97.devsu.cuenta_movimiento_service.controller;

import com.damoralesr97.devsu.cuenta_movimiento_service.dto.report.response.ReportResponse;
import com.damoralesr97.devsu.cuenta_movimiento_service.service.interfaces.IReportService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/reportes")
public class ReportController {

    private final IReportService reportService;

    @GetMapping
    public ResponseEntity<?> getStatementAccount(
            @RequestParam(required = false) @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "El formato de start date debe ser yyyy-MM-dd") String startDate,
            @RequestParam(required = false) @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "El formato de end date debe ser yyyy-MM-dd") String endDate,
            @RequestParam String dni
    ) {
        List<ReportResponse> reports = reportService.getStatementAccount(startDate, endDate, dni);
        if (reports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reports);
    }

}
