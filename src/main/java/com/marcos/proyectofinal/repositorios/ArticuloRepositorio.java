
package com.marcos.proyectofinal.repositorios;

import com.marcos.proyectofinal.entidades.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepositorio extends JpaRepository<Articulo,String>{
    
}
