package com.marcos.proyectofinal.servicios;

import com.marcos.proyectofinal.entidades.Articulo;
import com.marcos.proyectofinal.enumeracion.Variedad;
import com.marcos.proyectofinal.excepciones.WebException;
import com.marcos.proyectofinal.repositorios.ArticuloRepositorio;
import com.marcos.proyectofinal.repositorios.ProveedorRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticuloServicio {
    
    @Autowired
    private ArticuloRepositorio articuloRepositorio;
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {WebException.class, Exception.class})
    public Articulo guardar(String nombre, Double precio, Integer cantidad,
            String foto, String proveedor, String variedad) throws WebException {
//        validar(nombre, apellido, correo, clave, rol);
        Articulo entidad = new Articulo();
        entidad.setNombre(nombre);
        entidad.setPrecio(precio);
        entidad.setCantidad(cantidad);
        entidad.setFoto(foto);
        entidad.setProveedor(proveedorRepositorio.getById(proveedor));
        entidad.setVariedad(Variedad.valueOf(variedad));
        entidad.setActivo(true);
        return articuloRepositorio.save(entidad);
    }
    
    @Transactional(readOnly = true)
    public List<Articulo> listarTodos() {
        return articuloRepositorio.findAllByOrderByNombreAsc();
    }
    
    @Transactional(readOnly = true)
    public List<Articulo> listarVariedad(String variedad) {
        return articuloRepositorio.buscarPorVariedad(Variedad.valueOf(variedad));
    }
    
    @Transactional(readOnly = true)
    public List<Articulo> buscarNombre(String nombre) {
        return articuloRepositorio.findByNombreContainingOrderByNombre(nombre);
    }
}
