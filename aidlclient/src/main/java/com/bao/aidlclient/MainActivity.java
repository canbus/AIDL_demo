package com.bao.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bao.aidl_demo.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    private IBinder mService = null;
    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            int result =0;
            mService = service;
            try{
            result= IMyAidlInterface.Stub.asInterface(service).add(1,2);
            }catch (Exception e){
                System.out.println(e.toString());
            }
            System.out.println(result);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int result = IMyAidlInterface.Stub.asInterface(mService).add(10,20);
                    Toast.makeText(MainActivity.this, ""+result, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        aidlTest();
    }

    private void aidlTest() {
        System.out.println("aidlTest");
        Intent intent = new Intent();
        intent.setAction("com.bao.aidl_demo.MyService");
        intent.setPackage("com.bao.aidl_demo");
        bindService(intent,conn,BIND_AUTO_CREATE);
    }
}
