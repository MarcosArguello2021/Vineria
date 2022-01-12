package com.marcos.proyectofinal.servicios;

import com.marcos.proyectofinal.entidades.Foto;
import com.marcos.proyectofinal.repositorios.FotoRepositorio;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    
    @Transactional
    public Foto guardar(MultipartFile archivo) throws IOException {
        if (archivo != null && !archivo.isEmpty()) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
               
                foto.setContenido(archivo.getBytes());
                System.out.println(foto.getNombre());
                return fotoRepositorio.save(foto);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional
    public Foto actualizar(String idFoto, MultipartFile archivo) throws IOException {
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                if (idFoto != null) {
                    foto = fotoRepositorio.getById(idFoto);
                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return fotoRepositorio.save(foto);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
      @Transactional(readOnly = true)
    public List<Foto> listarTodos() {
        return fotoRepositorio.findAll();
    }
}
