package com.example.mindong.codimaker3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pants extends AppCompatActivity {

    Button mBtCancel;
    Button mBtOk;
    Button mBtPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        mBtCancel = (Button) findViewById(R.id.bt_cancel);
        mBtOk = (Button) findViewById(R.id.bt_OK);
        mBtPlus = (Button) findViewById(R.id.bt_plus);

        mBtOk.setOnClickListener(new View.OnClickListener(){

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

            }
        });
    }
}
