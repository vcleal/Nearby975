package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by root on 6/3/16.
 */
public class ActivityTelaPrincipal extends Activity{

    private TextView nomeUsuario;
    private ImageButton fotoUsuario;
    private Button buttonAgenda;
    private Button buttonMyEvents;
    private Button buttonCreateEvent;
    private Button buttonSearchEvent;
    private Button buttonAbrirContatos;
    private Button buttonLogout;
    private UsuarioSingleton user;
    DialogInterface.OnClickListener dialogClickListener;
    public static final String MY_PREFS_NAME = "LoginInfo";
    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        ctx=this;
        user = UsuarioSingleton.getInstance();

        nomeUsuario = (TextView)findViewById(R.id.text_nome);
        fotoUsuario = (ImageButton) findViewById(R.id.image_user_button);
        buttonAgenda = (Button) findViewById(R.id.button_agenda);
        buttonMyEvents = (Button) findViewById(R.id.button_my_events);
        buttonCreateEvent = (Button) findViewById(R.id.button_create_event);
        buttonSearchEvent = (Button) findViewById(R.id.button_search_event);
        buttonAbrirContatos = (Button) findViewById(R.id.button_abrir_lista_contatos);
        buttonLogout = (Button) findViewById(R.id.button_logout);

        /**
         * preencher nome do usuario e foto
         */
        //TODO: preencher foto

        nomeUsuario.setText(user.getNome());




        /**
         * Todos os click listener dos botoes estao abaixo
         */
        /**
         * LOGOUT
         */
        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("logged", false);
                        editor.commit();
                        ((Activity)ctx).finish();
                        dialog.dismiss();


                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };


        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Deseja realmente realizar logout?").setPositiveButton("Sim", dialogClickListener)
                        .setNegativeButton("Nao", dialogClickListener).show();
            }
        });


        /**
         * ALTERACAO DE DADOS PESSOAIS
         */
        fotoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityAlterarDadosPessoais.class);
                startActivity(intent);
            }
        });


        /**
         * AGENDA DE EVENTOS
         */
        buttonAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AgendaActivity.class);
                startActivity(intent);
            }
        });


        /**
         * MEUS EVENTOS
         */
        buttonMyEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityMeusEventos.class);
                startActivity(intent);
            }
        });

        /**
         * CRIAR EVENTO
         */

        buttonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityCriarEvento.class);
                startActivity(intent);
            }
        });


        /**
         * BUSCA DE EVENTO
         */

        buttonSearchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityBuscarEvento.class);
                startActivity(intent);
            }
        });


        /**
         * ABRIR CONTATOS
         */

        buttonAbrirContatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityListaContatos.class);
                startActivity(intent);
            }
        });








    }
}
