package com.example.aplikasikelolaasetpudam;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.aplikasikelolaasetpudam.Config.Server;
import com.example.aplikasikelolaasetpudam.Controllers.LoginActivity;
import com.example.aplikasikelolaasetpudam.Controllers.SessionManager;
import com.example.aplikasikelolaasetpudam.Service.Check;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailAsetActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1;
    private static final int SELECT_FILE = 2;
    public File camera = null;
    String id_aset = "";
    String kode_aset = "";
    //    private final String URL = "http://192.168.43.134/aset/public/aset/test?id="+kode_aset;
    SessionManager sessionManager;
    // Tambahkan Foto
    CircleImageView imageView;
    Button Foto, Mutasi;
    String currentPhotoPath;
    String foto;
    Context mContext;
    private TextView KodeAset, NamaAset, Satuan, Volume, HargaPerolehan, ThnHargaPerolehan, SumberDana, Tarif, Golongan, KondisiAset, Lokasi, Keterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_aset);
        mContext = this;

        kode_aset = getIntent().getStringExtra("kode");
        sessionManager = new SessionManager(getApplicationContext());

        KodeAset = (TextView) findViewById(R.id.textViewKodeAset);
        NamaAset = (TextView) findViewById(R.id.textViewNamaAset);
        Satuan = (TextView) findViewById(R.id.textViewSatuan);
        Volume = (TextView) findViewById(R.id.textViewVolume);
        HargaPerolehan = (TextView) findViewById(R.id.textViewHargaPerolehan);
        ThnHargaPerolehan = (TextView) findViewById(R.id.textViewThnHargaPerolehan);
        SumberDana = (TextView) findViewById(R.id.textViewSumberDana);
        Tarif = (TextView) findViewById(R.id.textViewTarif);
        Golongan = (TextView) findViewById(R.id.textViewGolongan);
        KondisiAset = (TextView) findViewById(R.id.textViewKondisiAset);
        Lokasi = (TextView) findViewById(R.id.textViewLokasi);
        Keterangan = (TextView) findViewById(R.id.textViewKeterangan);
        Mutasi = (Button) findViewById(R.id.button2);

        inputData();

        imageView = (CircleImageView) findViewById(R.id.myPict);
        Foto = (Button) findViewById(R.id.button);
        Foto.setVisibility(View.GONE);
        Foto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(DetailAsetActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DetailAsetActivity.this, new String[]{Manifest.permission.CAMERA}, 1);

                } else {
                    CharSequence options[] = new CharSequence[]{"Kamera", "Galeri"};

                    final AlertDialog.Builder builder = new AlertDialog.Builder(DetailAsetActivity.this);

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
                                            // Error occurred while creating the File
                                        }
                                        // Continue only if the File was successfully created
                                        if (photoFile != null) {
                                            Uri photoURI = FileProvider.getUriForFile(DetailAsetActivity.this,
                                                "com.example.android.path",
                                                photoFile);
                                            takePictureIntent.putExtra(MediaStore.ACTION_IMAGE_CAPTURE, photoURI);
                                            startActivityForResult(takePictureIntent, CAMERA_REQUEST);

                                        }
                                    }
                                } catch (ActivityNotFoundException ex) {
                                    Toast.makeText(DetailAsetActivity.this, "Gagal membuka Kamera", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(DetailAsetActivity.this, "Gagal membuka Galeri", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                    builder.show();
                }
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

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//            } else {
//                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

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
                        Bitmap gambar = Check.handleSamplingAndRotationBitmap(DetailAsetActivity.this, imageUri);
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
//        String URL = "http://192.168.43.134/aset/public/aset/test?id=" + kode_aset;
        String URL = Server.API_URL + "get-aset-detail/" + kode_aset;
        Log.e("QRCODE URL",URL);
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    // Ambil data JSON
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject data = response.getJSONObject(i);
                        id_aset = data.getString("id");
                        KodeAset.setText(data.getString("kode_aset"));
                        NamaAset.setText(data.getString("nama"));
                        Satuan.setText(data.getString("id_satuan"));
                        Volume.setText(data.getString("volume"));
                        HargaPerolehan.setText(data.getString("harga_perolehan"));
                        ThnHargaPerolehan.setText(data.getString("tahun_perolehan"));
                        SumberDana.setText(data.getString("id_sumberdana"));
                        Tarif.setText(data.getString("tarif"));
                        Golongan.setText(data.getString("id_golongan"));
                        if (data.getString("bisa_mutasi").equals("t")) {
                            Mutasi.setVisibility(View.GONE);
                        } else {
                            Mutasi.setVisibility(View.VISIBLE);
                        }
                        KondisiAset.setText(data.getString("id_kondisi"));
                        Lokasi.setText(data.getString("id_lokasi"));
                        Keterangan.setText(data.getString("keterangan"));


                        RequestOptions options;
                        options = new RequestOptions().centerCrop().placeholder(R.drawable.aset).error(R.drawable.aset);
                        Glide.with(mContext).load(Server.BASE_URL + data.getString("foto"))
                            .apply(RequestOptions.skipMemoryCacheOf(true))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .apply(options).into(imageView);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(DetailAsetActivity.this, "Terjadi kesalahan " + error.toString(),
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
        requestQueue.add(stringRequest);
    }

    public void Perbarui_Kondisi(View view) {
        Intent intent = new Intent(DetailAsetActivity.this, PerbaruiKondisiActivity.class);
        intent.putExtra("kode_aset", kode_aset);
        intent.putExtra("id_aset", id_aset);
        startActivity(intent);
    }

    public void Perbarui_Mutasi(View view) {
        Intent intent = new Intent(DetailAsetActivity.this, PerbaruiMutasiActivity.class);
        intent.putExtra("kode_asets", kode_aset);
        intent.putExtra("id_asets", id_aset);
        startActivity(intent);
    }
}
