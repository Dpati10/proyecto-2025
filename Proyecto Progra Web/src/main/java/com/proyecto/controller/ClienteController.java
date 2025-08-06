package com.proyecto.controller;

import com.proyecto.domain.Cliente;
import com.proyecto.dto.ClienteUpdateDTO;
import com.proyecto.service.ClienteService;
import com.proyecto.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    // ------------------- Registro -------------------

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

    // ------------------- Edición de perfil -------------------

    @GetMapping("/clientes/{id}/editar-perfil")
    public String mostrarFormularioEditarPerfil(@PathVariable Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        model.addAttribute("cliente", cliente);
        return "editar-perfil";
    }

    @PostMapping("/clientes/{id}/editar-perfil")
    public String procesarEdicionPerfil(
            @PathVariable Long id,
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String contrasenaActual,
            @RequestParam(required = false) String nuevaContrasena,
            RedirectAttributes redirectAttributes
    ) {
        ClienteUpdateDTO dto = new ClienteUpdateDTO();
        dto.setNombre(nombre);
        dto.setCorreo(correo);
        dto.setContrasenaActual(contrasenaActual);
        dto.setNuevaContrasena(nuevaContrasena);

        try {
            clienteService.actualizarPerfil(id, dto);
            redirectAttributes.addFlashAttribute("mensaje", "Perfil actualizado correctamente.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/clientes/" + id + "/editar-perfil";
    }
}
