
package com.marcos.proyectofinal.repositorios;

import com.marcos.proyectofinal.entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio  extends JpaRepository<Proveedor,String>{
    
}
