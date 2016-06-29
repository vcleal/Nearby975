package com.nearbysocialevents.nearby975;

/**
 * Created by root on 6/25/16.
 * Singleton para manter informacoes de usuario usadas em diferentes activities
 */
public class UsuarioSingleton {
    private static UsuarioSingleton ourInstance = new UsuarioSingleton();
    private String usuario;



    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }







    public static UsuarioSingleton getInstance() {
        return ourInstance;
    }

    private UsuarioSingleton() {
    }






}
