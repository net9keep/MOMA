package com.example.achaean.moma_app;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Achaean on 2016. 10. 8..
 */
public class popup_light extends AppCompatActivity {
    Dialog dialog = new Dialog(this);
    LinearLayout background = (LinearLayout)findViewById(R.id.popup_light_background);
    ImageView cancel = (ImageView)findViewById(R.id.Light_cancel);
    ImageView state = (ImageView)findViewById(R.id.LightState);

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_light);
        Toast.makeText(getApplicationContext(),"OnCreate",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy(){
        recycleView(background);
        recycleView(cancel);
        recycleView(state);
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
