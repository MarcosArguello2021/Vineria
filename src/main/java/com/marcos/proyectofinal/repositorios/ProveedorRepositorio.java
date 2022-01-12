package com.marcos.proyectofinal.repositorios;

import com.marcos.proyectofinal.entidades.Proveedor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String> {

    public List<Proveedor> findAllByOrderByNombreAsc();

    public Optional<Proveedor> findById(Long id);

    public Proveedor getById(Long id);

}
