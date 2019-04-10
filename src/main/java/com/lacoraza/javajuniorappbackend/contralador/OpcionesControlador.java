package com.lacoraza.javajuniorappbackend.contralador;

import com.lacoraza.javajuniorappbackend.bdconnect.daoPSQL.OpcionPSQL;
import com.lacoraza.javajuniorappbackend.bdconnect.daoPSQL.UsuarioPSQL;
import com.lacoraza.javajuniorappbackend.modelos.Opcion;
import com.lacoraza.javajuniorappbackend.modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

public class OpcionesControlador {

    OpcionPSQL opcionPSQL = new OpcionPSQL();



    public List<Opcion> getOpciones() {

        ArrayList<Opcion> LIST = opcionPSQL.obtenerGET();
        return  LIST;

    }
}
