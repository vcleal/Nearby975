package com.nearbysocialevents.nearby975;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.app.Activity;
import android.util.Log;

public class dbMySQL extends Activity{
    private Connection conn = null;
    private Statement st;
    private ResultSet rs;
    private String sql;

    public void conectarMySQL(String host, String porta, String banco, String usuario, String senha){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch(Exception erro){
            Log.e("MYSQL","Erro: "+erro);
        }
        try{
            conn=DriverManager.getConnection("jdbc:mysql://"+host+":"+porta+"/"+banco,usuario,senha);
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

    public void queryMySQL(){
        try{
            st=conn.createStatement();
            sql="SELECT * FROM teste WHERE 1";
            rs=st.executeQuery(sql);
            rs.first();
            do {
                Log.i("MYSQL", "Resultado: " + rs.getString("nome"));
            }while(rs.next());
        } catch (Exception erro){
            Log.e("MYSQL","Erro: "+erro);
        }
    }

}