
package com.marcos.proyectofinal.repositorios;


import com.marcos.proyectofinal.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario,String>{
    
}
