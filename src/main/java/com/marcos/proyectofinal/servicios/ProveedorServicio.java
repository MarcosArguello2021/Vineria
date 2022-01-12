package com.marcos.proyectofinal.servicios;


import com.marcos.proyectofinal.entidades.Proveedor;
import com.marcos.proyectofinal.excepciones.WebException;
import com.marcos.proyectofinal.repositorios.ProveedorRepositorio;
import java.util.List;
import java.util.Optional;
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
        entidad.setCuit(proveedor.getCuit());
        entidad.setActivo(true);
        return proveedorRepositorio.save(entidad);
    }
    @Transactional
    public void modificar(Proveedor dto) throws WebException {
        Proveedor proveedor = proveedorRepositorio.getById(dto.getId());
        proveedor.setNombre(dto.getNombre());
        proveedor.setCuit(dto.getCuit());
        proveedor.setCorreo(dto.getCorreo());
        proveedorRepositorio.save(proveedor);
    }
    
    @javax.transaction.Transactional
    public void eliminar(Long id) throws Exception {
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            proveedorRepositorio.delete(proveedor);
        } else {
            throw new Exception("No se encontró el proveedor.");
        }
    }
        
        @Transactional(readOnly = true)
    public Proveedor buscarProveedor(Long id) {
        return proveedorRepositorio.getById(id);
    }
    
     @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Proveedor alta(Long id) {

		Proveedor entidad = proveedorRepositorio.getById(id);

		entidad.setActivo(true);
		return proveedorRepositorio.save(entidad);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Proveedor baja(Long id) {

		Proveedor entidad = proveedorRepositorio.getById(id);

		entidad.setActivo(false);
		return proveedorRepositorio.save(entidad);
	}
}
