package com.bao.aidl_demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {
    private Binder mBinder = new IMyAidlInterface.Stub(){

        @Override
        public int add(int arg1, int arg2) throws RemoteException {
            System.out.println("onReceive");
            return arg1 + arg2;
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
