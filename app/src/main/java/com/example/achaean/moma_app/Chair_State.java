package com.example.achaean.moma_app;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Achaean on 2016. 10. 1..
 */
public class Chair_State extends AppCompatActivity{
    private final int CHAIR_ONE = 1;
    private final int CHAIR_TWO = 2;
    private final int CHAIR_THREE = 3;
    SharedPreferences state;
    private SharedPreferences.Editor editor;
    private int chairState = 0;
    public Chair_State(SharedPreferences getPre, SharedPreferences.Editor getEdit){
        state = getPre;
        editor = getEdit;
    }
    public int getChairState(int chairNumber){

        switch(chairNumber){
            case CHAIR_ONE:
                chairState = state.getInt("chair1",0);
                return chairState;
            case CHAIR_TWO:
                chairState = state.getInt("chair2",0);
                return chairState;
            case CHAIR_THREE:
                Toast.makeText(getApplicationContext(),"미구현 의자",Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getApplicationContext(),"오류 : 잘못된 의자벨류",Toast.LENGTH_LONG).show();
                break;
        }
        return -1;
    }

    public void editChairState(int chairNumber){
        switch (chairNumber){
            case CHAIR_ONE:
                if(chairState==1)
                    editor.putInt("chair1",0);
                else
                    editor.putInt("chair1",1);
                saveChairState();
                break;
            case CHAIR_TWO:
                if(chairState==1)
                    editor.putInt("chair2",0);
                else
                    editor.putInt("chair2",1);
                saveChairState();
                break;
            case CHAIR_THREE:
                Toast.makeText(getApplicationContext(),"미구현 의자",Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getApplicationContext(),"오류 : 잘못된 의자 벨류",Toast.LENGTH_LONG).show();
                break;
        }
    }
    public void valueClear(){
        editor.clear();
        saveChairState();
    }
    public void saveChairState(){
        editor.commit();
    }

}
