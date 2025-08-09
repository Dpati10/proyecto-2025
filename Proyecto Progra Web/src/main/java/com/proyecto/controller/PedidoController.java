package com.proyecto.controller;

import com.proyecto.entity.Pedido;
import com.proyecto.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * Crear pedido desde formulario (ModelAttribute) o si tu frontend manda JSON, cambia a @RequestBody.
     * Se asume que el frontend incluye los detalles en el form (nombres compatibles con DetallePedido).
     */
    @PostMapping("/crear")
    public String crearPedido(@ModelAttribute Pedido pedido, Model model, Principal principal) {
        String email = obtenerEmailDesdePrincipal(principal);
        pedido.setClienteEmail(email);

        Pedido guardado = pedidoService.crearPedido(pedido);

        model.addAttribute("numeroPedido", guardado.getNumeroPedido());
        model.addAttribute("pedido", guardado);
        model.addAttribute("mensaje", "¡Gracias! Tu pedido se procesó correctamente.");

        return "pedidos/confirmacion";
    }

    @GetMapping("/confirm/{numero}")
    public String verConfirmacionPorNumero(@PathVariable("numero") String numero, Model model) {
        Optional<Pedido> op = pedidoService.obtenerPorNumero(numero);
        if (op.isEmpty()) {
            model.addAttribute("error", "Pedido no encontrado.");
            return "error";
        }
        Pedido pedido = op.get();
        model.addAttribute("mensaje", "¡Gracias! Tu pedido se procesó correctamente.");
        model.addAttribute("numeroPedido", pedido.getNumeroPedido());
        model.addAttribute("pedido", pedido);
        return "pedidos/confirmacion";
    }

    @GetMapping("/historial")
    public String historialPedidos(Model model, Principal principal) {
        String email = obtenerEmailDesdePrincipal(principal);
        model.addAttribute("pedidos", pedidoService.obtenerPorClienteEmail(email));
        return "pedidos/historial";
    }

    @GetMapping("/detalle/{id}")
    public String detallePedido(@PathVariable("id") Long id, Model model, Principal principal) {
        Optional<Pedido> op = pedidoService.obtenerPorId(id);
        if (op.isEmpty()) {
            model.addAttribute("error", "Pedido no encontrado");
            return "error";
        }
        Pedido pedido = op.get();
        String email = obtenerEmailDesdePrincipal(principal);
        if (!pedido.getClienteEmail().equals(email)) {
            model.addAttribute("error", "No tienes permiso para ver este pedido");
            return "error";
        }
        model.addAttribute("pedido", pedido);
        return "pedidos/detalle";
    }

    private String obtenerEmailDesdePrincipal(Principal principal) {
        // Opción 1 (Spring Security simple): principal.getName() devuelve username/email
        if (principal != null && principal.getName() != null) {
            return principal.getName();
        }
        // Opción 2: si tu proyecto guarda email en sesión o en un servicio, adapta aquí:
        // return (String) httpSession.getAttribute("userEmail");
        // En ausencia de autenticación, usa un valor de pruebas (NO recomendado en producción)
        return "cliente@ejemplo.com";
    }
}
