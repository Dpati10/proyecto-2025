package com.proyecto.service;

public interface EmailService {
    void enviarConfirmacionPedido(String toEmail, String asunto, String cuerpoHtml);
}
