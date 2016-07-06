package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nearbysocialevents.nearby975.MySql.UpdateMySql;

import java.sql.SQLException;

/**
 * Created by root on 6/26/16.
 */
public class ActivityGerenciarEvento extends Activity {


    Button confirm;
    Button atendentes;
    AlertDialog.Builder alert;
    int event_id;
    Context ctx;

    private void definirAtendente(String nome){
        UpdateMySql job2;
        Toast.makeText(ctx,"Definindo atendente: "+nome,Toast.LENGTH_LONG).show();
        job2 = new UpdateMySql(){
            @Override
            public void naResposta(Integer result) throws SQLException {
                if(result > 0) {
                    Toast.makeText(ctx,"Atendente definido com sucesso.",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(ctx,"Erro - Verifique a conexao com a internet.",Toast.LENGTH_SHORT).show();
                }
            }
        };

        String sql = "INSERT INTO atendentes  (`event_id`,`usuario_atendente`) VALUES ('" +
                event_id+
                "','" +
                nome+
                "')";


        job2.execute(sql);

    }
    EditText edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gerenciamento_evento);
        event_id = getIntent().getIntExtra("evento_id",0);
        ctx=this;
        alert = new AlertDialog.Builder(this);
        confirm = (Button)findViewById(R.id.button_conf_alteracao_evento);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Alteracao do evento nao disponivel. Por favor crie outro.",Toast.LENGTH_LONG).show();
            }
        });
        atendentes = (Button)findViewById(R.id.button_def_atendente);
        atendentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittext = new EditText(v.getContext());
                alert = new AlertDialog.Builder(v.getContext());
                alert.setMessage("Digite o e-mail do atendente");
                alert.setTitle("Definir Atendentes");

                alert.setView(edittext);

                alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String usuarioParaEnviar = edittext.getText().toString();

                        definirAtendente(usuarioParaEnviar);



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


    }
}
