package com.example.mindong.codimaker3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class Pants extends AppCompatActivity {

    Button mBtOk;
    Button mBtCancel;
    Button mBtPlus;
    Button mBtRemove;

    //Intent mItTemp = new Intent(getApplicationContext(), MainActivity.class);
    ArrayList<String> itemList = new ArrayList<String>();

    final static int GALLERY = 100;

    public class ImageAdapter extends BaseAdapter {

        private Context mContext;


        public ImageAdapter(Context c) {
            mContext = c;
        }

        void add(String path){
            itemList.add(path);
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            Bitmap bm = decodeSampledBitmapFromUri(itemList.get(position), 220, 220);

            imageView.setImageBitmap(bm);
            return imageView;
        }

        public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {

            Bitmap bm = null;
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(path, options);

            return bm;
        }

        public int calculateInSampleSize(

                BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                if (width > height) {
                    inSampleSize = Math.round((float)height / (float)reqHeight);
                } else {
                    inSampleSize = Math.round((float)width / (float)reqWidth);
                }
            }

            return inSampleSize;
        }

    }

    ImageAdapter myImageAdapter;

    Intent mItTemp = new Intent(Pants.this, MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pants);

        mBtCancel = (Button) findViewById(R.id.bt_cancel);
        mBtOk = (Button) findViewById(R.id.bt_OK);
        mBtPlus = (Button) findViewById(R.id.bt_plus);
        mBtRemove = (Button) findViewById(R.id.bt_remove);

        GridView gridview = (GridView) findViewById(R.id.gridView);

        //원래 위치

        myImageAdapter = new ImageAdapter(this);
        gridview.setAdapter(myImageAdapter);

        String ExternalStorageDirectoryPath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath();

        String targetPath = ExternalStorageDirectoryPath + "/android/data/com.example.mindong.codimaker3/PANTS";

        Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();

        File targetDirector = new File(targetPath);

        File[] files = targetDirector.listFiles();
        for (File file : files){
            myImageAdapter.add(file.getAbsolutePath());
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mItTemp.putExtra("img", itemList.get(position));
            }
        });

        mBtOk.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                setResult(RESULT_OK, mItTemp);
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

        mBtRemove.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                setResult(300, mItTemp); // 300 = minus result code
                finish();
            }
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GALLERY) {
            if(resultCode == RESULT_OK) {
                String[] proj = { MediaStore.Images.Media.DATA };
                Cursor cursor = managedQuery(data.getData(), proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                String imgPath = cursor.getString(column_index);
                String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);


                //저장할 폴더 위치
                String T_Path = "/sdcard/android/data/com.example.mindong.codimaker3/PANTS";

                fileCopy(imgPath, T_Path + "/" + imgName);

                setResult(500); //
                finish();

            }
        }
    }

    public static void fileCopy(String inFileName, String outFileName) {
        try {
            FileInputStream fis = new FileInputStream(inFileName);
            FileOutputStream fos = new FileOutputStream(outFileName);

            FileChannel fcin = fis.getChannel();
            FileChannel fcout = fos.getChannel();

            long size = fcin.size();

            fcin.transferTo(0, size, fcout);
            fcout.close();
            fcin.close();
            fos.close();
            fis.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
