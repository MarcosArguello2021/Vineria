package com.marcos.proyectofinal.servicios;

import com.marcos.proyectofinal.entidades.Articulo;
import com.marcos.proyectofinal.entidades.Proveedor;
import com.marcos.proyectofinal.excepciones.WebException;
import com.marcos.proyectofinal.repositorios.ProveedorRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorServicio {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;


    @Transactional(readOnly = true)
    public List<Proveedor> listarTodos() {
        return proveedorRepositorio.findAllByOrderByNombreAsc();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {WebException.class, Exception.class})
    public Proveedor guardar(Proveedor proveedor) throws WebException {

        Proveedor entidad = new Proveedor();
        entidad.setNombre(proveedor.getNombre());
        entidad.setCorreo(proveedor.getCorreo());
        entidad.setActivo(true);
        return proveedorRepositorio.save(entidad);
    }
}
