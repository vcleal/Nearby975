package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nearbysocialevents.nearby975.Evento;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ra119555 on 16/05/16.
 */
public class AgendaActivity extends Activity {
    private ArrayList<Evento> eventos; //Lista de eventos que deve ser preenchida com os eventos
    private MyListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_agenda);
        eventos = new ArrayList<Evento>();

        ListView list = (ListView) findViewById(R.id.listViewAgenda);   //pega referencia da lista


        //TODO: preencher a lista 'eventos' do banco de dados



        /**
         * Evento(String nome2, float preco2, Date data2, float distancia2)
         */
        eventos.add(new Evento("0001","Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("0002","Evento 2",(float)0.50,new Date(),30));
        eventos.add(new Evento("0003","Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("0004","Evento 2",(float)0.50,new Date(),30));
        eventos.add(new Evento("0005","Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("0006","Evento 2",(float)0.50,new Date(),30));
        eventos.add(new Evento("0007","Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("0008","Evento 2",(float)0.50,new Date(),30));
        eventos.add(new Evento("0009","Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("0010","Evento 2",(float)0.50,new Date(),30));
        eventos.add(new Evento("0011","Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("0012","Evento 2",(float)0.50,new Date(),30));
        eventos.add(new Evento("0013","Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("0014","Evento 2",(float)0.50,new Date(),30));





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
