package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nearbysocialevents.nearby975.MySql.SendMySql;
import com.nearbysocialevents.nearby975.MySql.UpdateMySql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 6/7/16.
 */
public class ActivityValidarIngresso extends Activity {


    Button validate;
    int event_id;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_validate_qrcode);
        validate = (Button) findViewById(R.id.button_validate);
        ctx = this;
        event_id = getIntent().getIntExtra("evento_id",0);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes
                    startActivityForResult(intent, 0);
                } catch (Exception e) {
                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(marketIntent);

                }
            }
        });

    }

    UpdateMySql job2;
    private void updateIngresso(int id){
        //Toast.makeText(ctx,"updating ticket",Toast.LENGTH_SHORT).show();

        job2 = new UpdateMySql(){
            @Override
            public void naResposta(Integer result) throws SQLException {
                if(result > 0) {
          //          Toast.makeText(ctx,"deu certo",Toast.LENGTH_SHORT).show();


                }else{
                    //chamaToast("Ocorreu um erro !!! Tente de novo");
            //        Toast.makeText(ctx,"nao deu certo",Toast.LENGTH_SHORT).show();
                }
            }
        };

        String sql = "UPDATE ingressos SET foi_validdo = 1 WHERE id = "+id;


        job2.execute(sql);
    }




    /**
     * Retorna
     * @param identificador
     * @return
     */
    private boolean validaIngresso(final String identificador){
        try{
            SendMySql job1;
            job1 = new SendMySql() {
                @Override
                public void naResposta(ResultSet result) throws SQLException {
                    //Toast.makeText(ctx,"Validando Ingresso",Toast.LENGTH_SHORT).show();
                    if(!result.next()){
                        //nao retornou nada
                        Toast.makeText(ctx,"Ingresso nao encontrado!",Toast.LENGTH_SHORT).show();
                        //se o ingresso nao e valido
                        icon.setBackgroundResource(R.drawable.redx);

                        //TOM DE ERRO
                        toneG.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 200);
                        return;
                    }else{
                        if(result.getInt("foi_validdo")==0){
                            updateIngresso(Integer.parseInt(identificador));
                            Toast.makeText(ctx,"Ingresso OK",Toast.LENGTH_SHORT).show();
                            //se o ingresso eh valido
                            icon.setBackgroundResource(R.drawable.greentick);

                            //TOM DE SUCESSO
                            toneG.startTone(ToneGenerator.TONE_CDMA_MED_SLS, 200);




                            return;

                        }else{
                            Toast.makeText(ctx,"Ingresso ja foi usado",Toast.LENGTH_SHORT).show();
                            //se o ingresso nao e valido
                            icon.setBackgroundResource(R.drawable.redx);
                            //TOM DE ERRO
                            toneG.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 200);

                        }
                    }



                    do {
                        Log.i("MYSQL", "Resultado: " + result.getString("nome"));


                    }while(result.next());
                }
            };
            job1.execute("SELECT * FROM ingressos WHERE id = '"+ identificador + "' AND  nome_evento = '"+event_id +"'");
            Toast.makeText(ctx,"Validando Usuário",Toast.LENGTH_LONG).show();
            System.out.println("Login_execute");



            return true;
        }catch(Exception e){




            return false;
        }

    }


    ImageView icon;
    ToneGenerator toneG;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        icon = (ImageView) findViewById(R.id.image_ticket_valid);
        icon.setBackground(null);

        TextView txtCode;
        txtCode = (TextView) findViewById(R.id.text_qrcode);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT"); //this is the result

                toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 200);
                txtCode.setText(contents);
                validaIngresso(contents);







            } else
            if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }


}
