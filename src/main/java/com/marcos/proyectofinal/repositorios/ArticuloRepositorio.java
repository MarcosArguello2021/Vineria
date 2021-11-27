package com.marcos.proyectofinal.repositorios;

import com.marcos.proyectofinal.entidades.Articulo;
import com.marcos.proyectofinal.enumeracion.Variedad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepositorio extends JpaRepository<Articulo, String> {

    public List<Articulo> findAllByOrderByNombreAsc();

    @Query("SELECT a from Articulo a WHERE a.variedad LIKE :variedad AND a.activo = true")
    public List<Articulo> buscarPorVariedad(@Param("variedad") Variedad variedad);

    public List<Articulo> findByNombreContainingOrderByNombre(String nombre);
}
