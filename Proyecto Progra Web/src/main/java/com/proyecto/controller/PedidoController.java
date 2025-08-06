package com.proyecto.controller;

import com.proyecto.domain.Cliente;
import com.proyecto.entity.DetallePedido;
import com.proyecto.entity.Pedido;
import com.proyecto.model.CarritoItem;
import com.proyecto.repository.PedidoRepository;
import com.proyecto.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pedido")
@SessionAttributes({"carrito", "clienteAutenticado"})
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/confirmar")
    public String confirmarPedido(@RequestParam String direccion,
                                  @ModelAttribute("carrito") List<CarritoItem> carrito,
                                  @ModelAttribute("clienteAutenticado") Cliente cliente,
                                  Model model,
                                  SessionStatus status) {

        Pedido pedido = new Pedido();
        pedido.setDireccionEnvio(direccion);
        pedido.setFecha(LocalDate.now());
        pedido.setCliente(cliente);

        List<DetallePedido> detalles = new ArrayList<>();
        for (CarritoItem item : carrito) {
            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecio(item.getProducto().getPrecio());
            detalle.setPedido(pedido);
            detalles.add(detalle);
        }

        pedido.setDetalles(detalles);
        pedidoRepository.save(pedido);

        try {
            emailService.enviarConfirmacion(cliente.getCorreo(), pedido);
        } catch (Exception e) {
            System.out.println("Error simulado al enviar el correo: " + e.getMessage());
        }

        model.addAttribute("pedido", pedido);
        status.setComplete();
        return "confirmacion";
    }

    @GetMapping("/historial")
    public String verHistorial(Model model, @ModelAttribute("clienteAutenticado") Cliente cliente) {
        List<Pedido> pedidos = pedidoRepository.findByCliente(cliente);
        model.addAttribute("pedidos", pedidos);
        return "historial";
    }
}
