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

    //SET INTENT VALUE
    final static int TOP = 0;
    final static int PANTS = 1;
    final static int SHOES = 2;
    final static int RESTART = 500;
    final static int REMOVE = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //sd카드 폴더 Path
        String str = Environment.getExternalStorageState();
        if ( str.equals(Environment.MEDIA_MOUNTED)) {
            //원하는 폴더 Path
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

        //Image ClickListener
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
                    //data EXTRA에서 IMAGE Path받아오기
                    if(data.getExtras() == null) {
                        Intent intent = new Intent(MainActivity.this, Top.class);
                        Toast.makeText(getApplicationContext(), "사진을 선택해 주세요.", Toast.LENGTH_LONG).show();
                        startActivityForResult(intent, TOP);
                    }
                    else {
                        String str = data.getExtras().getString("img");

                        File temp = new File(str);
                        //파일을 비트맵으로 저장
                        Bitmap bitmap = decodeFile(temp);
                        //원히는 이미지뷰에 비트맵을 set
                        mImgTop.setImageBitmap(bitmap);
                    }

                }
                //intent를 다시 실행
                else if(resultCode == RESTART) {
                    Intent intent = new Intent(MainActivity.this, Top.class);
                    startActivityForResult(intent, TOP);
                }
                //str로 지정된 Image파일을 지운 후 intent를 다시 실행
                else if(resultCode == REMOVE) { // remove
                    if(data.getExtras() == null) {
                        Intent intent = new Intent(MainActivity.this, Top.class);
                        Toast.makeText(getApplicationContext(), "사진을 선택해 주세요.", Toast.LENGTH_LONG).show();
                        startActivityForResult(intent, TOP);
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, Top.class);
                        String str = data.getExtras().getString("img");
                        fileDelete(str);
                        startActivityForResult(intent, TOP);
                    }
                }
                break;
            case PANTS:
                if(resultCode == RESULT_OK) {
                    if(data.getExtras() == null) {
                        Intent intent = new Intent(MainActivity.this, Pants.class);
                        Toast.makeText(getApplicationContext(), "사진을 선택해 주세요.", Toast.LENGTH_LONG).show();
                        startActivityForResult(intent, PANTS);
                    }
                    else {
                        String str = data.getExtras().getString("img");

                        File temp = new File(str);
                        //파일을 비트맵으로 저장
                        Bitmap bitmap = decodeFile(temp);
                        //원히는 이미지뷰에 비트맵을 set
                        mImgPants.setImageBitmap(bitmap);
                    }
                }
                else if(resultCode == RESTART) {
                    Intent intent = new Intent(MainActivity.this, Pants.class);
                    startActivityForResult(intent, PANTS);
                }
                else if(resultCode == REMOVE) { // remove
                    if(data.getExtras() == null) {
                        Intent intent = new Intent(MainActivity.this, Pants.class);
                        Toast.makeText(getApplicationContext(), "사진을 선택해 주세요.", Toast.LENGTH_LONG).show();
                        startActivityForResult(intent, PANTS);
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, Pants.class);
                        String str = data.getExtras().getString("img");
                        fileDelete(str);
                        startActivityForResult(intent, PANTS);
                    }
                }
                break;
            case SHOES:
                if(resultCode == RESULT_OK) {

                    if(data.getExtras() == null) {
                        Intent intent = new Intent(MainActivity.this, Shoes.class);
                        Toast.makeText(getApplicationContext(), "사진을 선택해 주세요.", Toast.LENGTH_LONG).show();
                        startActivityForResult(intent, SHOES);
                    }
                    else {
                        String str = data.getExtras().getString("img");

                        File temp = new File(str);
                        //파일을 비트맵으로 저장
                        Bitmap bitmap = decodeFile(temp);
                        //원히는 이미지뷰에 비트맵을 set
                        mImgShoes.setImageBitmap(bitmap);
                    }
                }
                else if(resultCode == RESTART) {
                    Intent intent = new Intent(MainActivity.this, Shoes.class);
                    startActivityForResult(intent, SHOES);
                }
                else if(resultCode == REMOVE) { // remove
                    if(data.getExtras() == null) {
                        Intent intent = new Intent(MainActivity.this, Shoes.class);
                        Toast.makeText(getApplicationContext(), "사진을 선택해 주세요.", Toast.LENGTH_LONG).show();
                        startActivityForResult(intent, SHOES);
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, Shoes.class);
                        String str = data.getExtras().getString("img");
                        fileDelete(str);
                        startActivityForResult(intent, SHOES);
                    }
                }
                break;
        }
    }

    //파일을 지우는 함수
    public static  void fileDelete(String FileName) {
        File f = new File(FileName);
        f.delete();
    }

    //Bitmap 사이즈를 줄이는 함수
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