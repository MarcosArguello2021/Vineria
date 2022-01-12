package com.marcos.proyectofinal.controladores;

import com.marcos.proyectofinal.entidades.Articulo;
import com.marcos.proyectofinal.entidades.Usuario;
import com.marcos.proyectofinal.repositorios.ArticuloRepositorio;
import com.marcos.proyectofinal.repositorios.UsuarioRepositorio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foto")
public class FotoController {

    @Autowired
    private ArticuloRepositorio articuloRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @GetMapping("/articulo/{id}")
    public ResponseEntity<byte[]> fotoArticulo(@PathVariable Long id) throws Exception {
        try {

            Articulo articulo = articuloRepositorio.getById(id);
            if (articulo.getImagen() == null) {
                throw new Exception("El articulo no tiene una foto.");
            }
            byte[] foto = articulo.getImagen().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(FotoController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<byte[]> fotoUsuario(@PathVariable Long id) throws Exception {
        try {
           
            Usuario usuario = usuarioRepositorio.getById(id);
            if (usuario.getImagen() == null) {
                throw new Exception("El Usuario no tiene una foto.");
            }
            byte[] foto = usuario.getImagen().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            
            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(FotoController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
