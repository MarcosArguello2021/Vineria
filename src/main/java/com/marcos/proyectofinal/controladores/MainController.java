package com.marcos.proyectofinal.controladores;

import com.marcos.proyectofinal.entidades.Articulo;
import com.marcos.proyectofinal.entidades.Usuario;
import com.marcos.proyectofinal.servicios.ArticuloServicio;
import com.marcos.proyectofinal.servicios.CorreoServicio;
import com.marcos.proyectofinal.servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private ArticuloServicio articuloServicio;
    @Autowired
    private CorreoServicio correoServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarTodos();
        modelo.addAttribute("usuarios", usuarios);
        return "index.html";
    }

    @GetMapping("/login")
    public String login(HttpSession session, Authentication usuario, ModelMap modelo, @RequestParam(required = false) String error) {
        try {
            if (usuario.getName() != null) {
                session.setAttribute("usuariosession", usuario);
                Usuario login = (Usuario) session.getAttribute("usuariosession");
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
        return "redirect:/";
    }

    @GetMapping("/loginsuccess")
    public String loginresolver(HttpSession session, ModelMap model) {

        return "redirect:/";
    }

    @GetMapping("/terminos")
    public String terminos() {

        return "terminos";
    }
    
    @GetMapping("/nosotros/")
    public String nosotros(ModelMap modelo) {
 List<Usuario> usuarios = usuarioServicio.listarTodos();
        modelo.addAttribute("usuarios", usuarios);
        return "nosotros";
    }

   

    @GetMapping("/catalogo")
    public String catalogo(ModelMap modelo) {
        List<Articulo> articulos = articuloServicio.listarTodos();
        modelo.addAttribute("articulos", articulos);
         List<Usuario> usuarios = usuarioServicio.listarTodos();
        modelo.addAttribute("usuarios", usuarios);
        
        return "catalogo";
    }

    @PostMapping("/mailsender")
    public String enviarMail(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String correo, @RequestParam String mensaje) {
        try {
            correoServicio.notificar(nombre, apellido,
                    mensaje, correo);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
