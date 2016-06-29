package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by root on 6/25/16.
 */
public class ActivityListaContatos extends Activity {
    private ArrayList<Usuario> contatos; //Lista de contatos que deve ser preenchida com os contatos
    private MyContatoAdapter mAdapter;
    ListView list;

    EditText busca;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contatos);


        busca = (EditText) findViewById(R.id.edtBuscaContato);
        ctx = this;
        list = (ListView) findViewById(R.id.listViewContatos);
        contatos = new ArrayList<Usuario>();
        //TODO: preencher lista de contatos

        contatos.add(new Usuario("Dilma"));

        this.mAdapter = new MyContatoAdapter(this, contatos);
        list.setAdapter(mAdapter);



        busca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //TODO: realizar busca no banco de dados e mostrar na tela



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
}
