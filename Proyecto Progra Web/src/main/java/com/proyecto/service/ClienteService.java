package com.proyecto.service;

import com.proyecto.domain.Cliente;
import com.proyecto.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Optional<Cliente> login(String correo, String contrasena) {
        return clienteRepository.findByCorreoAndContrasena(correo, contrasena);
    }

    public boolean correoExistente(String correo) {
        return clienteRepository.findByCorreo(correo).isPresent();
    }

    public void registrarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }
}
