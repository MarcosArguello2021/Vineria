
package com.marcos.proyectofinal.repositorios;


import com.marcos.proyectofinal.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepositorio extends JpaRepository<Usuario,String>{
    
    @Query("SELECT a from Usuario a WHERE a.correo LIKE :correo AND a.activo = true")
	public Usuario buscarPorEmail(@Param("correo") String correo);
}
