package com.marcos.proyectofinal.servicios;

import com.marcos.proyectofinal.entidades.Articulo;
import com.marcos.proyectofinal.entidades.Carrito;
import com.marcos.proyectofinal.entidades.Proveedor;
import com.marcos.proyectofinal.excepciones.WebException;
import com.marcos.proyectofinal.repositorios.CarritoRepositorio;
import com.marcos.proyectofinal.repositorios.UsuarioRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarritoServicio {
   
    @Autowired
    private CarritoRepositorio carritoRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {WebException.class, Exception.class})
    public Carrito guardar(String usuario, List<Articulo> articulos, Double total) throws WebException {
//      validar(nombre, apellido, correo, clave, rol);
        Carrito entidad = new Carrito();
        entidad.setUsuario(usuarioRepositorio.getById(usuario));
        entidad.setArticulos(articulos);
        entidad.setTotalPedido(total);
        return carritoRepositorio.save(entidad);
    }
}
