package com.nearbysocialevents.nearby975.MySql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.app.Activity;
import android.util.Log;

public class dbMySQL{
    private Connection conn = null;
    private Statement st;
    private ResultSet rs;
    //private String sql;

    public void conectarMySQL(){
        //localhost
        /*
        String host = "10.0.2.2";
        String banco = "test";
        String usuario = "root";
        String senha = "bala123";
        */

        //server amazon
        //String host = "ea975.fee.unicamp.br";
        String host = "50.16.221.234";
        String banco = "EA975";
        String usuario = "EA975";
        String senha = "ea975";

        //server unicamp
        /*
        //String host = "ea975.fee.unicamp.br";
        String host = "143.106.148.113";
        String banco = "open";
        String usuario = "open";
        String senha = "open123";
        */

        String porta = "3306";

        System.out.println("teste2");
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("teste3");
        }catch(Exception erro){
            Log.e("MYSQL","Erro: "+erro);
        }
        try{
            System.out.println("teste4");
            conn=DriverManager.getConnection("jdbc:mysql://"+host+":"+porta+"/"+banco,usuario,senha);
            System.out.println("teste5");
            Log.i("MYSQL","Conectado.");
        }catch(Exception erro){
            Log.e("MYSQL","Erro: "+erro);
        }
    }

    public void desconectarMySQL(){
        try {
            conn.close();
            Log.i("MYSQL","Desconectado.");
        } catch (Exception erro) {
            Log.e("MYSQL","Erro: "+erro);
        }
    }

    public ResultSet querysMySQL(String sql){
        try{
            st=conn.createStatement();
            //sql="SELECT * FROM teste WHERE 1";
            rs=st.executeQuery(sql);
            //ResultSet vis = rs;
            /*
            vis.first();
            do {
                Log.i("MYSQL", "Resultado: " + vis.getString("nome"));
            }while(vis.next());
            */
        } catch (Exception erro){
            Log.e("MYSQL","Erro: "+erro);
        }
        return rs;
    }

}