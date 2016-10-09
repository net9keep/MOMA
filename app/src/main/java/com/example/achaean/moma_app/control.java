package com.example.achaean.moma_app;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Achaean on 2016. 9. 26..
 */
public class control extends AppCompatActivity{
    private static final int REQUEST_ENABLE_BT = 2;
    private static final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Set<BluetoothDevice> mDevices;
    BluetoothDevice mRemoteDevice;
    BluetoothSocket mSocket;
    OutputStream mOutputStream = null;
    InputStream mInputStream = null;
    byte[] readBuffer;
    int readBufferPosition;
    char mCharDelimiter = '\n';
    String mStrDelimiter = "\n";
    EditText mEditReceive;
    Button mButtonSend1,mButtonSend2;
    Thread mWorkerThread = null;
    String receiveChair = "";
    private int[] chairState = new int[2];
    private SharedPreferences getState;
    private SharedPreferences.Editor editor;
    ImageView




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);


        ImageView backImg = (ImageView)findViewById(R.id.backbtn);
        ImageView okImg = (ImageView)findViewById(R.id.okbtn);

        getState = getSharedPreferences("state",0);
        editor = getState.edit();
        backImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        okImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CreateChairDialog(1);
            }
        });
        //getState();

        //checkBluetooth();
    }


    protected void CreateChairDialog(final int chairNumber){
        final Dialog dialog = new Dialog(this);
        final Chair_State state = new Chair_State(getState, editor);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activitiy_popup_chair);
        //down = 0
        //up = 1
        final ImageView upImg = (ImageView)dialog.findViewById(R.id.up);
        final ImageView downImg = (ImageView)dialog.findViewById(R.id.down);
        final ImageView chair1Img = (ImageView)findViewById(R.id.control_chair1);
        chair1Img.setBackgroundResource(R.drawable.control_chair1_click);
        upImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(state.getChairState(chairNumber) == 1)
                    Toast.makeText(getApplicationContext(),"이미 의자가 올라가 있습니다",Toast.LENGTH_LONG).show();
                else{
                    state.editChairState(chairNumber);
                    chair1Img.setBackgroundResource(R.drawable.control_chair1_poss);
                    Toast.makeText(getApplicationContext(),"의자가 올라갔습니다."+state.getChairState(chairNumber), Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }

            }
        });
        downImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(state.getChairState(chairNumber) == 0)
                    Toast.makeText(getApplicationContext(),"이미 의자가 내려가 있습니다",Toast.LENGTH_LONG).show();
                else{
                    state.editChairState(chairNumber);
                    chair1Img.setBackgroundResource(R.drawable.control_chair1_imposs);
                    Toast.makeText(getApplicationContext(),"의자가 내려갔습니다."+state.getChairState(chairNumber), Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }
            }
        });

        dialog.show();
    }



    private void checkBluetooth() {
        if(mBluetoothAdapter==null){
            Toast.makeText(getApplicationContext(),"기기가 블루투스를 지원하지 않습니다",Toast.LENGTH_LONG).show();
            finish();
        }else{
            if(!mBluetoothAdapter.isEnabled()){
                Toast.makeText(getApplicationContext(),"현재 블루투스가 비활성 상태입니다",Toast.LENGTH_LONG).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            else
            {
                selectDevice();
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {

                } else if (resultCode == RESULT_CANCELED) {
                    finish();
                }
                break;
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
    void selectDevice(){
        mDevices=mBluetoothAdapter.getBondedDevices();
        if(mDevices.size()==0){
            finish();
        }
        connectToSelectedDevices("HC-06");
    }
    BluetoothDevice getDeviceFromBondedList(String name){
        BluetoothDevice selectedDevice = null;
        for(BluetoothDevice device:mDevices){
            if(name.equals(device.getName())){
                selectedDevice = device;
                break;
            }
        }
        return selectedDevice;
    }
    void beginListenForData(){
        final Handler handler = new Handler() {

            @Override
            public void publish(LogRecord record) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };
        readBufferPosition = 0;
        readBuffer = new byte[1024];

        mWorkerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()){
                    try{
                        int byteAvailable = mInputStream.available();
                        if(byteAvailable>0){
                            byte[] packetByte = new byte[byteAvailable];
                            mInputStream.read(packetByte);
                            for(int i=0; i<byteAvailable; i++){
                                byte b = packetByte[i];
                                if(b == mCharDelimiter){
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer,0,encodedBytes,0,encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition=0;
                                    mEditReceive.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            mEditReceive.setText(mEditReceive.getText().toString() + data + mStrDelimiter);
                                            receiveChair = mEditReceive.getText().toString();

                                        }
                                    });
                                }
                                else{
                                    readBuffer[readBufferPosition++] = b;
                                }

                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"데이터 수신 중 오류가 발생 했습니다",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });
        mWorkerThread.start();
    }
    void connectToSelectedDevices(String selectedDeviceName){
        mRemoteDevice = getDeviceFromBondedList(selectedDeviceName);
        UUID uuid =UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try{
            mSocket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect();
            mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();

            beginListenForData();

        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"블루투스 연결이 되어 있지 않습니다",Toast.LENGTH_LONG).show();
            //finish();
        }
    }

    void sendData(String msg){
        msg += mStrDelimiter;
        try{
            mOutputStream.write(msg.getBytes());
        }catch(Exception e){
            finish();
        }
    }

    protected void onDestory(){
        try{
            mWorkerThread.interrupt();
            mInputStream.close();
            mOutputStream.close();
            mSocket.close();
        }catch(Exception e){ }
        super.onDestroy();
    }
    @Override
    public void onBackPressed(){
        onDestory();
        super.onBackPressed();
    }
}
