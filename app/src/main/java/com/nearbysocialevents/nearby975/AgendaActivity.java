package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nearbysocialevents.nearby975.Evento;
import com.nearbysocialevents.nearby975.MySql.SendMySql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ra119555 on 16/05/16.
 */
public class AgendaActivity extends Activity {
    private ArrayList<Evento> eventos; //Lista de eventos que deve ser preenchida com os eventos
    private MyListAdapter mAdapter;
    SendMySql job1;
    Context ctx;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_agenda);
        eventos = new ArrayList<Evento>();
        ctx = this;
        list = (ListView) findViewById(R.id.listViewAgenda);   //pega referencia da lista


        //TODO: preencher a lista 'eventos' do banco de dados

        job1 = new SendMySql() {
            @Override
            public void naResposta(ResultSet result) throws SQLException {
                if(!result.next()){
                    //nao retornou nada
                    Toast.makeText(ctx,"Erro - Verifique sua conexao com a internet.",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    int id;
                    Date data;
                    String nome_evento;
                    Long preco_evento;
                    String local_evento;
                    String owner;
                    String descricao_evento;
                    eventos = new ArrayList<Evento>();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Evento event;
                    do{
                        try{
                            id = result.getInt("id");
                            data = dateFormat.parse(result.getString("data_dia").toString()+" "+result.getString("data_hora").toString());
                            nome_evento = result.getString("nome_evento").toString();
                            preco_evento = result.getLong("preco");
                            local_evento = result.getString("local").toString();
                            descricao_evento = result.getString("descricao").toString();
                            owner = result.getString("nome_dono").toString();
                            event = new Evento(id,nome_evento,preco_evento,data,local_evento,descricao_evento,owner);
                            eventos.add(event);


                        }catch (Exception e){

                        }



                    }while(result.next());
                    mAdapter = new MyListAdapter(ctx, eventos);
                    list.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                }






            }
        };
        job1.execute("SELECT * FROM eventos");






        Toast.makeText(this,"Atualizando Agenda...",Toast.LENGTH_LONG).show();



        this.mAdapter = new MyListAdapter(this, eventos);
        list.setAdapter(mAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(), ActivityEvento.class);
                /**
                 * Passa o objeto evento inteiro, pois ele implementa a interface serializable
                 */
                intent.putExtra("evento",eventos.get(position));
                startActivity(intent);


            }
        });


    }
}
