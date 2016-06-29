package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

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
                eventos = new ArrayList<Evento>();
                String conteudoDaBusca = busca.getText().toString();
                //TODO: popular eventos com itens que contenham 'conteudoDaBusca'
                //eventos.add(new Evento("0014","Evento 2",(float)i,new Date(),30));

                //----------------------

                mAdapter = new MyListAdapter(ctx, eventos);
                list.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });





    }
}
