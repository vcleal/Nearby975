package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.nearbysocialevents.nearby975.MySql.SendMySql;
import com.nearbysocialevents.nearby975.MySql.UpdateMySql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by root on 6/25/16.
 */
public class ActivityListaContatos extends Activity {
    private ArrayList<Usuario> contatos; //Lista de contatos que deve ser preenchida com os contatos
    private MyContatoAdapter mAdapter;
    ListView list;

    EditText busca;
    boolean isSearching = false;
    Context ctx;







    SendMySql job1;
    private void preencherContatos(){

        job1 = new SendMySql() {
            @Override
            public void naResposta(ResultSet result) throws SQLException {
                contatos = new ArrayList<Usuario>();
                while(result.next()){
                    contatos.add(new Usuario(result.getString("nome_contato"),result.getString("contato")));
                }
                mAdapter = new MyContatoAdapter(ctx, contatos);
                list.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        };
        job1.execute("SELECT * FROM contatos WHERE usuario = '"+UsuarioSingleton.getInstance().getUsuario() + "' ");
        Toast.makeText(ctx,"Carregando Contatos",Toast.LENGTH_LONG).show();




    }


    SendMySql job2;

    private void buscaContatos(String search){

        job2 = new SendMySql() {
            @Override
            public void naResposta(ResultSet result) throws SQLException {
                contatos = new ArrayList<Usuario>();
                while(result.next()){
                    if(result.getString("email")==UsuarioSingleton.getInstance().getUsuario()){
                        continue;
                    }
                    contatos.add(new Usuario(result.getString("nome"),result.getString("email")));
                }
                mAdapter = new MyContatoAdapter(ctx, contatos);
                list.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                isSearching=true;
            }
        };
        job2.execute("SELECT * FROM usuario WHERE nome LIKE '%"+search + "%' OR email LIKE '%"+search + "%'");
        Toast.makeText(ctx,"Buscando...",Toast.LENGTH_SHORT).show();




    }

UpdateMySql job3;
    private void addContato(String nomeContato,String mailContato){
        job3 = new UpdateMySql(){
            @Override
            public void naResposta(Integer result) throws SQLException {
                if(result > 0) {
                    Toast.makeText(ctx, "Contato adicionado com sucesso", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(ctx, "Falha ao adicionar contato - Verifique a internet", Toast.LENGTH_SHORT).show();
                }
            }
        };

        String sql = "INSERT INTO contatos  (`usuario`,`contato`,`nome_contato`) VALUES ('" +
                UsuarioSingleton.getInstance().getUsuario()+
                "','" +
                mailContato +
                "','" +
                nomeContato +
                "')";


        job3.execute(sql);




    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contatos);


        busca = (EditText) findViewById(R.id.edtBuscaContato);
        ctx = this;
        list = (ListView) findViewById(R.id.listViewContatos);
        contatos = new ArrayList<Usuario>();




        this.mAdapter = new MyContatoAdapter(this, contatos);
        list.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        preencherContatos();


        busca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(count == 0){
                    preencherContatos();
                    isSearching=false;
                }else{
                    buscaContatos(s.toString());
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                if(isSearching){
                    new AlertDialog.Builder(ctx)
                            .setTitle("Seguir")
                            .setMessage("Deseja mesmo adicionar "+contatos.get(position).nome+"?")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    addContato(contatos.get(position).nome,contatos.get(position).mail);
                                }
                            }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                            .show();
                }

            }
        });




    }
}
