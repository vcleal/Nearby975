package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nearbysocialevents.nearby975.MySql.SendMySql;
import com.nearbysocialevents.nearby975.MySql.UpdateMySql;

import org.w3c.dom.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 6/25/16.
 */
public class ActivityEvento extends Activity{
    Evento event;
    UsuarioSingleton currentUser = UsuarioSingleton.getInstance();
    TextView txtNomeEvento;
    TextView txtNumIngressos;
    TextView txtEnderecoEvento;
    TextView txtHoraEvento;
    TextView txtPrecoEvento;
    TextView txtNumAmigos;
    TextView txtDescricaoEvento;
    Button btnComprarIngresso;
    Button btnGerenciarEvento;
    Button btnEnviarIngresso;
    Button btnOpenChat;
    ImageView fotoCapa;
    Button btnOpenFotos;
    Button btnValidarIngresso;
    Button btnVerIngresso;
    UsuarioSingleton user;
    AlertDialog.Builder alert;
    Context ctx;
    UpdateMySql job2;

    /**
     * Realiza a transferencia no servidor
     * @return
     */
    private boolean enviarIngressoParaOutroUsuario(String usuarioDestino){
        String identificadorEvento = event.nome;
        String identificadorUsuario = user.getUsuario();
        job2 = new UpdateMySql(){
            @Override
            public void naResposta(Integer result) throws SQLException {
                if(result > 0) {
                    //          Toast.makeText(ctx,"deu certo",Toast.LENGTH_SHORT).show();

                        Toast.makeText(alert.getContext(), "Ingresso enviado com sucesso!", Toast.LENGTH_LONG).show();
                    contarIngressos();
                    contarAmigos();

                }else{
                    //chamaToast("Ocorreu um erro !!! Tente de novo");
                    //        Toast.makeText(ctx,"nao deu certo",Toast.LENGTH_SHORT).show();
                    Toast.makeText(alert.getContext(), "Falha ao enviar Ingresso. Verifique se voce possui ingressos.", Toast.LENGTH_LONG).show();
                }
            }
        };

        String sql = "UPDATE ingressos SET nome_dono = '"+usuarioDestino+"' WHERE nome_dono = '"+UsuarioSingleton.getInstance().getUsuario()+"' AND nome_evento = "+event.getId()+" LIMIT 1";


        job2.execute(sql);
        Toast.makeText(alert.getContext(), "Enviando Ingresso...", Toast.LENGTH_LONG).show();


        return true;
    }

    SendMySql job3;
    private void contarIngressos(){

        job3 = new SendMySql() {
            @Override
            public void naResposta(ResultSet result) throws SQLException {
                int counter = 0;
                while(result.next()){
                    counter++;
                }
                txtNumIngressos.setText(Integer.toString(counter));


            }
        };
        job3.execute("SELECT * FROM ingressos WHERE nome_dono = '"+UsuarioSingleton.getInstance().getUsuario()+"' AND nome_evento = "+event.getId() );

    }


    SendMySql job4;
    private void contarAmigos(){

        job4 = new SendMySql() {
            @Override
            public void naResposta(ResultSet result) throws SQLException {
                int counter = 0;
                while(result.next()){
                    counter++;
                }
                txtNumAmigos.setText(Integer.toString(counter));


            }
        };
        job4.execute("SELECT * FROM ingressos WHERE nome_dono = (SELECT contato FROM contatos WHERE usuario = '"+UsuarioSingleton.getInstance().getUsuario()+"' ) AND nome_evento = "+event.getId() );


    }



    SendMySql job5;
    private boolean euTenhoIngresso(){
        job5 = new SendMySql() {
            @Override
            public void naResposta(ResultSet result) throws SQLException {
                if(!result.next()){

                    return;
                }else{
                    btnOpenFotos.setVisibility(View.VISIBLE);
                    btnOpenFotos.setEnabled(true);
                    btnOpenChat.setVisibility(View.VISIBLE);
                    btnOpenChat.setEnabled(true);

                    btnEnviarIngresso.setVisibility(View.VISIBLE);
                    btnEnviarIngresso.setEnabled(true);

                    btnVerIngresso.setVisibility(View.VISIBLE);
                    btnVerIngresso.setEnabled(true);

                }


            }
        };
        job5.execute("SELECT * FROM ingressos WHERE nome_dono = '"+UsuarioSingleton.getInstance().getUsuario()+"' AND nome_evento = "+event.getId() );




        return false;
    }

    SendMySql job6;
    private boolean carregaFotoCapa(){
        job6 = new SendMySql() {
            @Override
            public void naResposta(ResultSet result) throws SQLException {
                if(!result.next()){
                    fotoCapa.setVisibility(View.GONE);
                    return;
                }else{
                    byte byte_arr[] = result.getString("foto").getBytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byte_arr , 0, byte_arr .length);
                    fotoCapa.setImageBitmap(bitmap);

                }


            }
        };
        job6.execute("SELECT * FROM eventos WHERE id = '"+event.getId()+"'" );




        return false;
    }




    private void souAtendente(){
        job1 = new SendMySql() {
            @Override
            public void naResposta(ResultSet result) throws SQLException {
                if(!result.next()){

                    return;
                }else{
                    btnValidarIngresso.setVisibility(View.VISIBLE);
                    btnValidarIngresso.setEnabled(true);
                }


            }
        };
        job1.execute("SELECT * FROM atendentes WHERE usuario_atendente = '"+UsuarioSingleton.getInstance().getUsuario()+"'" );

    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal_evento);
        event = (Evento) getIntent().getSerializableExtra("evento");
        alert= new AlertDialog.Builder(this);
        user = UsuarioSingleton.getInstance();
        ctx = this;
        txtNumIngressos = (TextView) findViewById(R.id.txt_num_ingressos);
        txtNomeEvento = (TextView)findViewById(R.id.event_name);
        txtNumAmigos = (TextView)findViewById(R.id.num_amigos);
        txtHoraEvento = (TextView)findViewById(R.id.hora);
        txtEnderecoEvento = (TextView)findViewById(R.id.endereco);
        txtPrecoEvento = (TextView)findViewById(R.id.preco);
        fotoCapa = (ImageView)findViewById(R.id.imagem_capa_evento);
        txtDescricaoEvento = (TextView)findViewById(R.id.descricao);
        btnComprarIngresso = (Button)findViewById(R.id.button_comprar_ingresso);
        btnGerenciarEvento = (Button)findViewById(R.id.button_gerenciar);
        btnEnviarIngresso = (Button)findViewById(R.id.button_enviar);
        btnOpenChat = (Button)findViewById(R.id.button_chat);
        btnOpenFotos = (Button)findViewById(R.id.button_fotos);
        btnValidarIngresso = (Button)findViewById(R.id.button_validar);
        btnVerIngresso = (Button) findViewById(R.id.button_ver_ingresso);
        contarIngressos();
        contarAmigos();
        Toast.makeText(this,"Aguarde. Carregando Informacoes...",Toast.LENGTH_LONG).show();
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



        souAtendente();

        btnOpenChat.setVisibility(View.INVISIBLE);
        btnOpenChat.setEnabled(false);
        btnOpenFotos.setVisibility(View.INVISIBLE);
        btnOpenFotos.setEnabled(false);

        btnEnviarIngresso.setVisibility(View.INVISIBLE);
        btnEnviarIngresso.setEnabled(false);

        btnVerIngresso.setVisibility(View.INVISIBLE);
        btnVerIngresso.setEnabled(false);

        euTenhoIngresso();

        txtNomeEvento.setText(event.nome);
        txtDescricaoEvento.setText(event.descricao);
        txtPrecoEvento.setText("R$"+Double.toString(event.preco));
        txtEnderecoEvento.setText(event.local);
        txtHoraEvento.setText(DateFormat.format("dd/MM/yyyy - hh:mm",event.data).toString());

        //carregaFotoCapa();
        /**
         * COMPRAR INGRESSO
         */
        btnComprarIngresso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateMySql job2;
                job2 = new UpdateMySql(){
                    @Override
                    public void naResposta(Integer result) throws SQLException {
                        if(result > 0) {

                            contarIngressos();
                            euTenhoIngresso();
                        }else{

                        }
                    }
                };

                String sql = "INSERT INTO ingressos  (`nome_evento`,`nome_dono`) VALUES ('" +
                        event.getId()+
                        "','" +
                        UsuarioSingleton.getInstance().getUsuario()+
                        "')";
                job2.execute(sql);


                new AlertDialog.Builder(v.getContext())
                        .setTitle("Aviso")
                        .setMessage("Funcionalidade de pagamento nao implementada pois nao tivemos acesso liberado a API de pagamentos do google ainda. O ingresso foi adiconado a sua conta com sucesso!")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .show();




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

                        enviarIngressoParaOutroUsuario(usuarioParaEnviar);



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
                intent.putExtra("evento_nome",event.nome);
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
                abrirIngresso();

            }
        });


    }

    SendMySql job1;
    private void abrirIngresso(){
        Toast.makeText(ctx,"Buscando Ingresso...",Toast.LENGTH_LONG).show();
        job1 = new SendMySql() {
            @Override
            public void naResposta(ResultSet result) throws SQLException {
                if(!result.next()){
                    //nao retornou nada
                    Toast.makeText(ctx,"Ingresso inexistente",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    Intent intent = new Intent(ctx, ActivityVerIngresso.class);

                    String ingressoId = Integer.toString(result.getInt("id"));
                    intent.putExtra("ingresso_id",ingressoId);
                    intent.putExtra("evento_id",event.getId());
                    startActivity(intent);
                }


            }
        };
        job1.execute("SELECT * FROM ingressos WHERE nome_dono = '"+UsuarioSingleton.getInstance().getUsuario() + "' AND  nome_evento = '"+event.getId() +"'");
        System.out.println("Login_execute");







    }



}
