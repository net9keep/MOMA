package com.example.achaean.moma_app;

import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Achaean on 2016. 9. 29..
 */
public class popup_chair extends AppCompatActivity {
    Dialog dialog = new Dialog(this);
    LinearLayout background = (LinearLayout)findViewById(R.id.popup_chair_background);
    ImageView cancel = (ImageView)findViewById(R.id.popup_chair_cancel);
    ImageView up = (ImageView)findViewById(R.id.up);
    ImageView down = (ImageView)findViewById(R.id.down);
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_popup_chair);

    }
    public void onClick(View v){

    }

    @Override
    public void onDestroy(){
        recycleView(background);
        recycleView(cancel);
        recycleView(up);
        recycleView(down);
    }

    private void recycleView(View view) {
        if(view != null) {
            Drawable bg = view.getBackground();
            if(bg != null) {
                bg.setCallback(null);
                ((BitmapDrawable)bg).getBitmap().recycle();
                view.setBackground(null);
            }
        }
    }
}
