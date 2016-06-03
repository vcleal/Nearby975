package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by root on 6/3/16.
 */
public class ActivityValidarIngresso extends Activity {


    Button validate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_validate_qrcode);
        validate = (Button) findViewById(R.id.button_validate);

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

    /**
     * Retorna
     * @param identificador
     * @return
     */
    private boolean validaIngresso(String identificador){
        try{




            return true;
        }catch(Exception e){




            return false;
        }

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView icon;
        icon = (ImageView) findViewById(R.id.image_ticket_valid);
        icon.setBackground(null);

        TextView txtCode;
        txtCode = (TextView) findViewById(R.id.text_qrcode);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT"); //this is the result

                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 200);
                txtCode.setText(contents);

                if(validaIngresso(contents)){
                    //se o ingresso eh valido
                    icon.setBackgroundResource(R.drawable.greentick);

                    //TOM DE SUCESSO
                    toneG.startTone(ToneGenerator.TONE_CDMA_MED_SLS, 200);

                }else{
                    //se o ingresso nao e valido
                    icon.setBackgroundResource(R.drawable.redx);

                    //TOM DE ERRO
                    toneG.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 200);

                }










            } else
            if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }


}
