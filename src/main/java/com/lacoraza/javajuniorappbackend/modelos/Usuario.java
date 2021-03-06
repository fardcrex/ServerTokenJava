package com.lacoraza.javajuniorappbackend.modelos;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

public class Usuario implements Serializable {
    private int id;
    private String nombre;
    private String imagen;
    private String correo;
    private String password;
    private Role role;
    private Date update_at;
    private Date created_at;
    private List<Opcion> pciones;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String imagen, String correo, String password, Role role, Date update_at, Date created_at) throws NoSuchAlgorithmException {
        this.id = id;
        this.nombre = nombre.trim();
        this.imagen = imagen.trim();
        this.correo = correo.trim();
        this.password = algoritmoDeEncriptacion(password).trim();
        this.role = role;
        this.update_at = update_at;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {

        this.password = algoritmoDeEncriptacion(password);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public List<Opcion> getPciones() {
        return pciones;
    }

    public void setPciones(List<Opcion> pciones) {
        this.pciones = pciones;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", correo='" + correo + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", update_at=" + update_at +
                ", created_at=" + created_at +
                '}';
    }
    private String algoritmoDeEncriptacion(String password){
        if (password.equalsIgnoreCase("")){
            return "";
        }
        password ="zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+password;
        password.trim();
        byte[] miClaveBytes = password.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(miClaveBytes);
         password = Jwts.builder().setHeaderParam("typ", "JWT").signWith(key, SignatureAlgorithm.HS256)
                .setSubject("pass")
                .compact();
        int posicion = password.indexOf(".");
        password=password.substring(posicion+1);
        posicion = password.indexOf(".");
        password=password.substring(posicion+1);
        return password;
    }
}
