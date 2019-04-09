package com.lacoraza.javajuniorappbackend.bdconnect.i_dao;

import java.util.ArrayList;

public interface InterfazGeneral<K,O> {
    public O insertarPOST(O o);
    public O modificarPUT(O o,K k);
    public O eliminarDELETE(K k);
    public O obtenerPorIDGet(K k);
    public ArrayList<O> obtenerGET();
}