/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.proyecto.controller;

import com.proyecto.entity.Producto;
import com.proyecto.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author diego
 */
@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Listar productos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "producto-list";
    }

    // Formulario para agregar producto
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "producto-form";
    }

    // Guardar producto nuevo o editado
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("producto") Producto producto) {
        productoService.guardar(producto);
        return "redirect:/productos";
    }

    // Editar producto
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        productoService.obtenerPorId(id).ifPresent(p -> model.addAttribute("producto", p));
        return "producto-form";
    }

    // Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/productos";
    }
}
