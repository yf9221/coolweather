package com.example.coolweather.callback;

public class DataCallBack implements one {


    private static DataCallBack sDataCallBack=null;
    private callback mCallBack;

    public static DataCallBack getInstance(){
        if (sDataCallBack == null) {
            synchronized (DataCallBack.class){
                if (sDataCallBack == null) {
                    sDataCallBack=new DataCallBack();
                }
            }
        }
        return sDataCallBack;
    }

    private DataCallBack(){

    }

    @Override
    public void zhuce(callback callback) {
        this.mCallBack=callback;
    }

    @Override
    public void xiaohui(callback callback) {
        mCallBack=null;
    }

    public void ff(){
        mCallBack.OnResponse();
    }
}
