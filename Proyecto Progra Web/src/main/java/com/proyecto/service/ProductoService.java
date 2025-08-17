/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.proyecto.service;
import com.proyecto.entity.Producto;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author diego
 */
public interface ProductoService {
      List<Producto> listarTodos();
    Optional<Producto> obtenerPorId(Long id);
    Producto guardar(Producto producto);
    void eliminar(Long id);
}
