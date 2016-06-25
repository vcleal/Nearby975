package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.os.Bundle;



/**
 * Created by root on 6/25/16.
 */
public class ActivityAlterarDadosPessoais extends Activity {






    /**
     * Decide se os dados no formulario sao validos ou nao
     * @return true se dados validos ou false se dados invalidos
     */
    private boolean isFormDataValid(){


        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_alteracao_dados_pessoais);


    }
}
