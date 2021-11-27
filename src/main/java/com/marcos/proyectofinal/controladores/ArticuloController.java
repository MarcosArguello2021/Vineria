package com.marcos.proyectofinal.controladores;

import com.marcos.proyectofinal.entidades.Articulo;
import com.marcos.proyectofinal.entidades.Proveedor;
import com.marcos.proyectofinal.excepciones.WebException;
import com.marcos.proyectofinal.servicios.ArticuloServicio;
import com.marcos.proyectofinal.servicios.ProveedorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/articulo")
public class ArticuloController {

    @Autowired
    private ArticuloServicio articuloServicio;
    @Autowired
    private ProveedorServicio proveedorServicio;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/registro")
    public String articulo(ModelMap modelo) {
        List<Articulo> articulos = articuloServicio.listarTodos();
        
        modelo.addAttribute("articulos", articulos);

        List<Proveedor> proveedores = proveedorServicio.listarTodos();
        modelo.addAttribute("proveedores", proveedores);
        return "articulosAdmin";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam String nombre,
            @RequestParam Double precio, @RequestParam Integer cantidad,
            @RequestParam String foto, @RequestParam String proveedor, 
            @RequestParam String variedad) {
        try {
            articuloServicio.guardar(nombre, precio,
                    cantidad, foto, proveedor, variedad);
            modelo.put("exito", "registro exitoso");
            return "articulosAdmin";
        } catch (WebException e) {
            modelo.put("error", e.getMessage());
            return "articulosAdmin";
        }
    }
   
    @PostMapping("/listar")
    public String listarVariedad(ModelMap modelo, String variedad) {
        List<Articulo> articulos = articuloServicio.listarVariedad(variedad); 
        modelo.addAttribute("articulos", articulos);
        return "listadoArticulos";
    }
    
    @PostMapping("/buscar")
    public String buscarNombre(ModelMap modelo, String nombre) {
        List<Articulo> articulos = articuloServicio.buscarNombre(nombre); 
        modelo.addAttribute("articulos", articulos);
        return "listadoArticulos";
    }
    
    @GetMapping("/descripcion")
    public String descripcion(ModelMap modelo) {
       
        return "pagina-producto";
    }
    
}
