package com.proyecto.repository;

import com.proyecto.domain.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    Optional<Cliente> findByCorreo(String correo);
    Optional<Cliente> findByCorreoAndContrasena(String correo, String contrasena);
}


