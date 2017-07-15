package com.alfian.android.kurirlaudry.volley;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ALFIAN on 17/06/2017.
 */

public class SessionManager {
    private Activity mActivity;
    private SharedPreferences mSharedPreferences;

    public SessionManager(Activity a){
        mActivity=a;
        mSharedPreferences = mActivity.getSharedPreferences("Alfian", Context.MODE_PRIVATE);
    }

    public void saveLogin(String[] data){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("status",data[0]);
        editor.putString("sesi", data[1]);
        editor.commit();
    }

    public void savePesanan(String[] data){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("id_pesanan",data[0]);
        editor.putString("user_plg", data[1]);
        editor.putString("longitute", data[2]);
        editor.putString("latitute", data[3]);
        editor.commit();
    }

    public void clearData(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("status","kosong");
        editor.putString("sesi", "kosong");
        editor.commit();
    }

    public String getStatus(){
        return mSharedPreferences.getString("status","kosong");
    }
    public String getSesi(){
        return mSharedPreferences.getString("sesi","kosong");
    }
    public String getId() {
        return mSharedPreferences.getString("id", "kosong");
    }
    public String getId__Pesanan() {
        return mSharedPreferences.getString("id_pesanan", "kosong");
    }
    public String getUser_plg() {
        return mSharedPreferences.getString("user_plg", "kosong");
    }
    public String getLongitute() {
        return mSharedPreferences.getString("longitute", "0");
    }
    public String getLatitute() {
        return mSharedPreferences.getString("latitute", "0");
    }
}
