package com.damoralesr97.devsu.cuenta_movimiento_service.service.interfaces;

import com.damoralesr97.devsu.cuenta_movimiento_service.dto.movement.request.MovementRequest;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.movement.response.MovementResponse;

import java.util.List;
import java.util.Optional;

public interface IMovementService {

    List<MovementResponse> findAll();
    Optional<MovementResponse> findById(Long id);
    MovementResponse save(MovementRequest request);
    MovementResponse update(Long id, MovementRequest request);
    void delete(Long id);

}

