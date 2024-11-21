package com.damoralesr97.devsu.cuenta_movimiento_service.controller;

import com.damoralesr97.devsu.cuenta_movimiento_service.service.interfaces.IMovementService;
import com.damoralesr97.devsu.cuenta_movimiento_service.utils.exceptions.NotFoundExcepcion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.movement.request.MovementRequest;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.movement.response.MovementResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movimientos")
public class MovementController {

    private final IMovementService movementService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<MovementResponse> movements = movementService.findAll();
        if (movements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return movementService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundExcepcion("Movement " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MovementRequest request) {
        MovementResponse savedMovement = movementService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MovementRequest request) {
        MovementResponse updatedMovement = movementService.update(id, request);
        return ResponseEntity.ok(updatedMovement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        movementService.delete(id);
        return ResponseEntity.ok().build();
    }

}