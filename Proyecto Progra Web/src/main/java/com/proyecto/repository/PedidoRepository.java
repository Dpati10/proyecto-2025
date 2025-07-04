package com.proyecto.repository;

import com.proyecto.domain.Cliente;
import com.proyecto.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Devuelve todos los pedidos realizados por un cliente específico
    List<Pedido> findByCliente(Cliente cliente);
}
