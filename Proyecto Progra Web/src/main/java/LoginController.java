
import com.proyecto.domain.Cliente;
import com.proyecto.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@SessionAttributes("clienteAutenticado")
public class LoginController {

    @Autowired
    private ClienteService clienteService;

    // Muestra el formulario de login
    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "login";  // Vista login.html
    }

    // Procesa el formulario de login
    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute Cliente clienteForm, Model model, HttpSession session) {
        Optional<Cliente> cliente = clienteService.login(clienteForm.getCorreo(), clienteForm.getContrasena());

        if (cliente.isPresent()) {
            Cliente clienteAutenticado = cliente.get();

            // Guardamos el objeto completo en la sesión para usar en vistas
            model.addAttribute("clienteAutenticado", clienteAutenticado);

            // También guardamos solo el ID en sesión (útil si accedemos desde otros controladores)
            session.setAttribute("clienteId", clienteAutenticado.getId());

            return "redirect:/panel";  // Redirige al panel del cliente
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos.");
            return "login";
        }
    }

    // Cierra la sesión
    @GetMapping("/logout")
    public String cerrarSesion(SessionStatus status, HttpSession session) {
        status.setComplete();           // Limpia los atributos @SessionAttributes
        session.invalidate();           // Invalida toda la sesión (borra clienteId también)
        return "redirect:/login?logout";
    }
}
