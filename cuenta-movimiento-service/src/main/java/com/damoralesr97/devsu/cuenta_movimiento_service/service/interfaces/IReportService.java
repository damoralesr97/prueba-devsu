package com.damoralesr97.devsu.cuenta_movimiento_service.service.interfaces;

import com.damoralesr97.devsu.cuenta_movimiento_service.dto.report.response.ReportResponse;

import java.util.List;

public interface IReportService {

    List<ReportResponse> getStatementAccount(String startDate, String endDate, String dni);

}
