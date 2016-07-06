package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.nearbysocialevents.nearby975.MySql.UpdateMySql;

import java.sql.SQLException;

/**
 * Created by root on 6/25/16.
 */
public class ActivityCriarEvento extends Activity {

    ImageButton imbFotoEvento;
    EditText edtNomeEvento;
    EditText edtLocalEvento;
    EditText edtNumeroIngressos;
    EditText edtPrecoIngresso;
    EditText edtDataEvento;
    EditText edtHoraEvento;
    EditText edtDescricao;
    Button btnCriarEvento;


    //TODO: validar formulario

    /**
     * Validacao do formulario
     * @return
     */
    private boolean isFormValid(){


        return true;
    }



    private void chamaToast(String var){
        Toast.makeText(this, var, Toast.LENGTH_SHORT).show();
    }

    UpdateMySql job1;
    private boolean enviarEventoParaServidor(){
        job1 = new UpdateMySql(){
            @Override
            public void naResposta(Integer result) throws SQLException {
                if(result > 0) {
                    chamaToast("Evento criado com sucesso !!!");
                    finish();
                }else{
                    chamaToast("Ocorreu um erro !!! Tente de novo");
                }
            }
        };

        UsuarioSingleton user = UsuarioSingleton.getInstance();

        String sql = "INSERT INTO eventos  (`nome_evento`,`nome_dono`,`data_dia`,`data_hora`,`preco`,`local`,`descricao` ) VALUES ('" +
                edtNomeEvento.getText().toString()+
                "','" +
                user.getUsuario() +
                "','" +
                edtDataEvento.getText().toString() +
                "','" +
                edtHoraEvento.getText().toString() +
                "','" +
                edtPrecoIngresso.getText().toString() +
                "','" +
                edtLocalEvento.getText().toString() +
                "','" +
                edtDescricao.getText().toString() +
                "')";

        job1.execute(sql);
        chamaToast("Criando Evento .....");

        System.out.println("Evento_Criado");

        return true;
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


        if (resultCode == RESULT_OK) {
            Uri selectedImage = imageReturnedIntent.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();


            Bitmap image = BitmapFactory.decodeFile(filePath);
            imbFotoEvento.setImageBitmap(image);



        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_criacao_evento);

        imbFotoEvento = (ImageButton) findViewById(R.id.eventImage);
        edtNomeEvento = (EditText) findViewById(R.id.nomeEvento);
        edtLocalEvento = (EditText) findViewById(R.id.localEvento);
        edtNumeroIngressos = (EditText) findViewById(R.id.localEvento);
        edtPrecoIngresso = (EditText) findViewById(R.id.precoIngresso);
        edtDataEvento = (EditText) findViewById(R.id.dataEvento);
        edtHoraEvento = (EditText) findViewById(R.id.horaEvento);
        edtDescricao = (EditText) findViewById(R.id.descricaoEvento);
        btnCriarEvento = (Button) findViewById(R.id.button_conf_create_event);




        btnCriarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormValid()){
                    if(!enviarEventoParaServidor()){
                        //Toast.makeText(v.getContext(), "Falha na criacao do evento - Confira sua conexao com a internet.", Toast.LENGTH_LONG).show();


                    }else{
                        //Toast.makeText(v.getContext(), "Evento criado com sucesso!", Toast.LENGTH_LONG).show();
                        //((Activity)v.getContext()).finish();
                    }


                }



            }
        });




        imbFotoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(
                        Intent.createChooser(intent, "Complete action using"),
                        1);

            }
        });



    }





}
