package com.damoralesr97.devsu.cuenta_movimiento_service.service;

import com.damoralesr97.devsu.cuenta_movimiento_service.dto.client.response.ClientResponse;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.report.response.ReportResponse;
import com.damoralesr97.devsu.cuenta_movimiento_service.mapper.ReportMapper;
import com.damoralesr97.devsu.cuenta_movimiento_service.model.Movement;
import com.damoralesr97.devsu.cuenta_movimiento_service.repository.MovementRepository;
import com.damoralesr97.devsu.cuenta_movimiento_service.service.interfaces.IReportService;
import com.damoralesr97.devsu.cuenta_movimiento_service.utils.exceptions.NotFoundExcepcion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static com.damoralesr97.devsu.cuenta_movimiento_service.utils.ClientRestConsumer.findClientByDni;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements IReportService {

    private final MovementRepository movementRepository;
    private final ReportMapper reportMapper;

    @Override
    public List<ReportResponse> getStatementAccount(String startDate, String endDate, String dni) {
        Optional<ClientResponse> client = findClientByDni(dni);
        if (client.isEmpty()) {
            throw new NotFoundExcepcion("Client not found with dni " + dni);
        }
        try {
            List<Movement> movements;
            if (startDate == null || endDate == null) {
                movements = movementRepository.findByAccountClientId(client.get().getId());
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                movements = movementRepository.findByAccountClientIdAndMovementDateIsBetween(
                        client.get().getId(),
                        formatter.parse(startDate),
                        formatter.parse(endDate)
                );
            }
            return movements.stream()
                    .map(movement -> {
                        ReportResponse response = reportMapper.toResponse(movement);
                        response.setClient(client.get().getName());
                        return response;
                    })
                    .toList();
        } catch (ParseException e) {
            throw new RuntimeException("The date format is yyyy-MM-dd");
        }
    }

}
