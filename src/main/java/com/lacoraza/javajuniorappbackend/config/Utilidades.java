package com.lacoraza.javajuniorappbackend.config;

import javax.crypto.SecretKey;
import java.util.Map;
import java.util.Properties;

public final class Utilidades {

    private static final Utilidades instancia = new Utilidades();

    private Utilidades(){}
    public static Utilidades getInstancia(){
        return instancia;
    }

    ///////////////////Propiedades///////////////////////////////

    private Map<String,String> propiedadesBD;
    private SecretKey keyJWT;





    ///////////////////GETTERS AND SETTERS///////////////////////////

    public void setConfiguraBaseDeDatos(Map<String,String> propiedades) {
        this.propiedadesBD = propiedades;
    }

    public Map<String,String> getConfiguracionBaseDeDatos() {
        return this.propiedadesBD;
    }


    public SecretKey getKeyJWT() {
        return keyJWT;
    }

    public void setKeyJWT(SecretKey keyJWT) {
        this.keyJWT = keyJWT;
    }

}

