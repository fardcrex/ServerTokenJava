package com.lacoraza.javajuniorappbackend.bdconnect.daoPSQL;

import com.lacoraza.javajuniorappbackend.bdconnect.i_dao.InterfaceUsuario;
import com.lacoraza.javajuniorappbackend.modelos.Usuario;

import java.util.ArrayList;

public class UsuarioPSQL  implements InterfaceUsuario {
    @Override
    public Usuario insertarPOST(Usuario o) {
        return null;
    }

    @Override
    public Usuario modificarPUT(Usuario o, Integer integer) {
        return null;
    }

    @Override
    public Usuario eliminarDELETE(Integer integer) {
        return null;
    }

    @Override
    public Usuario obtenerPorIDGet(Integer integer) {
        return null;
    }

    @Override
    public ArrayList<Usuario> obtenerGET() {
        return null;
    }
}
