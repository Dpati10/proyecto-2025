package com.proyecto.service;

import com.proyecto.entity.Pedido;
import com.proyecto.entity.DetallePedido;
import com.proyecto.repository.PedidoRepository;
import com.proyecto.service.EmailService;
import com.proyecto.service.PedidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final EmailService emailService;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, EmailService emailService) {
        this.pedidoRepository = pedidoRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public Pedido crearPedido(Pedido pedido) {
        // Generar numero de pedido único y legible
        String numero = "P-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        pedido.setNumeroPedido(numero);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("CONFIRMADO");

        // Calcular total si no viene
        if (pedido.getTotal() == null || pedido.getTotal() == 0.0) {
            double total = pedido.getDetalles().stream()
                    .mapToDouble(d -> d.getSubtotal() == null ? 0.0 : d.getSubtotal())
                    .sum();
            pedido.setTotal(total);
        }

        // Persistir pedido (detalles se guardan cascada)
        Pedido guardado = pedidoRepository.save(pedido);

        // Armar HTML del correo
        String asunto = "Confirmación de pedido " + guardado.getNumeroPedido();
        StringBuilder html = new StringBuilder();
        html.append("<div style='font-family: Arial, Helvetica, sans-serif; color: #222;'>")
            .append("<h2>Gracias por tu compra</h2>")
            .append("<p>Hemos recibido tu pedido <strong>").append(guardado.getNumeroPedido()).append("</strong>.</p>")
            .append("<h4>Resumen:</h4><ul>");
        for (DetallePedido d : guardado.getDetalles()) {
            html.append("<li>")
                .append(d.getCantidad()).append(" x ").append(d.getNombreProducto())
                .append(" — ₡").append(String.format("%.2f", d.getPrecioUnitario()))
                .append("</li>");
        }
        html.append("</ul>");
        html.append("<p><strong>Total: ₡").append(String.format("%.2f", guardado.getTotal())).append("</strong></p>");
        html.append("<p>Puedes ver tus pedidos en el historial en el panel de usuario.</p>");
        html.append("</div>");

        try {
            emailService.enviarConfirmacionPedido(guardado.getClienteEmail(), asunto, html.toString());
        } catch (Exception ex) {
            // No interrumpe el flujo; sólo log
            ex.printStackTrace();
        }

        return guardado;
    }

    @Override
    public Optional<Pedido> obtenerPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public List<Pedido> obtenerPorClienteEmail(String email) {
        return pedidoRepository.findByClienteEmailOrderByFechaDesc(email);
    }

    @Override
    public Optional<Pedido> obtenerPorNumero(String numeroPedido) {
        return Optional.ofNullable(pedidoRepository.findByNumeroPedido(numeroPedido));
    }

    @Override
    public List<Pedido> obtenerPedidosPorCliente(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

