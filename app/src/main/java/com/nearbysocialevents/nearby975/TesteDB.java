package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

        final Button button = (Button) findViewById(R.id.testedb_botao);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ///////////////////////
                SendfeedbackJob job = new SendfeedbackJob() {
                    @Override
                    protected void naResposta(ResultSet result) throws SQLException {
                        result.first();
                        do {
                            Log.i("MYSQL", "Resultado: " + result.getString("nome"));
                        }while(result.next());
                    }
                };
                job.execute("SELECT * FROM teste WHERE 1");
                System.out.println("teste");
                ////////////////////////////
            }
        });


    }






}
