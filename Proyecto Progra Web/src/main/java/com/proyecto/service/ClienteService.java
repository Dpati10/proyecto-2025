package com.proyecto.service;

import com.proyecto.domain.Cliente;
import com.proyecto.dto.ClienteUpdateDTO;

import java.util.Optional;

public interface ClienteService {
    Optional<Cliente> login(String correo, String contrasena);
    boolean correoExistente(String correo);
    void registrarCliente(Cliente cliente);
    Cliente actualizarPerfil(Long id, ClienteUpdateDTO dto);
}
