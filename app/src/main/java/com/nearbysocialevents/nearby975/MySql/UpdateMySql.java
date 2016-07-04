package com.nearbysocialevents.nearby975.MySql;

import android.os.AsyncTask;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.util.Log;

import com.nearbysocialevents.nearby975.MySql.dbMySQL;

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


}
