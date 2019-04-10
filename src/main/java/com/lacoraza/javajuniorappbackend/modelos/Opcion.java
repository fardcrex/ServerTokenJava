package com.lacoraza.javajuniorappbackend.modelos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Opcion implements Serializable {
    private int id;
    private String name;
    private boolean estado;
    private Date created_at;
    private Date updated_at;
    private List<Usuario> usuarios;

    public Opcion() {
    }

    public Opcion(int id, String name, boolean estado, Date created_at, Date updated_at, List<Usuario> usuarios) {
        this.id = id;
        this.name = name;
        this.estado = estado;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.usuarios = usuarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String toString() {
        return "Opcion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", estado=" + estado +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", usuarios=" + usuarios +
                '}';
    }
}
