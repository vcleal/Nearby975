package com.nearbysocialevents.nearby975;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        Intent intent = new Intent(this, ActivityValidarIngresso.class);
        startActivity(intent);


    }
}
