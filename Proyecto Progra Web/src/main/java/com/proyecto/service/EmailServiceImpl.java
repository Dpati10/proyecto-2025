/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.service;
 
import org.springframework.stereotype.Service;
 
@Service
public class EmailServiceImpl implements EmailService {
 
    @Override
    public void enviarConfirmacionPedido(String toEmail, String asunto, String cuerpoHtml) {
        // Por ahora solo imprime para pruebas
        System.out.println("Enviando correo a: " + toEmail);
        System.out.println("Asunto: " + asunto);
        System.out.println("Cuerpo HTML: " + cuerpoHtml);
    }
}