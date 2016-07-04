package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by root on 6/26/16.
 */
public class ActivityChat extends Activity {

    TextView corpoChat;
    EditText newMessage;
    Button sendButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tela_chat);
        corpoChat = (TextView)findViewById(R.id.corpo_chat);
        newMessage = (EditText)findViewById(R.id.edt_chat_message);
        sendButton = (Button)findViewById(R.id.btn_enviar_chat);


        //TODO preencher chat



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UsuarioSingleton user = UsuarioSingleton.getInstance();
                String mensage = user.getUsuario() + ": " +newMessage.getText().toString()+"\n";
                //TODO: dar append na menssangem da variavel 'mensage' e enviar ao servidor.

            }
        });



    }
}
