package com.example.aplikasikelolaasetpudam;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasikelolaasetpudam.Config.Server;
import com.example.aplikasikelolaasetpudam.Controllers.LoginActivity;
import com.example.aplikasikelolaasetpudam.Controllers.SessionManager;
import com.example.aplikasikelolaasetpudam.Service.Check;
import com.example.aplikasikelolaasetpudam.Service.Constants;
import com.example.aplikasikelolaasetpudam.Service.GeocoderIntentService;
import com.example.aplikasikelolaasetpudam.Service.GeocoderResultReceiver;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerbaruiTanahActivity extends AppCompatActivity {

//    private final String URL = "http://10.252.22.110/aset/public/aset/test?id="+kode_aset;
//    private final String URL_KONDISI = "http://10.252.22.110/aset/public/aset/aset/"+id_aset;
    private static final String TAG = "PerbaruiTanahActivity";
    private static final int CAMERA_REQUEST = 1;
    private static final int SELECT_FILE = 2;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000;
    public File camera = null;
    //    MyLocationService mService;
    protected LocationManager locationManager;
    Spinner StatusAset;
    String foto = "";
    String id_aset = "";
    String kode_aset = "";
    SessionManager sessionManager;
    // Tambahkan Foto
    CircleImageView imageView;
    Button Foto;
    String currentPhotoPath;
    // Perbarui Tanggal
    DatePickerDialog picker;
    EditText eText;
    // Memperbarui Lokasi
    Context mContext;
    private EditText KodeAset, NamaAset, TglPerbarui, Alamat, Keterangan;
    private TextView textHasil;
    private Button Simpan, Batal;
    private ProgressBar Loading;
    private GeocoderResultReceiver geocoderReceiver;
    private boolean mLastLocation;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perbarui_tanah);

        id_aset = getIntent().getStringExtra("id_aset");
        kode_aset = getIntent().getStringExtra("kode_aset");
        sessionManager = new SessionManager(getApplicationContext());

        Loading = (ProgressBar) findViewById(R.id.loading);
        KodeAset = (EditText) findViewById(R.id.editText);
        KodeAset.setEnabled(false);
        NamaAset = (EditText) findViewById(R.id.editText1);
        NamaAset.setEnabled(false);
        TglPerbarui = (EditText) findViewById(R.id.editText2);
        StatusAset = (Spinner) findViewById(R.id.spinner);
        textHasil = (TextView) findViewById(R.id.txtHasil);
        Alamat = (EditText) findViewById(R.id.editText3);
        Keterangan = (EditText) findViewById(R.id.editText4);
        mContext = this;
        geocoderReceiver = new GeocoderResultReceiver(new Handler());
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

//        HashMap<String, String> pasien = sessionManager.getUserDetails();
//        kodeaset = pasien.get(sessionManager.KODE_ASET);
//        namaaset = pasien.get(sessionManager.NAMA_ASET);
        inputData();

        imageView = (CircleImageView) findViewById(R.id.myPict);
        Foto = (Button) findViewById(R.id.button);
        Foto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PerbaruiTanahActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PerbaruiTanahActivity.this, new String[]{Manifest.permission.CAMERA}, 1);

                } else {
                    CharSequence options[] = new CharSequence[]{"Kamera", "Galeri"};

                    final AlertDialog.Builder builder = new AlertDialog.Builder(PerbaruiTanahActivity.this);

                    builder.setTitle("Foto aset");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, final int i) {
                            if (i == 0) {
                                try {
                                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                                        File photoFile = null;
                                        try {
                                            photoFile = createImageFile();
                                        } catch (IOException ex) {
                                            // Terjadi kesalahan saat membuat File
                                        }
                                        // Lanjutkan hanya jika File berhasil dibuat
                                        if (photoFile != null) {
                                            Uri photoURI = FileProvider.getUriForFile(PerbaruiTanahActivity.this,
                                                "com.example.android.path",
                                                photoFile);
                                            takePictureIntent.putExtra(MediaStore.ACTION_IMAGE_CAPTURE, photoURI);
                                            startActivityForResult(takePictureIntent, CAMERA_REQUEST);

                                        }
                                    }
                                } catch (ActivityNotFoundException ex) {
                                    Toast.makeText(PerbaruiTanahActivity.this, "Gagal membuka Kamera", Toast.LENGTH_SHORT).show();
                                }
                            }

                            if (i == 1) {

                                File photoFile = null;
                                try {
                                    photoFile = createImageFile();
                                } catch (IOException ex) {
                                    // Error occurred while creating the File
                                }

                                if (photoFile != null) {
                                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    try {
                                        startActivityForResult(pickPhoto, SELECT_FILE);//
                                    } catch (ActivityNotFoundException ex) {
                                        Toast.makeText(PerbaruiTanahActivity.this, "Gagal membuka Galeri", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                    builder.show();
                }
            }
        });

        Simpan = (Button) findViewById(R.id.btnSimpan);
        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerbaruiTanahActivity.this, DetailAsetActivity.class);
                addSimpan();
                finish();
            }
        });

        Batal = (Button) findViewById(R.id.btnBatal);
        Batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerbaruiTanahActivity.this, DetailAsetActivity.class);
                finish();
            }
        });

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            MINIMUM_TIME_BETWEEN_UPDATES,
            MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
            new MyLocationListener());

        functionDatePicker();
    }

    private void functionDatePicker() {
        eText = (EditText) findViewById(R.id.editText2);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // Dialog pemilih tanggal
                picker = new DatePickerDialog(PerbaruiTanahActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            eText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, year, month, day);
                picker.show();
            }
        });
    }

    private File createImageFile() throws IOException {
        // Buat nama file gambar
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",         /* suffix */
            storageDir      /* directory */
        );
        // Simpan file: jalur untuk digunakan dengan maksud ACTION_VIEW
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1: {
                if (resultCode == RESULT_OK) {
                    File file = new File(currentPhotoPath);
                    Bitmap bitmap = null;
                    try {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(photo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (bitmap != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                        byte[] thumb_byte = baos.toByteArray();
                        foto = Base64.encodeToString(thumb_byte, Base64.DEFAULT);
                        File f = new File(currentPhotoPath);
                        try {
                            f.createNewFile();
                            FileOutputStream fos = new FileOutputStream(f);
                            fos.write(thumb_byte);
                            fos.flush();
                            fos.close();
                            camera = f;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageView.setRotation(90);
                        imageView.setImageBitmap(bitmap);
                    }
                }
                break;
            }
            case 2: {
                if (resultCode == RESULT_OK) {

                    Uri imageUri = data.getData();

                    try {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        Bitmap gambar = Check.handleSamplingAndRotationBitmap(PerbaruiTanahActivity.this, imageUri);
                        gambar.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                        byte[] thumb_byte = baos.toByteArray();
                        File f = new File(currentPhotoPath);
                        try {
                            f.createNewFile();
                            FileOutputStream fos = new FileOutputStream(f);
                            fos.write(thumb_byte);
                            fos.flush();
                            fos.close();
                            camera = f;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        imageView.setImageBitmap(gambar);
                    } catch (IOException e) {
                        Log.d("orientation", "" + e.getMessage());
                        e.printStackTrace();
                    }

                }
            }
            break;
        }
    }

    protected void startIntentService() {
        // Membuat intent yang mengarah ke IntentService untuk proses reverse geocoding
        Intent intent = new Intent(this, GeocoderIntentService.class);

        // Mengirim ResultReceiver sebagai extra ke intent service.
        intent.putExtra(Constants.RECEIVER, geocoderReceiver);

        // Mengirim location data sebagai extra juga ke intent service.
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        // tl;dr = menyalakan intent service :)
        startService(intent);
    }

    private void geoCoder(final LatLng latLng) {
        Log.d(TAG, "geoCoder: run geocoder");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Geocoder geocoder = new Geocoder(PerbaruiTanahActivity.this, Locale.getDefault());
                    final List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    PerbaruiTanahActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!addresses.isEmpty()) {
                                if (addresses.size() > 0) {
                                    // Alamat
                                    String address = addresses.get(0).getAddressLine(0);
                                    Log.d(TAG, "run: " + address);
                                    // setText ke Edit text
                                    Alamat.setText(address);
                                }
                            } else {
//                                editText.setText(R.string.text_addressNotAvailable);
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void isLocationEnabled() {

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(mContext);
            alertDialog.setTitle("Aktifkan Lokasi");
            alertDialog.setMessage("Pengaturan lokasi Anda tidak diaktifkan, silahkan aktifkan di menu pengaturan.");
            alertDialog.setPositiveButton("Pengaturan Lokasi", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            android.app.AlertDialog alert = alertDialog.create();
            alert.show();
        }
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
        isLocationEnabled();
        inputData();
    }

    private void inputData() {
//        String URL = "http://192.168.3.7/aset/public/aset/test?id=" + kode_aset;
//        String URL = "http://192.168.43.134/aset/public/aset/test?id=" + kode_aset;
        String URL = Server.API_URL + "get-aset-detail?aset_id=" + kode_aset;

        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    // Ambil data JSON
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject data = response.getJSONObject(i);

                        KodeAset.setText(data.getString("kode_aset"));
                        NamaAset.setText(data.getString("nama"));
                        if (data.getString("id_status").equals("Hak Pakai")) {
                            StatusAset.setSelection(0);
                        } else if (data.getString("id_status").equals("Hak Guna Bangunan")) {
                            StatusAset.setSelection(1);
                        } else if (data.getString("id_status").equals("Hak Milik")) {
                            StatusAset.setSelection(2);
                        } else if (data.getString("id_status").equals("Hak Guna Usaha")) {
                            StatusAset.setSelection(3);
                        } else if (data.getString("id_status").equals("Hak Pengelolaan")) {
                            StatusAset.setSelection(3);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(PerbaruiKondisiActivity.this, "Terjadi kesalahan " + error.toString(),
//                        Toast.LENGTH_SHORT).show();
            }
        }){
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
        requestQueue.add(stringRequest);
    }

    private void addSimpan() {
//        String URL_KONDISI = "http://192.168.3.7/aset/public/aset/aset/" + id_aset;
        String URL_KONDISI = Server.API_URL + "post-aset-update/" + id_aset;
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, URL_KONDISI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PerbaruiTanahActivity.this, "Berhasil memperbarui kondisi",
                    Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("foto", foto);
                params.put("kode_aset", KodeAset.getText().toString());
                params.put("nama", NamaAset.getText().toString());
                params.put("tanggal", TglPerbarui.getText().toString());
                if (StatusAset.getSelectedItem().equals("Hak Pakai")) {
                    params.put("id_status", "1");
                }
                if (StatusAset.getSelectedItem().equals("Hak Guna Bangunan")) {
                    params.put("id_status", "2");
                }
                if (StatusAset.getSelectedItem().equals("Hak Milik")) {
                    params.put("id_status", "3");
                }
                if (StatusAset.getSelectedItem().equals("Hak Guna Usaha")) {
                    params.put("id_status", "4");
                }
                if (StatusAset.getSelectedItem().equals("Hak Pengelolaan")) {
                    params.put("id_status", "4");
                }
                params.put("id_status", StatusAset.getSelectedItem().toString());
                params.put("id_lokasi", Alamat.getText().toString());
                params.put("keterangan", Keterangan.getText().toString());
                return params;
            }


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
        requestQueue.add(stringRequest);
    }

    private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            String Long = String.valueOf(location.getLongitude());
            String Lat = String.valueOf(location.getLatitude());
            String msg = "Latitude : " + Lat + "  |  Longitude : " + Long;
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            textHasil.setText(msg);
            Alamat.setText(msg);

            if (latLng != null) {
                Log.d(TAG, "onLocationChanged: " + latLng.latitude + " " + latLng.longitude);
                geoCoder(latLng);
            }
        }

        public void onStatusChanged(String s, int i, Bundle b) {

        }

        public void onProviderEnabled(String s) {
            Toast.makeText(PerbaruiTanahActivity.this,
                "Penyedia diaktifkan oleh pengguna. GPS dihidupkan",
                Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(PerbaruiTanahActivity.this,
                "Penyedia dinonaktifkan oleh pengguna. GPS dimatikan",
                Toast.LENGTH_LONG).show();
        }
    }

}
