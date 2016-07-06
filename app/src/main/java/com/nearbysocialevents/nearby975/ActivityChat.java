package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nearbysocialevents.nearby975.MySql.SendMySql;
import com.nearbysocialevents.nearby975.MySql.UpdateMySql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 6/26/16.
 */
public class ActivityChat extends Activity {


    TextView corpoChat;
    EditText newMessage;
    Button sendButton;


    private UsuarioSingleton user = UsuarioSingleton.getInstance();


    String id_evento = "5";

    SendMySql job1;
    private boolean buscaMensagens(){
        job1 = new SendMySql(){
            @Override
            public void naResposta(ResultSet result) throws SQLException {
                result.first();
                do {
                    System.out.println("teste_chat");
                    corpoChat.append(result.getString("autor")+": "+result.getString("mensagem")+"\n");
                }while(result.next());
            }
        };

        String sql = "SELECT * FROM chat WHERE `id_evento` = '"+ id_evento+"'";
        job1.execute(sql);
        chamaToast("Carregando Mensagens");
        System.out.println("Buscando_Chat-Evento:"+id_evento);

        return true;
    }

    private void chamaToast(String var){
        Toast.makeText(this, var, Toast.LENGTH_SHORT).show();
    }


    /**
     * Este método envia os dados inserido no servidor
     */
    UpdateMySql job2;
    private boolean enviaMensagemServidor(){
        job2 = new UpdateMySql(){
            @Override
            public void naResposta(Integer result) throws SQLException {
                if(result > 0) {
                    corpoChat.append(user.getNome()+": "+ newMessage.getText().toString() +"\n");
                    newMessage.setText("");
                }else{
                    chamaToast("Ocorreu um erro !!! Tente de novo");
                }
            }
        };

        String sql = "INSERT INTO chat  (`autor`,`nome_evento`,`id_evento`,`mensagem`) VALUES ('" +
                user.getNome()+
                "','" +
                nome_festa+
                "','" +
                id_evento +
                "','" +
                newMessage.getText().toString() +
                "')";

        //sql = "INSERT INTO usuario (`senha`) VALUES ('blablabla')";
        job2.execute(sql);

        System.out.println("Chat_Mensagem_Enviada");

        return true;
    }



    TextView titulo_chat;
    String nome_festa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tela_chat);
        corpoChat = (TextView)findViewById(R.id.corpo_chat);
        newMessage = (EditText)findViewById(R.id.edt_chat_message);
        sendButton = (Button)findViewById(R.id.btn_enviar_chat);
        titulo_chat= (TextView)findViewById(R.id.titulo_chat);
        nome_festa = getIntent().getStringExtra("evento_nome");
        titulo_chat.setText("Chat de "+nome_festa);
        id_evento = Integer.toString(getIntent().getIntExtra("evento_id",0));
        //user = UsuarioSingleton.getInstance();

        buscaMensagens();




        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //UsuarioSingleton user = UsuarioSingleton.getInstance();
                //String mensage = user.getUsuario() + ": " +newMessage.getText().toString()+"\n";

                enviaMensagemServidor();
                Toast.makeText(v.getContext(), "Enviando mensagem...", Toast.LENGTH_SHORT).show();

            }
        });



    }
}
