package com.example.achaean.moma_app;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                finish();
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }

        };
        handler.sendEmptyMessageDelayed(0,2000);
    }
    public void onBackPressed(){}
}