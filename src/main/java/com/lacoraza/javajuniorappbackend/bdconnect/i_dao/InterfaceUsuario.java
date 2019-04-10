package com.lacoraza.javajuniorappbackend.bdconnect.i_dao;

import com.lacoraza.javajuniorappbackend.modelos.Usuario;

public interface InterfaceUsuario  extends InterfazGeneral<Integer, Usuario>{
    public Usuario validarUsuario(String uname,String uclave);
}
