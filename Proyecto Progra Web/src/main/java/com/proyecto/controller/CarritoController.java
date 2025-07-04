package com.proyecto.controller;

import com.proyecto.model.CarritoItem;
import com.proyecto.entity.Producto;
import com.proyecto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/carrito")
@SessionAttributes("carrito")
public class CarritoController {

    @Autowired
    private ProductoRepository productoRepository;

    @ModelAttribute("carrito")
    public List<CarritoItem> crearCarrito() {
        return new ArrayList<>();
    }

    @PostMapping("/agregar/{id}")
    public String agregarProducto(@PathVariable Long id,
                                  @RequestParam(defaultValue = "1") int cantidad,
                                  @ModelAttribute("carrito") List<CarritoItem> carrito) {

        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            boolean encontrado = false;
            for (CarritoItem item : carrito) {
                if (item.getProducto().getId().equals(id)) {
                    item.setCantidad(item.getCantidad() + cantidad);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                carrito.add(new CarritoItem(producto, cantidad));
            }
        }
        return "redirect:/productos";  
    }

    @GetMapping
    public String mostrarCarrito(@ModelAttribute("carrito") List<CarritoItem> carrito, Model model) {
        model.addAttribute("carrito", carrito);
        return "carrito/ver"; 
    }

    @PostMapping("/modificar")
    public String modificarCantidad(@RequestParam Long id,
                                    @RequestParam int cantidad,
                                    @ModelAttribute("carrito") List<CarritoItem> carrito) {
        for (CarritoItem item : carrito) {
            if (item.getProducto().getId().equals(id)) {
                if (cantidad <= 0) {
                    carrito.remove(item);
                } else {
                    item.setCantidad(cantidad);
                }
                break;
            }
        }
        return "redirect:/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciarCarrito(SessionStatus status) {
        status.setComplete();  
        return "redirect:/productos";
    }
}
