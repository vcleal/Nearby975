package com.nearbysocialevents.nearby975;

import android.app.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_tela_chat);

        Intent intent = new Intent(this, TesteDB.class);
        startActivity(intent);

        //Intent intent = new Intent(this, ActivityValidarIngresso.class);
        //startActivity(intent);

    }
}

