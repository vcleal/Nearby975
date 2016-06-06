package com.nearbysocialevents.nearby975;

import android.os.AsyncTask;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.os.Looper;
import android.util.Log;

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
 *
 *
 */



public class SendfeedbackJob extends AsyncTask<String, Void, ResultSet> {
    /** The system calls this to perform work in a worker thread and
     * delivers it the parameters given to AsyncTask.execute() */
    private dbMySQL dbmysql = new dbMySQL();

    protected ResultSet doInBackground(String... sql) {
        dbmysql.conectarMySQL(); // ip do servidor mysql, porta, banco, usuário, senha
        ResultSet retornoSQL = dbmysql.querysMySQL(sql[0]);
        return retornoSQL;
    }

    /** The system calls this to perform work in the UI thread and delivers
     * the result from doInBackground() */
    protected void onPostExecute(ResultSet result) {
        try {
            naResposta(result);
        }catch (Exception erro){
            Log.e("MYSQL","Erro: "+erro);
        }
        dbmysql.desconectarMySQL();
    }

    protected void naResposta(ResultSet result) throws SQLException {
        //De um override nesta função com as ações a serem executadas com a resposta do SQL
    }


}
