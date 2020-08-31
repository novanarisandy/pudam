package com.example.aplikasikelolaasetpudam;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasikelolaasetpudam.Controllers.LoginActivity;

public class PencarianLokasiActivity extends AppCompatActivity {

//    String foto = "";
//    String id_aset = "";
//    String kode_aset = "";
    private EditText NamaAset;
    private Button Cari;

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
    }

//    private void inputData() {
//        String URL = "http://10.252.22.110/aset/public/aset/cari";
//        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET,URL,null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    // Ambil data JSON
//                    for (int i=0; i <response.length(); i++) {
//                        JSONObject data = response.getJSONObject(i);
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
////                Intent intent = new Intent(PencarianLokasiActivity.this, TanggapanPencarianActivity.class);
////                startActivity(intent);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Toast.makeText(TanggapanPencarianActivity.this, "Terjadi kesalahan " + error.toString(),
////                        Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("nama", NamaAset.getText().toString());
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }

    public void Tanggapan_Pencarian(View view) {
        Intent intent = new Intent(PencarianLokasiActivity.this, TanggapanPencarianActivity.class);
        intent.putExtra("nama", NamaAset.getText().toString());
        if (NamaAset.getText().toString().length()==0){
            // Jika form Nama belum di isi / masih kosong
            NamaAset.setError("Masukkan nama yang diperlukan");
        }

        startActivity(intent);
    }
}
