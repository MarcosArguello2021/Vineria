package com.marcos.proyectofinal.controladores;

import com.marcos.proyectofinal.entidades.Articulo;
import com.marcos.proyectofinal.entidades.Foto;
import com.marcos.proyectofinal.entidades.Proveedor;
import com.marcos.proyectofinal.entidades.Usuario;
import com.marcos.proyectofinal.excepciones.WebException;
import com.marcos.proyectofinal.servicios.ArticuloServicio;
import com.marcos.proyectofinal.servicios.FotoServicio;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/articulo")
public class ArticuloController {

    @Autowired
    private ArticuloServicio articuloServicio;
    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private FotoServicio fotoServicio;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/registro")
    public String articulo(ModelMap modelo, final Articulo articulo) {
        List<Articulo> articulos = articuloServicio.listarTodos();
        modelo.addAttribute("articulos", articulos);
        List<Proveedor> proveedores = proveedorServicio.listarTodos();
        modelo.addAttribute("proveedores", proveedores);
        List<Usuario> usuarios = usuarioServicio.listarTodos();
        modelo.addAttribute("usuarios", usuarios);
        return "articulosAdmin";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/registro")
    public String guardar(ModelMap modelo, Articulo articulo, 
            MultipartFile archivo) throws WebException {
        try {
      
            articuloServicio.guardar(modelo, articulo, archivo);
            modelo.put("exito", "registro exitoso");
            return "redirect:/articulo/registro";
        } catch (WebException e) {
            modelo.put("error", e.getMessage());
            return "redirect:/articulo/registro";
        }
    }

    @PostMapping("/listar")
    public String listarVariedad(ModelMap modelo, String variedad) {
        List<Articulo> articulos = articuloServicio.listarVariedad(variedad);
        modelo.addAttribute("articulos", articulos);
List<Usuario> usuarios = usuarioServicio.listarTodos();
        modelo.addAttribute("usuarios", usuarios);
        return "listadoArticulos";
    }

    @PostMapping("/buscar")
    public String buscarNombre(ModelMap modelo, String nombre) {
        List<Articulo> articulos = articuloServicio.buscarNombre(nombre);
        modelo.addAttribute("articulos", articulos);
        List<Usuario> usuarios = usuarioServicio.listarTodos();
        modelo.addAttribute("usuarios", usuarios);
        return "listadoArticulos";
    }

    @PostMapping("/descripcion")
    public String descripcion(ModelMap modelo, Long id) {
        Articulo articulo = articuloServicio.buscarDescripcion(id);
        modelo.addAttribute("articulo", articulo);
        return "paginaProducto";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar")
    public String listar(ModelMap modelo, final Articulo articulo) {
        List<Articulo> articulos = articuloServicio.listarTodos();
        modelo.addAttribute("articulos", articulos);
        List<Proveedor> proveedores = proveedorServicio.listarTodos();
        modelo.addAttribute("proveedores", proveedores);
        List<Usuario> usuarios = usuarioServicio.listarTodos();
        modelo.addAttribute("usuarios", usuarios);
        List<Foto> fotos = fotoServicio.listarTodos();
        modelo.addAttribute("fotos", fotos);
        return "administrarArticulos";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/editar")
    public String editar(ModelMap modelo, Articulo articulo, 
            MultipartFile archivo) {
        System.out.println(archivo.getName());
        try {
            
            articuloServicio.modificar(modelo, articulo, archivo);
            modelo.put("exito", "registro exitoso");
            return "redirect:/articulo/listar";
        } catch (WebException e) {

            modelo.put("error", e.getMessage());
            return "administrarArticulos";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/eliminar/{id}")
    public String eliminarArticulo(ModelMap model, @PathVariable("id") Long id) {
        try {
            articuloServicio.eliminar(id);
            return "redirect:/articulo/listar";
        } catch (Exception e) {
            model.put("error", "El libro no pudo ser eliminado");
            return "redirect:/articulo/listar";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/baja/{id}")
    public String baja(ModelMap modelo, @PathVariable Long id) {

        try {
            articuloServicio.baja(id);
            return "redirect:/articulo/listar";
        } catch (Exception e) {
            return "redirect:/articulo/listar";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/alta/{id}")
    public String alta(ModelMap modelo, @PathVariable Long id) {

        try {
            articuloServicio.alta(id);
            return "redirect:/articulo/listar";
        } catch (Exception e) {
            return "redirect:/articulo/listar";
        }
    }
}
