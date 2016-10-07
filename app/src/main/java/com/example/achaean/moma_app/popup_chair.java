package com.example.achaean.moma_app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Achaean on 2016. 9. 29..
 */
public class popup_chair extends AppCompatActivity {
    Dialog dialog = new Dialog(this);
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activitiy_popup_chair);
    }
    public void onClick(View v){

    }
}
