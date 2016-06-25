package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by root on 6/3/16.
 */
public class ActivityLogin extends Activity{

    private EditText mail;
    private EditText pwd;
    private Button confirm;
    public static final String MY_PREFS_NAME = "LoginInfo";
    private UsuarioSingleton user;



    /**
     * realiza validacao de usuario e senha atraves do servidor
     * @param email
     * @param senha
     * @return
     */
    private boolean validaLogin(String email, String senha){
        if(email.equals("admin") && senha.equals("admin")){
            return true;
        }

        //TODO validar atraves do servidor
        /**
         *
         */



        return false;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        mail =  (EditText) findViewById(R.id.edtMailLogin);
        pwd = (EditText) findViewById(R.id.edtPwdLogin);
        confirm = (Button) findViewById(R.id.button_login);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaLogin(mail.getText().toString(),pwd.getText().toString())){
                    SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("logged", true);
                    editor.putString("user",mail.getText().toString());
                    user = UsuarioSingleton.getInstance();
                    user.setUsuario(mail.getText().toString());
                    editor.commit();
                    ((Activity)v.getContext()).finish();
                    Intent intent = new Intent(v.getContext(), ActivityTelaPrincipal.class);
                    startActivity(intent);

                }



            }
        });



    }
}
