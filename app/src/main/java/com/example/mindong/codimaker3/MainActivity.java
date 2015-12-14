package com.example.mindong.codimaker3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    ImageView mImgTop;
    ImageView mImgPants;
    ImageView mImgShoes;

    final static int TOP = 0;
    final static int PANTS = 1;
    final static int SHOES = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String str = Environment.getExternalStorageState();
        if ( str.equals(Environment.MEDIA_MOUNTED)) {

            String Top_Path = "/sdcard/android/data/com.example.mindong.codimaker3/TOP";
            String Pants_Path = "/sdcard/android/data/com.example.mindong.codimaker3/PANTS";
            String Shoes_Path = "/sdcard/android/data/com.example.mindong.codimaker3/SHOES";

            File top_file = new File(Top_Path);
            File pants_file = new File(Pants_Path);
            File shoes_file = new File(Shoes_Path);

            if( !top_file.exists() )  // 원하는 경로에 폴더가 있는지 확인
                top_file.mkdirs();

            if( !pants_file.exists() )
                pants_file.mkdirs();

            if( !shoes_file.exists() )
                shoes_file.mkdirs();

        }
        else {
            Toast.makeText(MainActivity.this, "SD Card 인식 실패", Toast.LENGTH_SHORT).show();
        }

        mImgTop = (ImageView) findViewById(R.id.img_top);
        mImgPants = (ImageView) findViewById(R.id.img_pants);
        mImgShoes = (ImageView) findViewById(R.id.img_shoes);

        mImgTop.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Top.class);
                startActivityForResult(intent, TOP);
            }
        });

        mImgPants.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Pants.class);
                startActivityForResult(intent, PANTS);
            }
        });

        mImgShoes.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Shoes.class);
                startActivityForResult(intent, SHOES);
            }
        });
    }

    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TOP:
                if(resultCode == RESULT_OK) {
                    String str = data.getExtras().getString("img");

                    File temp = new File(str);

                    Bitmap bitmap = decodeFile(temp);

                    mImgTop.setImageBitmap(bitmap);

                }
                else if(resultCode == 500) {
                    Intent intent = new Intent(MainActivity.this, Top.class);
                    startActivityForResult(intent, TOP);
                }
                else if(resultCode == 300) { // remove
                    Intent intent = new Intent(MainActivity.this, Top.class);
                    String str = data.getExtras().getString("img");
                    fileDelete(str);
                    startActivityForResult(intent, TOP);
                }
                break;
            case PANTS:
                if(resultCode == RESULT_OK) {
                    String str = data.getExtras().getString("img");

                    File temp = new File(str);

                    Bitmap bitmap = decodeFile(temp);

                    mImgPants.setImageBitmap(bitmap);
                }
                else if(resultCode == 500) {
                    Intent intent = new Intent(MainActivity.this, Pants.class);
                    startActivityForResult(intent, PANTS);
                }
                else if(resultCode == 300) { // remove
                    Intent intent = new Intent(MainActivity.this, Pants.class);
                    String str = data.getExtras().getString("img");
                    fileDelete(str);
                    startActivityForResult(intent, PANTS);
                }
                break;
            case SHOES:
                if(resultCode == RESULT_OK) {

                    String str = data.getExtras().getString("img");

                    File temp = new File(str);

                    Bitmap bitmap = decodeFile(temp);

                    mImgShoes.setImageBitmap(bitmap);
                    /*String str = data.getExtras().getString("img");
                    Bitmap image = BitmapFactory.decodeFile(str);

                    mImgShoes.setImageBitmap(image);

                    Toast toast = Toast.makeText(getApplicationContext(), "SHOES", Toast.LENGTH_LONG);
                    toast.show();*/
                }
                else if(resultCode == 500) {
                    Intent intent = new Intent(MainActivity.this, Shoes.class);
                    startActivityForResult(intent, SHOES);
                }
                else if(resultCode == 300) { // remove
                    Intent intent = new Intent(MainActivity.this, Shoes.class);
                    String str = data.getExtras().getString("img");
                    fileDelete(str);
                    startActivityForResult(intent, SHOES);
                }
                break;
        }
    }

    public static  void fileDelete(String FileName) {
        File f = new File(FileName);
        f.delete();
    }

    private Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);
            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale++;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }

}