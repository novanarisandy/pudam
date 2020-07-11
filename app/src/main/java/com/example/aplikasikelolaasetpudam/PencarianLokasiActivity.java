package com.example.aplikasikelolaasetpudam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PencarianLokasiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian_lokasi);
    }

    public void Melihat_Data_Aset(View view) {
        Intent intent = new Intent(PencarianLokasiActivity.this, TanggapanPencarianActivity.class);
        startActivity(intent);
    }
}
