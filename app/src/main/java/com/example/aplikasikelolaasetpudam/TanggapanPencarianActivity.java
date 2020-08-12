package com.example.aplikasikelolaasetpudam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasikelolaasetpudam.Controllers.LoginActivity;
import com.example.aplikasikelolaasetpudam.Service.MyAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

public class TanggapanPencarianActivity extends AppCompatActivity {

    private TextView NamaAset, ThnHargaPerolehan, Lokasi;
    String id_aset = "";
    String kode_aset = "";
//    private final String URL = "http://192.168.43.134/aset/public/aset/test?id="+kode_aset;

    RecyclerView recyclerView;

    String s1[], s2[], s3[];
    int images[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher_round};

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanggapan_pencarian);

        recyclerView = findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.tahun);
        s2 = getResources().getStringArray(R.array.nama);
        s3 = getResources().getStringArray(R.array.lokasi);

        MyAdapter myAdapter = new MyAdapter(this, s1, s2, s3, images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            startActivity(new Intent(this, HomeActivity.class));
        } else if (item.getItemId() == R.id.item2) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (item.getItemId() == R.id.item3) {
            startActivity(new Intent(this, ProfilActivity.class));
        }
        return true;
    }


}
