package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.nearbysocialevents.nearby975.MySql.SendMySql;
import com.nearbysocialevents.nearby975.MySql.UpdateMySql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 6/25/16.
 */
public class ActivityBuscarEvento extends Activity {

    private ArrayList<Evento> eventos; //Lista de eventos que deve ser preenchida com os eventos
    private MyListAdapter mAdapter;
    private ListView list;
    private EditText busca;
    private Context ctx;
    int i=0;



    SendMySql job1;
    private boolean buscarEventosNoServidor(){
        job1 = new SendMySql(){
            @Override
            public void naResposta(ResultSet result) throws SQLException {
                //result.first();
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
                while(result.next()){
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
                }
                mAdapter = new MyListAdapter(ctx, eventos);
                list.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        };

        String sql = "SELECT * FROM eventos WHERE nome_evento LIKE '%"+busca.getText().toString()+"%'";

        job1.execute(sql);

        System.out.println("Buscando_Evento");

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_busca_eventos);
        eventos = new ArrayList<Evento>();
        list = (ListView) findViewById(R.id.listViewBusca);   //pega referencia da lista
        busca = (EditText)findViewById(R.id.textBuscaEvento);
        ctx = this;



        this.mAdapter = new MyListAdapter(this, eventos);
        list.setAdapter(mAdapter);


        busca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                i++;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                buscarEventosNoServidor();


                mAdapter = new MyListAdapter(ctx, eventos);
                list.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });

        buscarEventosNoServidor();



    }
}
