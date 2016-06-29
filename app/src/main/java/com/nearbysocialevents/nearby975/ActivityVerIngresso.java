package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
/**
 * Created by root on 6/26/16.
 */
public class ActivityVerIngresso extends Activity {

    String ingressoId;
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ingresso);
        ImageView imageView = (ImageView) findViewById(R.id.image_qr_code);
        TextView txtNumeroQr = (TextView)findViewById(R.id.txt_qr_code_id);


        ingressoId = getIntent().getStringExtra("ingresso_id");
        txtNumeroQr.setText(ingressoId);


        try {
            Bitmap bitmap = encodeAsBitmap(ingressoId);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Bitmap encodeAsBitmap(String str) throws Exception {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 150, 150, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 150, 0, 0, w, h);
        return bitmap;
    }

}
