package com.example.aplikasikelolaasetpudam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasikelolaasetpudam.Adapter.MyAdapter;
import com.example.aplikasikelolaasetpudam.Config.Server;
import com.example.aplikasikelolaasetpudam.Controllers.LoginActivity;
import com.example.aplikasikelolaasetpudam.Controllers.SessionManager;
import com.example.aplikasikelolaasetpudam.Model.ModelAset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAsetActivity extends AppCompatActivity {

    ArrayList<ModelAset> arraylistModelAset;
    //    private TextView KodeAset, NamaAset, KondisiAset, StatusAset, Lokasi;
//    String id_aset = "";
//    String kode_aset = "";
//    private final String URL = "http://192.168.43.134/aset/public/aset/test?id="+kode_aset;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<ModelAset> mData;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_aset);

        mData = new ArrayList<>();
        arraylistModelAset = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView1);
        adapter = new MyAdapter(this, mData);
        recyclerView.setAdapter(adapter);

        sessionManager = new SessionManager(this);

//        EditText editText = findViewById(R.id.action_search);
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                filter(editable.toString());
//            }
//        });

        inputData();
    }

    private void filter(String text) {
        ArrayList<ModelAset> filteredList = new ArrayList<>();

        for (ModelAset data : mData) {
            if (data.getNama().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(data);
            }
        }

        adapter.filterList(filteredList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        inputData();
//    }

    private void inputData() {
//        String URL_JSON = "http://192.168.43.134/aset/public/aset/aset";
        String URL_JSON = Server.API_URL + "get-aset-list";
        Log.e("GET ASET URL=", URL_JSON + " token:" + sessionManager.getToken());
//        Log.e("TOKEN : ", "");
        request = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        ModelAset modelAset = new ModelAset();
                        modelAset.setImage_url(jsonObject.getString("foto"));
                        modelAset.setKode(jsonObject.getString("kode_aset"));
                        modelAset.setNama(jsonObject.getString("nama"));
                        modelAset.setSatuan(jsonObject.getString("id_satuan"));
                        modelAset.setVolume(jsonObject.getString("volume"));
                        modelAset.setHarga(jsonObject.getInt("harga_perolehan"));
                        modelAset.setTahun(jsonObject.getInt("tahun_perolehan"));
                        modelAset.setSumberdana(jsonObject.getString("id_sumberdana"));
                        modelAset.setTarif(jsonObject.getInt("tarif"));
                        modelAset.setGolongan(jsonObject.getString("id_golongan"));
                        modelAset.setKondisi(jsonObject.getString("id_kondisi"));
//                        modelAset.setStatus(jsonObject.getString("id_status"));
                        modelAset.setLokasi(jsonObject.getString("id_lokasi"));
                        modelAset.setKeterangan(jsonObject.getString("keterangan"));
                        modelAset.setImage_url(jsonObject.getString("foto"));
                        arraylistModelAset.add(modelAset);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                setuprecyclerview(arraylistModelAset);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(DataAsetActivity.this, "Terjadi kesalahan " + error.toString(),
//                        Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + sessionManager.getToken());
                headers.put("Accept", "application/json");
                Log.e("HEADER ", sessionManager.getToken());
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void setuprecyclerview(ArrayList<ModelAset> asetArrayList) {
        MyAdapter adapter = new MyAdapter(this, asetArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
