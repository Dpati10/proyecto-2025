/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.service;

import com.proyecto.entity.Pedido;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void enviarConfirmacion(String destinatario, Pedido pedido) {
        // Simulación de envío de correo (solo imprime en consola)
        System.out.println("Simulando envío de correo a: " + destinatario);
        System.out.println("Asunto: Confirmación de Pedido #" + pedido.getId());
        System.out.println("Contenido:\nGracias por tu compra.\n\n"
                + "Número de pedido: " + pedido.getId() + "\n"
                + "Dirección: " + pedido.getDireccionEnvio() + "\n"
                + "Fecha: " + pedido.getFecha() + "\n\n"
                + "Detalle del pedido:\n" + pedido.getDetalleResumen());
    }
}