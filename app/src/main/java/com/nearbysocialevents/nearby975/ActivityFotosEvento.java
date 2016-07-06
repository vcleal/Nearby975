package com.nearbysocialevents.nearby975;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.nearbysocialevents.nearby975.MySql.UpdateMySql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by root on 6/26/16.
 */
public class ActivityFotosEvento extends Activity {

    ArrayList<Foto> fotos;
    MyImageListAdapter mAdapter;
    Button enviarFotos;
    ListView list;
    int event_id;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_compartilha_foto);
        event_id = getIntent().getIntExtra("evento_id",0);
        ctx = this;
        enviarFotos = (Button)findViewById(R.id.button_enviar_foto);


        enviarFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Complete action using"),
                        1);




                //ENVIAR
                //Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.logo);
                //UpdateMySql.sendPicture(bitmap);
                //
                //RECEBER
                /*
                UpdateMySql tmp = new UpdateMySql(){
                    @Override
                    public void recebeImagem(Bitmap imagem){
                        ImageView img = (ImageView) findViewById(R.id.imageView);
                        img.setImageBitmap(imagem);
                    }
                };
                UpdateMySql.retrievePicture(1, tmp);
                */

            }
        });

        list = (ListView) findViewById(R.id.list_fotos);   //pega referencia da lista
        fotos = new ArrayList<Foto>();



        UpdateMySql tmp = new UpdateMySql(){
            @Override
            public void recebeImagem(Bitmap imagem){

                fotos.add(new Foto(imagem));

                mAdapter = new MyImageListAdapter(getApplicationContext(), fotos);
                list.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        };
        UpdateMySql.retrievePicture(event_id, tmp);

        Toast.makeText(ctx,"Carregando Fotos...",Toast.LENGTH_SHORT).show();


    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);



        if (resultCode == RESULT_OK) {
            Uri selectedImage = imageReturnedIntent.getData();

            try{
                Bitmap image = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(),selectedImage);
                image = scaleDown(image,400,true);
                //ENVIAR
                UpdateMySql.sendPicture(image,event_id);
                Toast.makeText(ctx,"Enviando Foto...",Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                Toast.makeText(ctx,"Erro ao abrir fotos",Toast.LENGTH_SHORT).show();
            }

            //System.out.println(image.toString());
            //Bitmap image = BitmapFactory.decodeFile("com.android.providers.media.documents/document/image%3A38");

            /*
            fotos.add(new Foto(image));
            this.mAdapter = new MyImageListAdapter(this, fotos);
            list.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();    //recarrega a lista

            */


            //UpdateMySql.sendPicture(image);

        }
    }









}
