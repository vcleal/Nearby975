package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nearbysocialevents.nearby975.MySql.SendMySql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 6/3/16.
 */
public class ActivityLogin extends Activity{

    private EditText mail;
    private EditText pwd;
    private Button confirm;
    public static final String MY_PREFS_NAME = "LoginInfo";
    private UsuarioSingleton user;
    Context ctx;
    SendMySql job1;


    /**
     * realiza validacao de usuario e senha atraves do servidor
     * @param email
     * @param senha
     * @return
     */
    private boolean validaLogin(String email, String senha){

        job1 = new SendMySql() {
            @Override
            public void naResposta(ResultSet result) throws SQLException {
                if(!result.next()){
                    //nao retornou nada
                    Toast.makeText(ctx,"Senha ou usuario invalidos",Toast.LENGTH_LONG).show();
                    return;
                }



                do {
                    Log.i("MYSQL", "Resultado: " + result.getString("nome"));
                    SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("logged", true);
                    editor.putString("user",mail.getText().toString());
                    user = UsuarioSingleton.getInstance();
                    user.setUsuario(mail.getText().toString());
                    user.setNome(result.getString("nome"));
                    editor.commit();
                    ((Activity)ctx).finish();
                    Intent intent = new Intent(((Activity)ctx), ActivityTelaPrincipal.class);
                    startActivity(intent);

                }while(result.next());
            }
        };
        job1.execute("SELECT * FROM usuario WHERE email = '"+email + "' AND  senha = '"+senha +"'");
        Toast.makeText(ctx,"Validando Usuário",Toast.LENGTH_LONG).show();
        System.out.println("Login_execute");




        return false;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        mail =  (EditText) findViewById(R.id.edtMailLogin);
        pwd = (EditText) findViewById(R.id.edtPwdLogin);
        confirm = (Button) findViewById(R.id.button_login);
        ctx = this;
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaLogin(mail.getText().toString(),pwd.getText().toString())){


                }



            }
        });



    }
}
