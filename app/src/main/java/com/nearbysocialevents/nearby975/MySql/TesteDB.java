package com.nearbysocialevents.nearby975.MySql;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nearbysocialevents.nearby975.R;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by BalaPC on 06-Jun-16.
 */
public class TesteDB extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_teste_db);

        ////////////Tipo 1 de chamada///////////
        final Button button1 = (Button) findViewById(R.id.testedb_botao1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                SendMySql job1 = new SendMySql() {
                    @Override
                    protected void naResposta(ResultSet result) throws SQLException {
                        result.first();
                        do {
                            Log.i("MYSQL", "Resultado: " + result.getString("nome"));
                        }while(result.next());
                    }
                };
                job1.execute("SELECT * FROM teste WHERE 1");
                System.out.println("teste");

            }
        });
        //////////////Fim Tipo 1////////////////

        ////////////Tipo 2 de chamada///////////

        class teste1 implements MySqlRunnable{
            public void run(ResultSet result) throws SQLException{
                result.first();
                do {
                    Log.i("MYSQL", "Resultado: " + result.getString("nome"));
                }while(result.next());
            }
        }

        final Button button2 = (Button) findViewById(R.id.testedb_botao2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SendMySql job2 = new SendMySql();
                teste1 obj = new teste1();
                job2.usar(obj);
                job2.execute("SELECT * FROM teste WHERE 1");
            }
        });
        //////////////Fim Tipo 2////////////////

        ////////////Tipo 3 de chamada///////////
        final Button button3 = (Button) findViewById(R.id.testedb_botao3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SendMySql job3 = new SendMySql();
                job3.usarTabela(1);
                job3.execute("SELECT * FROM teste WHERE 1");
            }
        });
        //////////////Fim Tipo 3////////////////
    }






}
