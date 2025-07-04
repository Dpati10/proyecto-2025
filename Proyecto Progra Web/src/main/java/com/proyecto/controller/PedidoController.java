package com.proyecto.controller;

import com.proyecto.entity.DetallePedido;
import com.proyecto.entity.Pedido;
import com.proyecto.model.CarritoItem;
import com.proyecto.repository.PedidoRepository;
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
@SessionAttributes("carrito")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping("/confirmar")
    public String confirmarPedido(@RequestParam String direccion,
                                  @ModelAttribute("carrito") List<CarritoItem> carrito,
                                  Model model,
                                  SessionStatus status) {

        if (carrito == null || carrito.isEmpty()) {
            model.addAttribute("mensaje", "El carrito está vacío");
            return "carrito/ver";
        }

        Pedido pedido = new Pedido();
        pedido.setDireccionEnvio(direccion);
        pedido.setFecha(LocalDate.now());

        List<DetallePedido> detalles = new ArrayList<>();
        for (CarritoItem item : carrito) {
            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getProducto().getPrecio());
            detalle.setPedido(pedido);
            detalles.add(detalle);
        }
        pedido.setDetalles(detalles);

        pedidoRepository.save(pedido);

        status.setComplete(); 

        model.addAttribute("mensaje", "Pedido confirmado exitosamente");
        model.addAttribute("pedido", pedido);

        return "pedido/resumen"; 
    }
}
