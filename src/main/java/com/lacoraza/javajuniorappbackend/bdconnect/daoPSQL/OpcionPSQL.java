package com.lacoraza.javajuniorappbackend.bdconnect.daoPSQL;

import com.lacoraza.javajuniorappbackend.bdconnect.i_dao.InterfaceOpcion;
import com.lacoraza.javajuniorappbackend.modelos.Opcion;
import com.lacoraza.javajuniorappbackend.modelos.Role;
import com.lacoraza.javajuniorappbackend.modelos.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OpcionPSQL implements InterfaceOpcion {

    private String  OBTENER_ALL = "SELECT * FROM opciones ";
    private String  OBTENER_ById_User =" SELECT opciones.id ,opciones.name,opciones.estado,opciones.created_at,opciones.updated_at  FROM opciones INNER JOIN usuarios_opciones on opciones.id = usuarios_opciones.opcion_id where usuario_id = ?";
    private Connection conexion;
    private PreparedStatement sentencia;
    private ResultSet resultados;

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
    public ArrayList<Opcion> obtenerGET(){
    ArrayList<Opcion> OpcionList = new ArrayList<>();

        try {
        conexion = new ConexionPSQL().getConnection();
        sentencia = conexion.prepareStatement(OBTENER_ALL);
        resultados = sentencia.executeQuery();

        while (resultados.next()){
            Opcion opcion = new Opcion();
            opcion.setId(resultados.getInt("id"));
            opcion.setName(resultados.getString("name"));
            opcion.setEstado(resultados.getBoolean("estado"));
            opcion.setCreated_at(resultados.getDate(4));
            opcion.setUpdated_at(resultados.getDate(5));
            OpcionList.add(opcion);
        }
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(UsuarioPSQL.class.getName()).log(Level.SEVERE, null, ex);
    } catch (
    SQLException e) {
        e.printStackTrace();
    }  finally {
        cerrarConexiones();
    }
        return OpcionList;
    }



    public ArrayList<Opcion> getOpcionesbyId(int id) {
        ArrayList<Opcion> OpcionList = new ArrayList<>();

        try {
            conexion = new ConexionPSQL().getConnection();
            sentencia = conexion.prepareStatement(OBTENER_ById_User);
            sentencia.setInt(1,id);
            resultados = sentencia.executeQuery();

            while (resultados.next()){
                Opcion opcion = new Opcion();
                opcion.setId(resultados.getInt("id"));
                opcion.setName(resultados.getString("name"));
                opcion.setEstado(resultados.getBoolean("estado"));
                opcion.setCreated_at(resultados.getDate(4));
                opcion.setUpdated_at(resultados.getDate(5));
                OpcionList.add(opcion);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioPSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }  finally {
            cerrarConexiones();
        }
        return OpcionList;

    }


    private void cerrarConexiones(){
        try {
            if (resultados != null){
                resultados.close();
            }
            if (sentencia != null){
                sentencia.close();
            }
            if (conexion != null){
                conexion.close();
            }
        }catch(SQLException e)
        {
            System.out.println(e);
        }
    }
}
