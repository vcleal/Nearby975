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
                while(result.next()){
                    eventos.add(new Evento(result.getString("nome_evento"), Double.valueOf(result.getString("preco")), new Date() ));
                    Log.i("MYSQL", "Resultado: " + result.getString("nome_evento"));
                }
            }
        };

        String sql = "SELECT * FROM eventos WHERE 1";

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
               // eventos = new ArrayList<Evento>();
                String conteudoDaBusca = busca.getText().toString();
                //TODO: FEITO - popular eventos com itens que contenham 'conteudoDaBusca'
                //TODO: corrigir a forma como os eventos são mostrados.
                //eventos.add(new Evento("0014","Evento 2",(float)i,new Date(),30));
                // eventos.add(new Evento("Casa da Joana Funck", 20.0, new Date() ));
                // eventos.add(new Evento("Casa da Joana Funck 2", 25.0, new Date() ));
                //----------------------

                mAdapter = new MyListAdapter(ctx, eventos);
                list.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });

        buscarEventosNoServidor();



    }
}
