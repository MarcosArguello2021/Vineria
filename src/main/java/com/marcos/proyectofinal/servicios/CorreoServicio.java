package com.marcos.proyectofinal.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CorreoServicio {

    @Autowired
    private JavaMailSender mailsender;

    @Value("${spring.mail.username}")
    private String mailPagina;

    private static final String SUBJECT = "Correo de bienvenida";
    private static final String TEXT = "Bienvenido a nuestra página. Gracias por registrarte";

    @Async
    public void enviarBienvenida(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(mailPagina);
        message.setSubject(SUBJECT);
        message.setText(TEXT);
        mailsender.send(message);
    }

    public void enviarThread(String to) {
        new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom(mailPagina);
            message.setSubject(SUBJECT);
            message.setText(TEXT);
            mailsender.send(message);
        }).start();
    }

    @Async
    public void notificar(String nombre, String apellido,
            String consulta, String correo) {

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(mailPagina);
        mensaje.setFrom(mailPagina);
        mensaje.setSubject("Consulta de: " + nombre + " " + apellido + " Correo: " + correo);
        mensaje.setText(consulta);
        mailsender.send(mensaje);
    }
}
