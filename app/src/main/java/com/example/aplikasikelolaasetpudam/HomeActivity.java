package com.example.aplikasikelolaasetpudam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

//    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        sessionManager = new SessionManager(this);
//        sessionManager.checkLogin();
//
//
//        HashMap<String, String> user = sessionManager.getUserDetail();
    }

    public void Data_Aset(View view) {
        Intent intent = new Intent(HomeActivity.this, ScannerActivity.class);
        startActivity(intent);
    }

    public void Pencarian_Lokasi(View view) {
        Intent intent = new Intent(HomeActivity.this, PencarianLokasiActivity.class);
        startActivity(intent);
    }

    public void Pindai_QRCode(View view) {
        Intent intent = new Intent(HomeActivity.this, DetailAsetActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(HomeActivity.this);
            alertbox.setTitle("Apakah Anda Ingin Keluar ???");
            alertbox.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    clearApplicationData();
                    finishAffinity();
                }
            });

            alertbox.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });
            alertbox.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void clearApplicationData() {
    }
}
