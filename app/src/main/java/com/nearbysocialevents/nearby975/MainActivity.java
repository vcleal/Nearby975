package com.nearbysocialevents.nearby975;

import android.app.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        SendfeedbackJob job = new SendfeedbackJob();
        job.execute();

        /*
        dbMySQL dbmysql = new dbMySQL();
        dbmysql.conectarMySQL("127.0.0.1", "3306", "test", "root", "bala123"); // ip do servidor mysql, porta, banco, usuário, senha
        dbmysql.queryMySQL();
        dbmysql.desconectarMySQL();
        */

        setContentView(R.layout.layout_tela_chat);


        //Intent intent = new Intent(this, ActivityValidarIngresso.class);
        //startActivity(intent);


    }
}

