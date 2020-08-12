package com.example.aplikasikelolaasetpudam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasikelolaasetpudam.Controllers.LoginActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PencarianLokasiActivity extends AppCompatActivity {

    private EditText NamaAset;
    private Button Cari;
    String foto = "";
    String id_aset = "";
    String kode_aset = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian_lokasi);

        NamaAset = (EditText) findViewById(R.id.editText);
        Cari = (Button) findViewById(R.id.btnCari);

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
            startActivity(new Intent(this, LoginActivity.class));
        } else if (item.getItemId() == R.id.item3) {
            startActivity(new Intent(this, ProfilActivity.class));
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        inputData();
    }

    private void inputData() {
        String URL = "http://10.252.22.110/aset/public/aset/test?id="+kode_aset;
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET,URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    // Ambil data JSON
                    for (int i=0; i <response.length(); i++) {
                        JSONObject data = response.getJSONObject(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(TanggapanPencarianActivity.this, "Terjadi kesalahan " + error.toString(),
//                        Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", NamaAset.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Tanggapan_Pencarian(View view) {
        Intent intent = new Intent(PencarianLokasiActivity.this, TanggapanPencarianActivity.class);
        startActivity(intent);
    }
}
