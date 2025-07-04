package com.proyecto.controller;

import com.proyecto.domain.Cliente;
import com.proyecto.entity.Pedido;
import com.proyecto.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class PanelController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/panel")
    public String mostrarPanel(@SessionAttribute("clienteAutenticado") Cliente cliente, Model model) {
        List<Pedido> pedidos = pedidoRepository.findByCliente(cliente);
        model.addAttribute("cliente", cliente);
        model.addAttribute("pedidos", pedidos);
        return "panel";
    }
}

