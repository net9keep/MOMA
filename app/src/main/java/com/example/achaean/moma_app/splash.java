package com.example.achaean.moma_app;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout background = (RelativeLayout)this.findViewById(R.id.splash_background);
        background.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.splash)));
        setContentView(R.layout.activity_splash);
        final Intent intent = new Intent(this, main.class);
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                startActivity(intent);
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0,2000);
    }
    public void onBackPressed(){}
}