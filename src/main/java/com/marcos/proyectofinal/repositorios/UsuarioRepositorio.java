package com.marcos.proyectofinal.repositorios;

import com.marcos.proyectofinal.entidades.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT a from Usuario a WHERE a.correo LIKE :correo AND a.activo = true")
    public Usuario buscarPorEmail(@Param("correo") String correo);

    public List<Usuario> findAllByOrderByNombreAsc();

    public Optional<Usuario> findById(Long id);

    public Usuario getById(Long id);

    @Modifying
    @Query("UPDATE Usuario u SET u.nombre = :nombre, u.apellido = :apellido, u.correo = :correo where u.id = :id")
    public void modificarUsuario(@Param(value = "id") long id,
            @Param(value = "nombre") String nombre,
            @Param(value = "apellido") String apellido, 
            @Param(value = "correo") String correo);
    
    @Modifying
    @Query("UPDATE Usuario u SET u.nombre = :nombre, u.apellido = :apellido, u.correo = :correo, u.password = :password where u.id = :id")
    public void modificarUsuarioPassword(@Param(value = "id") long id,
            @Param(value = "nombre") String nombre,
            @Param(value = "apellido") String apellido, 
            @Param(value = "correo") String correo,
            @Param(value = "password") String password);
}
