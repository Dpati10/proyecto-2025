package com.proyecto.service;

import com.proyecto.entity.DetallePedido;
import com.proyecto.entity.Pedido;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void enviarConfirmacion(String destinatario, Pedido pedido) {
        System.out.println("Simulando envío de correo a: " + destinatario);
        System.out.println("Asunto: Confirmación de Pedido #" + pedido.getId());

        StringBuilder cuerpo = new StringBuilder();
        cuerpo.append("Gracias por tu compra.\n\n");
        cuerpo.append("Número de pedido: ").append(pedido.getId()).append("\n");
        cuerpo.append("Dirección: ").append(pedido.getDireccionEnvio()).append("\n");
        cuerpo.append("Fecha: ").append(pedido.getFecha()).append("\n\n");
        cuerpo.append("Detalle del pedido:\n");

        for (DetallePedido detalle : pedido.getDetalles()) {
            cuerpo.append("- ").append(detalle.getProducto().getNombre())
                  .append(" x").append(detalle.getCantidad())
                  .append(" ₡").append(detalle.getPrecioUnitario()).append("\n");
        }

        System.out.println("Contenido:\n" + cuerpo);
    }
}
