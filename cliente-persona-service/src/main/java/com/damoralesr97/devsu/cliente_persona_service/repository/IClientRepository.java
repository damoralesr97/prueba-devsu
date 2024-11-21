package com.damoralesr97.devsu.cliente_persona_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.damoralesr97.devsu.cliente_persona_service.model.Client;

import java.util.Optional;

public interface IClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByDni(String dni);

}