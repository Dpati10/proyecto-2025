package com.proyecto.service;

import com.proyecto.entity.Pedido;
import java.util.List;
import java.util.Optional;

public interface PedidoService {
    Pedido crearPedido(Pedido pedido);
    Optional<Pedido> obtenerPorId(Long id);
    List<Pedido> obtenerPorClienteEmail(String email);
    Optional<Pedido> obtenerPorNumero(String numeroPedido);

    public List<Pedido> obtenerPedidosPorCliente(Long id);
}
