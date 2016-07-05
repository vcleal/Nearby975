package com.nearbysocialevents.nearby975;

import android.Manifest;
import android.app.Activity;
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


                /*
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
                */

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

        //TODO:FEITO preencher a lista 'fotos' do banco de dados
        //TODO: Pegar o id do evento e passar para pegar as fotos.

        UpdateMySql tmp = new UpdateMySql(){
            @Override
            public void recebeImagem(Bitmap imagem){
                //ImageView img = (ImageView) findViewById(R.id.imageView);
                //img.setImageBitmap(imagem);
                fotos.add(new Foto(imagem));

                mAdapter = new MyImageListAdapter(getApplicationContext(), fotos);
                list.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        };
        UpdateMySql.retrievePicture(1, tmp);



        //fotos.add(new Foto(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.logo)));
        /*
        this.mAdapter = new MyImageListAdapter(this, fotos);
        list.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        */


    }




    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


        //TODO: filePath está vindo vazio
        if (resultCode == RESULT_OK) {
            Uri selectedImage = imageReturnedIntent.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            String path = Environment.getExternalStorageDirectory().getPath();
            String myJpgPath = path + "/Download/download.jpg";
            System.out.println("Diretorio Foto: "+myJpgPath);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {


                int permissionCheck = getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                permissionCheck = getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                permissionCheck = getApplicationContext().checkCallingOrSelfPermission("android.permission.READ_EXTERNAL_STORAGE");
                permissionCheck = getApplicationContext().checkCallingOrSelfUriPermission(selectedImage,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                permissionCheck = getApplicationContext().checkCallingOrSelfUriPermission(Uri.parse(myJpgPath),Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }



            /*
            String wholeID = DocumentsContract.getDocumentId(selectedImage);
            String id = wholeID.split(":")[1];
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    filePathColumn, sel, new String[]{ id }, null);
            String filePath = "";
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
*/



            System.out.println("FilePath--"+selectedImage.toString());
            System.out.println("FilePathb--"+filePathColumn[0]);

            //Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);


            if (!cursor.moveToFirst()){System.out.println("Cursor Vazio");}


            System.out.println("FilePathw--"+cursor.getColumnName(0));
            System.out.println("FilePathw--"+cursor.getColumnName(1));
            System.out.println("FilePathw--"+cursor.getColumnName(2));
            System.out.println("FilePathw--"+cursor.getColumnName(3));
            System.out.println("FilePathw--"+cursor.getColumnName(4));
            System.out.println("FilePathw--"+cursor.getColumnName(5));

            System.out.println("FilePath2--"+cursor.getString(0));
            System.out.println("FilePath2--"+cursor.getString(1));
            System.out.println("FilePath2--"+cursor.getString(2));
            System.out.println("FilePath2--"+cursor.getString(3));
            System.out.println("FilePath2--"+cursor.getString(4));
            System.out.println("FilePath2--"+cursor.getString(5));

            /*
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            System.out.println("FilePath3--"+columnIndex);

            String filePath = cursor.getString(columnIndex);
            cursor.close();

            System.out.println("FilePath4--"+filePath);
*/


            //String a = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/";
            //String a = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbs‌​olutePath();;



            Bitmap image = BitmapFactory.decodeFile(myJpgPath);
            System.out.println(image.toString());
            //Bitmap image = BitmapFactory.decodeFile("com.android.providers.media.documents/document/image%3A38");

            /*
            fotos.add(new Foto(image));
            this.mAdapter = new MyImageListAdapter(this, fotos);
            list.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();    //recarrega a lista

            */
            //TODO:FEITO Enviar variavel 'image' para o servidor

            //UpdateMySql.sendPicture(image);

        }
    }









}
