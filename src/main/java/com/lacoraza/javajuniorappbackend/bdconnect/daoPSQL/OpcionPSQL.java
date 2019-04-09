package com.lacoraza.javajuniorappbackend.bdconnect.daoPSQL;

import com.lacoraza.javajuniorappbackend.bdconnect.i_dao.InterfaceOpcion;
import com.lacoraza.javajuniorappbackend.modelos.Opcion;

import java.util.ArrayList;

public class OpcionPSQL implements InterfaceOpcion {
    @Override
    public Opcion insertarPOST(Opcion o) {
        return null;
    }

    @Override
    public Opcion modificarPUT(Opcion o, Integer integer) {
        return null;
    }

    @Override
    public Opcion eliminarDELETE(Integer integer) {
        return null;
    }

    @Override
    public Opcion obtenerPorIDGet(Integer integer) {
        return null;
    }

    @Override
    public ArrayList<Opcion> obtenerGET() {
        return null;
    }
}
