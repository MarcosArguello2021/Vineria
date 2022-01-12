package com.marcos.proyectofinal.controladores;

import com.marcos.proyectofinal.entidades.Proveedor;
import com.marcos.proyectofinal.entidades.Usuario;
import com.marcos.proyectofinal.excepciones.WebException;
import com.marcos.proyectofinal.servicios.ProveedorServicio;
import com.marcos.proyectofinal.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/registro")
    public String proveedor(ModelMap modelo, final Proveedor proveedor) {
List<Usuario> usuarios = usuarioServicio.listarTodos();
        modelo.addAttribute("usuarios", usuarios);
        return "proveedorAdmin";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/registro")
    public String guardar(ModelMap modelo, Proveedor proveedor) {
        try {
            proveedorServicio.guardar(proveedor);
            modelo.put("exito", "registro exitoso");
            return "redirect:/proveedor/registro";
        } catch (WebException e) {
            modelo.put("error", e.getMessage());
            return "proveedorAdmin";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar")
    public String listar(ModelMap modelo, final Proveedor proveedor) {
        List<Proveedor> proveedores = proveedorServicio.listarTodos();
        modelo.addAttribute("proveedores", proveedores);
        List<Usuario> usuarios = usuarioServicio.listarTodos();
        modelo.addAttribute("usuarios", usuarios);
        return "administrarProveedores";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/editar/{id}")
    public String editar(ModelMap modelo, Proveedor proveedor) {
        System.out.println(proveedor.toString());
        try {
            proveedorServicio.modificar(proveedor);
            modelo.put("exito", "registro exitoso");
            return "redirect:/proveedor/listar";
        } catch (WebException e) {
            modelo.put("error", e.getMessage());
            return "administrarProveedores";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/eliminar/{id}")
    public String eliminarProveedor(ModelMap model, @PathVariable("id") Long id) {
        try {
            proveedorServicio.eliminar(id);
            return "redirect:/proveedor/listar";
        } catch (Exception e) {
            model.put("error", "El proveedor no pudo ser eliminado");
            return "redirect:/proveedor/listar";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/baja/{id}")
    public String baja(ModelMap modelo, @PathVariable Long id) {

        try {
            proveedorServicio.baja(id);
            return "redirect:/proveedor/listar";
        } catch (Exception e) {
            return "redirect:/proveedor/listar";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/alta/{id}")
    public String alta(ModelMap modelo, @PathVariable Long id) {

        try {
            proveedorServicio.alta(id);
            return "redirect:/proveedor/listar";
        } catch (Exception e) {
            return "redirect:/proveedor/listar";
        }
    }
}
