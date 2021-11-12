
package com.marcos.proyectofinal.controladores;

import com.marcos.proyectofinal.excepciones.WebException;
import com.marcos.proyectofinal.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @PostMapping("/registro")
	public String guardar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido,
			@RequestParam String correo, @RequestParam String password) {

		try {
			usuarioServicio.guardar(nombre, apellido, correo, password);

			modelo.put("exito", "registro exitoso");
			return "index";
		} catch (WebException e) {
			modelo.put("error", e.getMessage());
			return "index";
		}
	}
}
