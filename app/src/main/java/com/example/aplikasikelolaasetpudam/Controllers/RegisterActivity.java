package com.example.aplikasikelolaasetpudam.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.aplikasikelolaasetpudam.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText Nama, Email, Username, Password, c_Password;
    private Button Register;
    private ProgressBar Loading;
    private static String URL_REGISTER = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Loading = (ProgressBar) findViewById(R.id.loading);
        Nama = (EditText) findViewById(R.id.editText);
        Email = (EditText) findViewById(R.id.editText1);
        Username = (EditText) findViewById(R.id.editText2);
        Password = (EditText) findViewById(R.id.editText3);
        c_Password = (EditText) findViewById(R.id.editText4);
        Register = (Button) findViewById(R.id.buttonRegister);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
    }

    private void Register() {
        Loading.setVisibility(View.VISIBLE);
        Register.setVisibility(View.GONE);

        final String name = this.Nama.getText().toString().trim();
        final String email = this.Email.getText().toString().trim();
        final String username = this.Username.getText().toString().trim();
        final String password = this.Password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("Berhasil");

                    if (success.equals("1")) {
                        Toast.makeText(RegisterActivity.this,"Register Berhasil", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this,"Register Gagal " + e.toString(), Toast.LENGTH_SHORT).show();
                    Loading.setVisibility(View.GONE);
                    Register.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this,"Register Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
                        Loading.setVisibility(View.GONE);
                        Register.setVisibility(View.VISIBLE);

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Nama", name);
                params.put("Email", email);
                params.put("Username", username);
                params.put("Password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Login(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
