package com.nearbysocialevents.nearby975;

import android.os.AsyncTask;
import android.os.Looper;

/**
 * Created by BalaPC on 03-Jun-16.
 */
public class SendfeedbackJob extends AsyncTask<String, Void, dbMySQL> {
    /** The system calls this to perform work in a worker thread and
     * delivers it the parameters given to AsyncTask.execute() */
    protected dbMySQL doInBackground(String... urls) {
        Looper.prepare();
        dbMySQL dbmysql = new dbMySQL();
        dbmysql.conectarMySQL("10.0.2.2", "3306", "test", "root", "bala123"); // ip do servidor mysql, porta, banco, usuário, senha
        dbmysql.queryMySQL();
        Looper.loop();
        return dbmysql;
    }

    /** The system calls this to perform work in the UI thread and delivers
     * the result from doInBackground() */
    protected void onPostExecute(dbMySQL result) {
        result.desconectarMySQL();
    }
}
