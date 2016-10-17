package com.example.achaean.moma_app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Achaean on 2016. 9. 25..
 */
public class main extends AppCompatActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void moveActivity(View v){
        Intent intent = new Intent(this, control.class);
        startActivity(intent);
    }
    public void onBackPressed(){
        super.onBackPressed();
    }



}