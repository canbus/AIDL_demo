# AIDL_demo
AIDL的简易demo

一、新建一个AIDL 服务端
1.AS中new一个AIDL文件
package com.bao.aidl_demo;
interface IMyAidlInterface {
     int add(int arg1,int arg2);
}

2.new 一个service
public class MyService extends Service {
    private Binder mBinder = new IMyAidlInterface.Stub(){
        public int add(int arg1, int arg2) throws RemoteException {
            System.out.println("onReceive");
            return arg1 + arg2;
        }
    };
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}

3.在配置文件中启动service
<service android:name=".MyService"
            android:process=":remote"
            android:enabled="true"
            android:exported="true" >
            <intent-filter>
                <action android:name="com.bao.aidl_demo.MyService"/>
            </intent-filter>
</service>

二、新建一个客户端来使用以上的aidl
1.把服务器的aidl文件拷贝下来
2.新建一个connection
private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            int result =0;
            try{
            result= IMyAidlInterface.Stub.asInterface(service).add(1,2);
            }catch (Exception e){}
            System.out.println(result);
        }

3.bind定服务
Intent intent = new Intent();
        intent.setAction("com.bao.aidl_demo.MyService");
        intent.setPackage("com.bao.aidl_demo");
        bindService(intent,conn,BIND_AUTO_CREATE);
