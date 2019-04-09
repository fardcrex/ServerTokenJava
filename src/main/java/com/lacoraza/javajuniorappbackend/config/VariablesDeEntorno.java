package com.lacoraza.javajuniorappbackend.config;

import javax.crypto.SecretKey;
import java.util.Map;

public final class VariablesDeEntorno {

    private static final VariablesDeEntorno instancia = new VariablesDeEntorno();

    private VariablesDeEntorno(){}
    public static VariablesDeEntorno getInstancia(){
        return instancia;
    }

    ///////////////////Variables de Entorno///////////////////////////////

    private Map<String,String> propiedadesBD;
    private SecretKey keyJWT;


    ///////////////////GETTERS AND SETTERS///////////////////////////

    public void setConfigBD(Map<String,String> propiedades) {
        this.propiedadesBD = propiedades;
    }

    public Map<String,String> getConfigBD() {
        return this.propiedadesBD;
    }


    public SecretKey getKeyJWT() {
        return keyJWT;
    }

    public void setKeyJWT(SecretKey keyJWT) {
        this.keyJWT = keyJWT;
    }

}

