package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    Button btnVerIngresso;
    UsuarioSingleton user;
    AlertDialog.Builder alert;


    /**
     * Realiza a transferencia no servidor
     * @return
     */
    private boolean enviarIngressoParaOutroUsuario(String usuarioDestino){
        String identificadorEvento = event.nome;
        String identificadorUsuario = user.getUsuario();

        //TODO Enviar para servidor e verificar se deu certo

        return true;
    }


    private boolean euTenhoIngresso(){
        String meuUsuario = user.getUsuario();
        String nomeEvento = event.nome;
        //TODO: Verifcar se eu tenho ingresso pra esse evento

        return true;
    }









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal_evento);
        event = (Evento) getIntent().getSerializableExtra("evento");
        alert= new AlertDialog.Builder(this);
        user = UsuarioSingleton.getInstance();
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
        btnVerIngresso = (Button) findViewById(R.id.button_ver_ingresso);


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

        /**
         * se eu nao tenho ingresso, nao posso ve-lo nem envia-lo
         */
        if(!euTenhoIngresso()){
            btnVerIngresso.setVisibility(View.INVISIBLE);
            btnVerIngresso.setEnabled(false);
            btnEnviarIngresso.setVisibility(View.INVISIBLE);
            btnEnviarIngresso.setEnabled(false);

        }





        txtNomeEvento.setText(event.nome);
        txtDescricaoEvento.setText(event.descricao);
        txtPrecoEvento.setText("R$"+Float.toString(event.preco));
        txtEnderecoEvento.setText(event.local);
        txtHoraEvento.setText(DateFormat.format("dd/MM/yyyy - hh:mm",event.data).toString());


        /**
         * COMPRAR INGRESSO
         */
        btnComprarIngresso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Aviso")
                        .setMessage("Funcionalidade de pagamento nao implementada pois nao tivemos acesso liberado a API de pagamentos do google ainda. O ingresso foi adiconado a sua conta com sucesso!")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .show();
                        //TODO: Adicionar ingresso para o evento a conta do usuario atual no servidor


            }
        });


        /**
         * GERENCIAR EVENTO
         */
        btnGerenciarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityGerenciarEvento.class);
                intent.putExtra("evento_id",event.getId());
                startActivity(intent);
            }
        });

        /**
         * ENVIAR INGRESSO
         */
        btnEnviarIngresso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText edittext = new EditText(v.getContext());
                alert.setMessage("Digite o nome do usuario para enviar o ingresso:");
                alert.setTitle("Enviar Ingresso");

                alert.setView(edittext);

                alert.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String usuarioParaEnviar = edittext.getText().toString();


                        if(enviarIngressoParaOutroUsuario(usuarioParaEnviar)){
                            Toast.makeText(alert.getContext(), "Ingresso enviado com sucesso!", Toast.LENGTH_LONG).show();


                        }else{
                            Toast.makeText(alert.getContext(), "Falha no envio do Ingresso. Verifique conexao com a internet.", Toast.LENGTH_LONG).show();


                        }


                    }
                });

                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });


        /**
         * CHAT DO EVENTO
         */
        btnOpenChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityChat.class);
                intent.putExtra("evento_id",event.getId());
                startActivity(intent);
            }
        });


        /**
         * FOTOS DO EVENTO
         */

        btnOpenFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityFotosEvento.class);
                intent.putExtra("evento_id",event.getId());
                startActivity(intent);
            }
        });


        /**
         * VALIDAR INGRESSOS
         */
        btnValidarIngresso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityValidarIngresso.class);
                intent.putExtra("evento_id",event.getId());
                startActivity(intent);
            }
        });


        /**
         * VER INGRESSO
         */
        btnVerIngresso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityVerIngresso.class);
                //TODO:Pegar o ID do ingresso e jogar na variavel abaixo:
                String ingressoId = "12345678";
                intent.putExtra("ingresso_id",ingressoId);
                intent.putExtra("evento_id",event.getId());
                startActivity(intent);

            }
        });


    }
}
