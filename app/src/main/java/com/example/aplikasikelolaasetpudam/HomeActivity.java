package com.example.aplikasikelolaasetpudam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasikelolaasetpudam.Controllers.LoginActivity;
import com.example.aplikasikelolaasetpudam.Controllers.SessionManager;

public class HomeActivity extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);
//        sessionManager.checkLogin();

//        HashMap<String, String> user = sessionManager.getUserDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            startActivity(new Intent(this, HomeActivity.class));
        } else if (item.getItemId() == R.id.item2) {
            startActivity(new Intent(this, DataAsetActivity.class));
        } else if (item.getItemId() == R.id.item3) {
            startActivity(new Intent(this, PencarianLokasiActivity.class));
        } else if (item.getItemId() == R.id.item4) {
            startActivity(new Intent(this, ScannerActivity.class));
        }
        return true;
    }

    public void Melihat_Data_Aset(View view) {
        Intent intent = new Intent(HomeActivity.this, DataAsetActivity.class);
        startActivity(intent);
    }

    public void Pencarian_Lokasi(View view) {
        Intent intent = new Intent(HomeActivity.this, PencarianLokasiActivity.class);
        startActivity(intent);
    }

    public void Pindai_QRCode(View view) {
        Intent intent = new Intent(HomeActivity.this, ScannerActivity.class);
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
        sessionManager.logoutUser();
    }

}
