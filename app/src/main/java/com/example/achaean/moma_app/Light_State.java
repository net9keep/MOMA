package com.example.achaean.moma_app;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Achaean on 2016. 10. 9..
 */
public class Light_State extends AppCompatActivity {
    private final static int ON = 1;
    private final static int OFF = 0;
    SharedPreferences state;
    private SharedPreferences.Editor editor;
    private int lightState = 0;
    public Light_State(SharedPreferences getPre, SharedPreferences.Editor getEdit){
        state = getPre;
        editor = getEdit;
    }
    public int getLightState() {
        lightState = state.getInt("Light",0);
        return lightState;
    }

    public void editLightState(){
        if(lightState==1)
            editor.putInt("Light",0);
        else
            editor.putInt("Light",1);
        saveLightState();
    }

    private void saveLightState(){
        editor.commit();
    }
}
