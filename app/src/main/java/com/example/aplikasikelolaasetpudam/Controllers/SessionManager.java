package com.example.aplikasikelolaasetpudam.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.aplikasikelolaasetpudam.HomeActivity;

import java.util.HashMap;

public class SessionManager {
    public static final String NAMA = "Nama";
    public static final String EMAIL = "Email";
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final String C_PASSWORD = "Confirm_Password";
    public static final String KODE_ASET = "Kode_Aset";
    public static final String NAMA_ASET = "Nama_Aset";
    private static final String PREF_NAME = "Login";
    private static final String LOGIN = "Is_Login";
    public static final String TOKEN = "token";
    public static final String BOOLEAN = "boolean";
    public SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public Context context;
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void saveToken(String keyToken, String value) {
        editor.putString(keyToken, value);
        editor.commit();
    }

    public void saveBoolean(String keyToken, Boolean value) {
        editor.putBoolean(keyToken, value);
        editor.commit();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN, "");
    }

    public Boolean getBoolean() {
        return sharedPreferences.getBoolean(BOOLEAN, false);
    }

    public void createSession(String username,String password){

        editor.putBoolean(LOGIN, true);
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.apply();

    }
    public void createSession(String nama, String email, String username,
                              String password, String c_password,
                              String kode_aset, String nama_aset, String token) {
        editor.putBoolean(LOGIN, true);
        editor.putString(NAMA, nama);
        editor.putString(EMAIL, email);
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.putString(C_PASSWORD, c_password);
        editor.putString(KODE_ASET, kode_aset);
        editor.putString(NAMA_ASET, nama_aset);
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {
        if (!this.isLogin()) {
            Intent i = new Intent(context, LoginActivity.class);
            // Menutup semua kegiatan
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Menambahkan untuk memulai aktivitas baru
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            ((HomeActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<>();
        user.put(NAMA, sharedPreferences.getString(NAMA, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        user.put(C_PASSWORD, sharedPreferences.getString(C_PASSWORD, null));
        user.put(KODE_ASET, sharedPreferences.getString(KODE_ASET, null));
        user.put(NAMA_ASET, sharedPreferences.getString(NAMA_ASET, null));
        user.put(TOKEN, sharedPreferences.getString(TOKEN,null));
        return user;
    }

    public void logoutUser() {
        editor.putBoolean(LOGIN, false);
        editor.apply();
        editor.clear();
//        Intent i = new Intent(context, LoginActivity.class);
        // Menutup semua kegiatan
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Menambahkan untuk memulai aktivitas baru
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
    }
}

