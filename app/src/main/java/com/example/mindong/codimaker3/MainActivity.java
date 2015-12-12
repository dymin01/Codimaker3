package com.example.mindong.codimaker3;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

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
                    Toast toast = Toast.makeText(getApplicationContext(), "TOP", Toast.LENGTH_LONG);
                    toast.show();
                }
                else if(resultCode == 500) {
                    Intent intent = new Intent(MainActivity.this, Top.class);
                    startActivityForResult(intent, TOP);
                }
                break;
            case PANTS:
                if(resultCode == RESULT_OK) {
                    Toast toast = Toast.makeText(getApplicationContext(), "PANTS", Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            case SHOES:
                if(resultCode == RESULT_OK) {
                    Toast toast = Toast.makeText(getApplicationContext(), "SHOES", Toast.LENGTH_LONG);
                    toast.show();
                }
        }
    }

}
