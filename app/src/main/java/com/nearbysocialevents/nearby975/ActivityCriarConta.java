package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nearbysocialevents.nearby975.MySql.SendMySql;
import com.nearbysocialevents.nearby975.MySql.UpdateMySql;
import com.nearbysocialevents.nearby975.MySql.dbMySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 6/3/16.
 */
public class ActivityCriarConta extends Activity{

    private EditText txtNome;
    private EditText txtMail;
    private EditText txtPwd;
    private EditText txtConfPwd;
    private EditText txtPhone;
    private EditText txtBirthDate;
    private Spinner spnSexo;
    private Button buttonConfirm;
    private Context ctx;

    /**
     * Valida um endereco de email
     * @param target
     * @return
     */
    public final static boolean isValidEmail(String target){
        if(TextUtils.isEmpty(target)){
            return false;
        }else{
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

//TODO validar string com data
    private boolean validaData(){
        return true;
    }



    boolean isdebugging = true;
    /**
     * Testa se o formulario esta preenchido
     * @return
     */
    private boolean isFilled(){
        if(isdebugging) return true;
        if(txtNome.getText().equals("") || txtNome.getText().length() < 2){
            Toast.makeText(this, "Nome Invalido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isValidEmail(txtMail.getText().toString())){
            Toast.makeText(this, "E-mail Invalido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtPwd.getText().toString().isEmpty()){
            Toast.makeText(this, "Insira uma senha", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtPwd.getText().toString().length() < 6){
            Toast.makeText(this, "Senha deve ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtConfPwd.getText().toString().isEmpty()){
            Toast.makeText(this, "Confirme sua senha", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!txtConfPwd.getText().toString().equals(txtPwd.getText().toString())){
            Toast.makeText(this, "Senhas inseridas nao sao iguais", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtPhone.getText().length() < 10){
            Toast.makeText(this, "Telefone Incorreto - Deve conter apenas numeros e o DDD", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtBirthDate.getText().length()!=10){
            Toast.makeText(this, "Data de nascimento incorreta - Formato: dd/mm/yyyy", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    /**
     * Esta funcao deve enviar os dados ao servidor e retornar se o cadastro foi feito com sucesso ou nao
     * @return
     */


    UpdateMySql job1;
    private boolean enviaCadastroServidor(){
        job1 = new UpdateMySql();

        String sql = "INSERT INTO usuario  (`nome`,`email`,`senha`,`telefone`,`data_nascimento` ) VALUES ('" +
        txtNome.getText().toString()+
        "','" +
        txtMail.getText().toString() +
        "','" +
        txtPwd.getText().toString() +
        "','" +
        txtPhone.getText().toString() +
        "','" +
        txtBirthDate.getText().toString() +

        "')";




        //sql = "INSERT INTO usuario (`senha`) VALUES ('blablabla')";
        job1.execute(sql);


        System.out.println("teste");

        return true;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cadastro);
        txtNome = (EditText) findViewById(R.id.text_nome);
        ctx = this;

        txtMail = (EditText) findViewById(R.id.text_email);
        txtPwd = (EditText) findViewById(R.id.text_pwd);
        txtConfPwd = (EditText) findViewById(R.id.text_pwd_conf);
        txtPhone = (EditText) findViewById(R.id.text_telefone);
        txtBirthDate = (EditText) findViewById(R.id.text_birth);
        spnSexo = (Spinner) findViewById(R.id.spinner_sexo_cadastro);
        buttonConfirm = (Button) findViewById(R.id.button_conf_cadastro);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFilled()){
                    if(enviaCadastroServidor()){




                    }



                }else{
                    //do nothing
                }


            }
        });






    }
}
