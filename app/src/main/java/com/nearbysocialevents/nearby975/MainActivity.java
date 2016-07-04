package com.nearbysocialevents.nearby975;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {


    private Button buttonLogin;
    private Button buttonCriarConta;
    public static final String MY_PREFS_NAME = "LoginInfo";
    private boolean isLogged;
    private UsuarioSingleton user = UsuarioSingleton.getInstance();


    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        isLogged = prefs.getBoolean("logged",false);
        if(isLogged) {
            user.setUsuario(prefs.getString("user", "ERRO_USUARIO"));
            Intent intent = new Intent(this, ActivityTelaPrincipal.class);
            startActivity(intent);
            this.finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ecra_salpico);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        isLogged = prefs.getBoolean("logged",false);
        if(isLogged){
            user.setUsuario(prefs.getString("user","ERRO_USUARIO"));
            user.setNome(prefs.getString("nome","ERRO_USUARIO"));
            Intent intent = new Intent(this, ActivityTelaPrincipal.class);
            startActivity(intent);
            this.finish();

        }else{

            buttonLogin = (Button) findViewById(R.id.button_abrir_login);
            buttonCriarConta = (Button) findViewById(R.id.button_cria_conta);

            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ActivityLogin.class);
                    startActivity(intent);


                }
            });

            buttonCriarConta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ActivityCriarConta.class);
                    startActivity(intent);
                }
            });







        }












//        Intent intent = new Intent(this, ActivityValidarIngresso.class);
//        startActivity(intent);


    }
}
