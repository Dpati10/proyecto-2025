package com.proyecto.controller;

import com.proyecto.entity.Producto;
import com.proyecto.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
public String listar(Model model) {
    model.addAttribute("productos", productoService.listarTodos());
    model.addAttribute("producto", new Producto()); // 👈 necesario para formulario
    return "producto-list"; 
}


    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto) {
        productoService.guardar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/productos";
    }
}
