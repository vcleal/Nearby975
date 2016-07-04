package com.nearbysocialevents.nearby975.MySql;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.sql.SQLException;

import android.util.Base64;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap;
import 	android.graphics.BitmapFactory;


import com.nearbysocialevents.nearby975.MySql.dbMySQL;
import com.nearbysocialevents.nearby975.R;

/**
 * Created by BalaPC on 03-Jun-16.
 *
 SendfeedbackJob job = new SendfeedbackJob();
 job.execute();
 dbMySQL dbmysql = new dbMySQL();
 dbmysql.conectarMySQL("127.0.0.1", "3306", "test", "root", "bala123"); // ip do servidor mysql, porta, banco, usuário, senha
 dbmysql.queryMySQL();
 dbmysql.desconectarMySQL();
 *
 */



public class UpdateMySql extends AsyncTask<String, Void, Integer> {
    /** The system calls this to perform work in a worker thread and
     * delivers it the parameters given to AsyncTask.execute() */
    private dbMySQL dbmysql = new dbMySQL();
    private TabelaFuncMySql tmpTabela = new TabelaFuncMySql();
    private int escolhaExecucao = 1;
    //private ClassTmpUsar tmpUsar;
    private int indiceTabela;

    protected Integer doInBackground(String... sql) {
        Integer resposta;
        dbmysql.conectarMySQL(); // ip do servidor mysql, porta, banco, usuário, senha
        resposta =  dbmysql.updatesMySQL(sql[0]);
        return resposta;
    }

    /** The system calls this to perform work in the UI thread and delivers
     * the result from doInBackground() */
    protected void onPostExecute(Integer result) {
        try {
            naResposta(result);
        }catch (Exception erro){
            Log.e("MYSQL","Erro: "+erro);
        }
        dbmysql.desconectarMySQL();
    }

    public void naResposta(Integer result) throws SQLException {
        //De um override nesta função com as ações a serem executadas com a resposta do SQL
    }


    static public void sendPicture(Bitmap bitmap){
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        //Resources res = getContext().getResources();
        //Bitmap bitmap = BitmapFactory.decodeResource(bitmap2, R.drawable.cadastro);
        //System.out.println(bitmap.toString());

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
        byte [] byte_arr = stream.toByteArray();
        String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
        enviaImagemServidor(image_str);

    }


    static public void retrievePicture(int number){
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);

        /*
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
        byte [] byte_arr = stream.toByteArray();
        String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
        System.out.println(image_str);
        */

    }


    static private boolean enviaImagemServidor(String img){
        UpdateMySql job2;
        job2 = new UpdateMySql(){
            @Override
            public void naResposta(Integer result) throws SQLException {
                if(result > 0) {
                    System.out.println("Foto Enviada");
                }else{
                    System.out.println("Foto não enviada");
                }
            }
        };

        String sql = "INSERT INTO fotos  (`dono`,`foto`) VALUES ('" +
                "Felipe"+
                "','" +
                img +
                "')";

        //sql = "INSERT INTO usuario (`senha`) VALUES ('blablabla')";
        job2.execute(sql);

        //System.out.println("Chat_Mensagem_Enviada");

        return true;
    }


}
