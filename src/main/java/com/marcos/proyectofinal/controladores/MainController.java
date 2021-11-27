package com.marcos.proyectofinal.controladores;

import com.marcos.proyectofinal.entidades.Articulo;
import com.marcos.proyectofinal.servicios.ArticuloServicio;
import com.marcos.proyectofinal.servicios.NotificacionService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private ArticuloServicio articuloServicio;
    @Autowired
    private NotificacionService notificacionService;
    

    @GetMapping("/login")
    public String login(HttpSession session, Authentication usuario, ModelMap modelo, @RequestParam(required = false) String error) {
        try {
            if (usuario.getName() != null) {

                return "redirect:/";
            } else {

                if (error != null && !error.isEmpty()) {
                    modelo.addAttribute("error", "La dirección de mail o la contraseña que ingresó son incorrectas.");
                }
                return "index";
            }

        } catch (Exception e) {
            if (error != null && !error.isEmpty()) {
                modelo.addAttribute("error", "La dirección de mail o la contraseña que ingresó son incorrectas.");
            }
            return "index";
        }
    }

    @GetMapping("/logout")
    public String logout(@RequestParam(required = false) String logout, ModelMap model) {

        if (logout != null) {
            model.put("logout", "Ha salido correctamente.");
        }
        return "index";
    }

    @GetMapping("/loginsuccess")
    public String loginresolver(HttpSession session, ModelMap model) {

        return "redirect:/";
    }

    @GetMapping("/")
    public String index(ModelMap modelo) {

        return "index.html";
    }

    @GetMapping("/nosotros/")
    public String nosotros(ModelMap modelo) {

        return "nosotros";
    }

    @GetMapping("/contacto")
    public String contacto(ModelMap modelo) {

        return "contacto";
    }

    @GetMapping("/catalogo")
    public String catalogo(ModelMap modelo) {
        List<Articulo> articulos = articuloServicio.listarTodos();
        modelo.addAttribute("articulos", articulos);
        return "catalogo";
    }

   @PostMapping("/mailsender")
	public String enviarMail(@PathVariable String nombre, @RequestParam String apellido,
                @RequestParam String correo, @RequestParam String mensaje) {
		try {
//			notificacionService.notificar();
			return "redirect:/";
		} catch (Exception e) {
			return "redirect:/";
		}
	}
}
