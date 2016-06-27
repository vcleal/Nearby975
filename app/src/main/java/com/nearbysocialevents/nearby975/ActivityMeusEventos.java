package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 6/25/16.
 */
public class ActivityMeusEventos extends Activity {
    private ArrayList<Evento> eventos; //Lista de eventos que deve ser preenchida com os eventos
    private MyListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_agenda);
        eventos = new ArrayList<Evento>();

        ListView list = (ListView) findViewById(R.id.listViewAgenda);   //pega referencia da lista


        //TODO: preencher a lista 'eventos' com meus eventos do BD



        /**
         * Evento(String nome2, float preco2, Date data2, float distancia2)
         */
        eventos.add(new Evento("0001","Meu Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("0002","Meu Evento 2",(float)0.50,new Date(),30));
        eventos.add(new Evento("0003","Meu Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("0004","Meu Evento 2",(float)0.50,new Date(),30));
        eventos.add(new Evento("0005","Meu Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("0006","Meu Evento 2",(float)0.50,new Date(),30));


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
