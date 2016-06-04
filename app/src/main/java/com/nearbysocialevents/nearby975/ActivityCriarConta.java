package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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


    private boolean validaData(){

    }


    /**
     * Testa se o formulario esta preenchido
     * @return
     */
    private boolean isFilled(){
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
    private boolean enviaCadastroServidor(){


        return true;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cadastro);
        txtNome = (EditText) findViewById(R.id.text_nome);
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

                        Toast.makeText(v.getContext(), "Cadastro Efetuado com sucesso!", Toast.LENGTH_SHORT).show();


                    }



                }else{
                    //do nothing
                }


            }
        });






    }
}
