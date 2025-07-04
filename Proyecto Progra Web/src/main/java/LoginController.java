

import com.proyecto.domain.Cliente;
import com.proyecto.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Optional;

@Controller
@SessionAttributes("clienteAutenticado")
public class LoginController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "login";  // Muestra la vista login.html
    }

    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute Cliente clienteForm, Model model) {
        Optional<Cliente> cliente = clienteService.login(clienteForm.getCorreo(), clienteForm.getContrasena());

        if (cliente.isPresent()) {
            model.addAttribute("clienteAutenticado", cliente.get());
            return "redirect:/panel";  // Redirige al panel del cliente si el login fue correcto
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos.");
            return "login";  // Vuelve a la página de login con mensaje de error
        }
    }

    @GetMapping("/logout")
    public String cerrarSesion(SessionStatus status) {
        status.setComplete();  // Cierra la sesión
        return "redirect:/login?logout";
    }
}
