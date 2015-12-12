package com.example.mindong.codimaker3;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Top extends AppCompatActivity {

    Button mBtOk;
    Button mBtCancel;
    Button mBtPlus;

    final static int GALLERY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        mBtCancel = (Button) findViewById(R.id.bt_cancel);
        mBtOk = (Button) findViewById(R.id.bt_OK);
        mBtPlus = (Button) findViewById(R.id.bt_plus);

        mBtOk.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        mBtCancel.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        mBtPlus.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GALLERY) {
            if(resultCode == RESULT_OK) {

                //복사할 파일 이름
                String imgName = getImageNameToUri(data.getData());
                //저장한 폴더 위치
                String T_Path = "/sdcard/android/data/com.example.mindong.codimaker3/TOP";

                fileCopy(imgName, T_Path + "/" + imgName);

            }
        }
    }

    public String getImageNameToUri(Uri data) {

        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgPath+ "/" + imgName;

    }

    public static void fileCopy(String inFileName, String outFileName) {
        try {
            FileInputStream fis = new FileInputStream(inFileName);
            FileOutputStream fos = new FileOutputStream(outFileName);

            int data = 0;
            while((data=fis.read())!=-1) {
                fos.write(data);
            }
            fis.close();
            fos.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
