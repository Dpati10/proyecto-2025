package com.proyecto.repository;

import com.proyecto.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar por email del cliente (si usas Usuario, adapta)
    List<Pedido> findByClienteEmailOrderByFechaDesc(String clienteEmail);

    Pedido findByNumeroPedido(String numeroPedido);
}
