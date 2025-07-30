package com.proyecto.controller;

import com.proyecto.domain.Cliente;
import com.proyecto.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("cliente", new Cliente()); 
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarCliente(@ModelAttribute Cliente cliente, Model model) {
        if (clienteService.correoExistente(cliente.getCorreo())) {
            model.addAttribute("error", "El correo ya está registrado.");
            return "registro";
        }
        clienteService.registrarCliente(cliente);
        return "redirect:/registro?exito";
    }
}
