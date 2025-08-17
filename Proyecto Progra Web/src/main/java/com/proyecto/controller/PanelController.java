package com.proyecto.controller;

import com.proyecto.domain.Cliente;
import com.proyecto.entity.Pedido;
import com.proyecto.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PanelController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/panel")
    public String panelUsuario(HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/login";
        }
        model.addAttribute("cliente", cliente); // aseguramos que esté en el modelo
        return "panel";
    }

    @GetMapping("/usuario/panel")
    public String verPanelUsuario(HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/login";
        }
        model.addAttribute("cliente", cliente);
        return "usuario/panel";
    }

    @GetMapping("/usuario/pedidos")
    public String verHistorialDesdePanel() {
        // Redirige al historial de pedidos
        return "redirect:/pedidos/historial";
    }

    @GetMapping("/panel/historial")
    public String historialPedidos(HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/login";
        }
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorCliente(cliente.getId());
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("cliente", cliente);
        return "historial";
    }
}
