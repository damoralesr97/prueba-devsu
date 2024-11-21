package com.damoralesr97.devsu.cuenta_movimiento_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.damoralesr97.devsu.cuenta_movimiento_service.model.Movement;

import java.util.Date;
import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByAccountClientId(Long clientId);
    List<Movement> findByAccountClientIdAndMovementDateIsBetween(Long clientId, Date from, Date to);

}