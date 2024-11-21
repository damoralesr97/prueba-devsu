package com.damoralesr97.devsu.cliente_persona_service.controller;

import com.damoralesr97.devsu.cliente_persona_service.dto.client.request.ClientAccountRequest;
import com.damoralesr97.devsu.cliente_persona_service.service.interfaces.IClientService;
import com.damoralesr97.devsu.cliente_persona_service.utils.exceptions.NotFoundExcepcion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.damoralesr97.devsu.cliente_persona_service.dto.client.request.ClientRequest;
import com.damoralesr97.devsu.cliente_persona_service.dto.client.response.ClientResponse;
import java.util.List;


@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<ClientResponse> clients = clientService.findAll();
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<?> findByDni(@PathVariable String dni) {
        return clientService.findByDni(dni)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundExcepcion("Dni " + dni + " not found"));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClientAccountRequest request) {
        ClientResponse savedClient = clientService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @PutMapping("/{dni}")
    public ResponseEntity<?> update(@PathVariable String dni, @RequestBody ClientRequest request) {
        ClientResponse updatedClient = clientService.update(dni, request);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<?> delete(@PathVariable String dni) {
        clientService.delete(dni);
        return ResponseEntity.ok().build();
    }

}