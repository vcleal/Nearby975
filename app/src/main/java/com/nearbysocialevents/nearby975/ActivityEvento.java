package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by root on 6/25/16.
 */
public class ActivityEvento extends Activity{
    Evento event;
    UsuarioSingleton currentUser = UsuarioSingleton.getInstance();
    TextView txtNomeEvento;

    TextView txtEnderecoEvento;
    TextView txtHoraEvento;
    TextView txtPrecoEvento;
    TextView txtDescricaoEvento;
    Button btnComprarIngresso;
    Button btnGerenciarEvento;
    Button btnEnviarIngresso;
    Button btnOpenChat;
    Button btnOpenFotos;
    Button btnValidarIngresso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal_evento);
        event = (Evento) getIntent().getSerializableExtra("evento");

        txtNomeEvento = (TextView)findViewById(R.id.event_name);
        txtHoraEvento = (TextView)findViewById(R.id.hora);
        txtEnderecoEvento = (TextView)findViewById(R.id.endereco);
        txtPrecoEvento = (TextView)findViewById(R.id.preco);
        txtDescricaoEvento = (TextView)findViewById(R.id.descricao);
        btnComprarIngresso = (Button)findViewById(R.id.button_comprar_ingresso);
        btnGerenciarEvento = (Button)findViewById(R.id.button_gerenciar);
        btnEnviarIngresso = (Button)findViewById(R.id.button_enviar);
        btnOpenChat = (Button)findViewById(R.id.button_chat);
        btnOpenFotos = (Button)findViewById(R.id.button_fotos);
        btnValidarIngresso = (Button)findViewById(R.id.button_validar);


        /**
         * se eu nao for o dono, nao posso gerenciar
         */
        if(!currentUser.getUsuario().equals(event.getOwner())){
            btnGerenciarEvento.setVisibility(View.INVISIBLE);
            btnGerenciarEvento.setEnabled(false);
        }
        /**
         * se eu nao for atendente ou dono do evento, nao posso validar ingresso
         */
        if(!event.getAtendentes().contains(currentUser.getUsuario()) && !currentUser.getUsuario().equals(event.getOwner())){
            btnValidarIngresso.setVisibility(View.INVISIBLE);
            btnValidarIngresso.setEnabled(false);
        }

        txtNomeEvento.setText(event.nome);
        txtDescricaoEvento.setText(event.descricao);
        txtPrecoEvento.setText("R$"+Float.toString(event.preco));
        txtEnderecoEvento.setText(event.local);
        txtHoraEvento.setText(DateFormat.format("dd/MM/yyyy - hh:mm",event.data).toString());


        //TODO: colocar os click listeners dos botoes




    }
}
