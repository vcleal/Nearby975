package com.nearbysocialevents.nearby975;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 6/26/16.
 */
public class ActivityFotosEvento extends Activity {

    ArrayList<Foto> fotos;
    MyImageListAdapter mAdapter;
    Button enviarFotos;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_compartilha_foto);

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

            }
        });

        list = (ListView) findViewById(R.id.list_fotos);   //pega referencia da lista
        fotos = new ArrayList<Foto>();

        //TODO: preencher a lista 'fotos' do banco de dados














        this.mAdapter = new MyImageListAdapter(this, fotos);
        list.setAdapter(mAdapter);

    }




    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


        if (resultCode == RESULT_OK) {
            Uri selectedImage = imageReturnedIntent.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();


            Bitmap image = BitmapFactory.decodeFile(filePath);
            fotos.add(new Foto(image));
            this.mAdapter = new MyImageListAdapter(this, fotos);
            list.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();    //recarrega a lista


            //TODO: Enviar variavel 'image' para o servidor



        }
    }









}
