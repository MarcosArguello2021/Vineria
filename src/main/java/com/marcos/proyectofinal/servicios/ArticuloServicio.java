package com.marcos.proyectofinal.servicios;

import com.marcos.proyectofinal.entidades.Articulo;
import com.marcos.proyectofinal.entidades.Foto;
import com.marcos.proyectofinal.enumeracion.Variedad;
import com.marcos.proyectofinal.excepciones.WebException;
import com.marcos.proyectofinal.repositorios.ArticuloRepositorio;
import com.marcos.proyectofinal.repositorios.ProveedorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArticuloServicio {

    @Autowired
    private ArticuloRepositorio articuloRepositorio;
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private FotoServicio fotoServicio;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {WebException.class, Exception.class})
    public Articulo guardar(ModelMap model, Articulo articulo, MultipartFile archivo) throws WebException {

        Articulo entidad = new Articulo();
        try {
            validar(articulo);
            entidad.setNombre(articulo.getNombre());
            entidad.setPrecio(articulo.getPrecio());
            entidad.setCantidad(articulo.getCantidad());
            entidad.setFoto(articulo.getFoto());
            Foto foto = fotoServicio.guardar(archivo);
            entidad.setImagen(foto);
            entidad.setProveedor(proveedorRepositorio.getById(articulo.getProveedor().getId()));
            entidad.setVariedad(articulo.getVariedad());
            entidad.setDescripcion(articulo.getDescripcion());
            entidad.setActivo(true);
            articuloRepositorio.save(entidad);
            return entidad;
        } catch (Exception e) {

            model.put("error", e.getMessage());
        }
        return entidad;
    }

    @Transactional
    public Articulo modificar(ModelMap modelo, Articulo articulo,
            MultipartFile archivo) throws WebException {

        Articulo entidad = articuloRepositorio.getById(articulo.getId());

        try {
            if (!archivo.isEmpty()) {
                Foto foto = fotoServicio.guardar(archivo);
                entidad.setImagen(foto);
            }

        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        } finally {
            
            entidad.setNombre(articulo.getNombre());
            entidad.setPrecio(articulo.getPrecio());
            entidad.setCantidad(articulo.getCantidad());
            entidad.setFoto(articulo.getFoto());
            entidad.setProveedor(proveedorRepositorio.getById(articulo.getProveedor().getId()));
            entidad.setVariedad(articulo.getVariedad());
            entidad.setDescripcion(articulo.getDescripcion());
            articuloRepositorio.save(entidad);
        }
        return entidad;
    }

    @javax.transaction.Transactional
    public void eliminar(Long id) throws Exception {
        Optional<Articulo> respuesta = articuloRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Articulo articulo = respuesta.get();
            articuloRepositorio.delete(articulo);
        } else {
            throw new Exception("No se encontró el artículo.");
        }
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

    @Transactional(readOnly = true)
    public Articulo buscarDescripcion(Long id) {
        return articuloRepositorio.findAllById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Articulo alta(Long id) {

        Articulo entidad = articuloRepositorio.getById(id);

        entidad.setActivo(true);
        return articuloRepositorio.save(entidad);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Articulo baja(Long id) {

        Articulo entidad = articuloRepositorio.getById(id);

        entidad.setActivo(false);
        return articuloRepositorio.save(entidad);
    }

    public void validar(Articulo articulo) throws Exception {
        if (articulo.getNombre() == null || articulo.getNombre().isEmpty()) {
            throw new Exception("El nombre del articulo es obligatorio.");
        }
        if (articulo.getPrecio() == null || articulo.getPrecio().toString().isEmpty()) {
            throw new Exception("El precio del articulo es obligatorio.");
        }

        if (articulo.getCantidad() == null || articulo.getCantidad().toString().isEmpty()) {
            throw new Exception("La cantidad del articulo es obligatorio.");
        }
        if (articulo.getProveedor() == null || articulo.getProveedor().toString().isEmpty()) {
            throw new Exception("El proveedor del articulo es obligatorio");
        }

    }
}
