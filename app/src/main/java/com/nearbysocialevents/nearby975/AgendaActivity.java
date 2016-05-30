package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
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


        /**
         * Evento(String nome2, float preco2, Date data2, float distancia2)
         */
        eventos.add(new Evento("Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("Evento 2",(float)0.50,new Date(),30));
        eventos.add(new Evento("Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("Evento 2",(float)0.50,new Date(),30));
        eventos.add(new Evento("Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("Evento 2",(float)0.50,new Date(),30));
        eventos.add(new Evento("Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("Evento 2",(float)0.50,new Date(),30));
        eventos.add(new Evento("Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("Evento 2",(float)0.50,new Date(),30));
        eventos.add(new Evento("Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("Evento 2",(float)0.50,new Date(),30));
        eventos.add(new Evento("Evento 1",(float)20.50,new Date(),2));
        eventos.add(new Evento("Evento 2",(float)0.50,new Date(),30));





        this.mAdapter = new MyListAdapter(this, eventos);
        list.setAdapter(mAdapter);




    }
}
