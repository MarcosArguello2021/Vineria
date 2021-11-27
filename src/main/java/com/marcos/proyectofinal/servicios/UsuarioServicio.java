package com.marcos.proyectofinal.servicios;

import com.marcos.proyectofinal.entidades.Usuario;
import com.marcos.proyectofinal.enumeracion.Rol;
import com.marcos.proyectofinal.excepciones.WebException;
import com.marcos.proyectofinal.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

   @Autowired
   private UsuarioRepositorio usuarioRepositorio;
   
//   @Autowired
//    private CorreoServicio correoServicio;
   
   @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {WebException.class, Exception.class})
    public Usuario guardar(String nombre, String apellido, String correo,
            String password) throws WebException {

//        validar(nombre, apellido, correo, clave, rol);

        Usuario entidad = new Usuario();

        entidad.setNombre(nombre);
        entidad.setApellido(apellido);
        entidad.setCorreo(correo);
        entidad.setPassword(new BCryptPasswordEncoder().encode(password));
        entidad.setRol(Rol.USUARIO);
        entidad.setActivo(true);
        //correoServicio.enviarThread(entidad.getCorreo());
        return usuarioRepositorio.save(entidad);
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {

        Usuario user = usuarioRepositorio.buscarPorEmail(correo);

        if (user != null) {
            List<GrantedAuthority> permissions = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRol().toString());
            permissions.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuario", user);
            return new org.springframework.security.core.userdetails.User(user.getCorreo(), user.getPassword(),
                    permissions);
        }
        return null;
    }
}
