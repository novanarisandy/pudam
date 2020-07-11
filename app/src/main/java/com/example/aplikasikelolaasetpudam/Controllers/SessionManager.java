package com.example.aplikasikelolaasetpudam.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.aplikasikelolaasetpudam.HomeActivity;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Login";
    private static final String LOGIN = "Is_Login";
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String username, String password) {
        editor.putBoolean(LOGIN, true);
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {
        if (!this.isLogin()) {
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((HomeActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail() {

        HashMap<String, String> user = new HashMap<>();
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));

        return user;
    }
}
