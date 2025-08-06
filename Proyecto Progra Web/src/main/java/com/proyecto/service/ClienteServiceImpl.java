package com.proyecto.service;

import com.proyecto.domain.Cliente;
import com.proyecto.dto.ClienteUpdateDTO;
import com.proyecto.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Optional<Cliente> login(String correo, String contrasena) {
        return clienteRepository.findByCorreoAndContrasena(correo, contrasena);
    }

    @Override
    public boolean correoExistente(String correo) {
        return clienteRepository.findByCorreo(correo).isPresent();
    }

    @Override
    public void registrarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public Cliente actualizarPerfil(Long id, ClienteUpdateDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (!cliente.getContrasena().equals(dto.getContrasenaActual())) {
            throw new RuntimeException("Contraseña actual incorrecta");
        }

        cliente.setNombre(dto.getNombre());
        cliente.setCorreo(dto.getCorreo());

        if (dto.getNuevaContrasena() != null && !dto.getNuevaContrasena().isBlank()) {
            cliente.setContrasena(dto.getNuevaContrasena());
        }

        return clienteRepository.save(cliente);
    }
}
