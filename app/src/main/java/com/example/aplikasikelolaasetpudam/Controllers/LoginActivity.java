package com.example.aplikasikelolaasetpudam.Controllers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasikelolaasetpudam.Config.Server;
import com.example.aplikasikelolaasetpudam.HomeActivity;
import com.example.aplikasikelolaasetpudam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText Username, Password;
    private Button Login, Register;
    private ProgressBar Loading;
    private static String URL_LOGIN = Server.URL + "";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        Loading = (ProgressBar) findViewById(R.id.loading);
        Username = (EditText) findViewById(R.id.editText);
        Password = (EditText) findViewById(R.id.editText1);
        Login = (Button) findViewById(R.id.buttonLogin);
        Register = (Button) findViewById(R.id.buttonRegister);

//        Login.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
//                String mUsername = Username.getText().toString().trim();
//                String mPassword = Password.getText().toString().trim();
//
//                if (!mUsername.isEmpty() || !mPassword.isEmpty()) {
//                    login(mUsername, mPassword);
//                } else {
//                    Username.setError("Silahkan masukkan Username");
//                    Password.setError("Silahkan masukkan Password");
//                }
//            }
//        });
//        Register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//            }
//        });
//    }

//    private void login(final String Username, final String Password) {
//
//        Loading.setVisibility(View.VISIBLE);
//        Login.setVisibility(View.GONE);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("Berhasil");
//                    JSONArray jsonArray = jsonObject.getJSONArray("Login");
//
//                    if (success.equals("1")) {
//
//                        for (int i = 0; i <jsonArray.length(); i++) {
//
//                            JSONObject object = jsonArray.getJSONObject(i);
//
//                            String username = object.getString("Username").trim();
//                            String password = object.getString("Password").trim();
//
//                            sessionManager.createSession(username, password);
//
//                            Toast.makeText(LoginActivity.this,"Login Berhasil. \nUsername Anda : "+username
//                                    +"\nPassword Anda : "+password, Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                            intent.putExtra("Username", username);
//                            intent.putExtra("Password", password);
//                            startActivity(intent);
//
//                            Loading.setVisibility(View.GONE);
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Loading.setVisibility(View.GONE);
//                    Login.setVisibility(View.VISIBLE);
//                    Toast.makeText(LoginActivity.this,"Gagal " +e.toString(),Toast.LENGTH_SHORT).show();
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Loading.setVisibility(View.GONE);
//                        Login.setVisibility(View.VISIBLE);
//                        Toast.makeText(LoginActivity.this,"Gagal " +error.toString(),Toast.LENGTH_SHORT).show();
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Username", Username);
//                params.put("Password", Password);
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(LoginActivity.this);
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

    public void Register(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void Home(View view) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
