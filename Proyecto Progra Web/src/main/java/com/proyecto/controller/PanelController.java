package com.proyecto.controller;

import com.proyecto.domain.Cliente;
import com.proyecto.entity.Pedido;
import com.proyecto.entity.Producto;
import com.proyecto.service.PedidoService;
import com.proyecto.service.ProductoService;
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

    @Autowired
    private ProductoService productoService;

    @GetMapping("/panel")
    public String panelUsuario(HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/login";
        }
        model.addAttribute("cliente", cliente);

        // ✅ Agregamos lista de productos al panel
        List<Producto> productos = productoService.listarTodos();
        model.addAttribute("productos", productos);

        return "panel";
    }

    @GetMapping("/usuario/panel")
    public String verPanelUsuario(HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/login";
        }
        model.addAttribute("cliente", cliente);

        // ✅ Agregamos lista de productos al panel de usuario
        List<Producto> productos = productoService.listarTodos();
        model.addAttribute("productos", productos);

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
