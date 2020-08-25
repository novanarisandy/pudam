package com.example.aplikasikelolaasetpudam;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailTanahActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1;
    private static final int SELECT_FILE = 2;
    public File camera = null;
    String id_tanah = "";
    String kode_tanah = "";
    //    private final String URL = "http://192.168.43.134/aset/public/aset/test?id="+kode_aset;
    SessionManager sessionManager;
    // Tambahkan Foto
    CircleImageView imageView;
    Button Foto;
    String currentPhotoPath;
    private TextView KodeAset, NamaAset, LuasTanah, HargaTanah, SumberDana, StatusAset, Keterangan, Lokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tanah);

        kode_tanah = getIntent().getStringExtra("kode");
        sessionManager = new SessionManager(getApplicationContext());

        KodeAset = (TextView) findViewById(R.id.textViewKodeAset);
        NamaAset = (TextView) findViewById(R.id.textViewNamaAset);
        LuasTanah = (TextView) findViewById(R.id.textViewLuasTanah);
        HargaTanah = (TextView) findViewById(R.id.textViewHargaTanah);
        SumberDana = (TextView) findViewById(R.id.textViewSumberDana);
        StatusAset = (TextView) findViewById(R.id.textViewStatusAset);
        Keterangan = (TextView) findViewById(R.id.textViewKeterangan);
        Lokasi = (TextView) findViewById(R.id.textViewLokasi);

        inputData();

        imageView = (CircleImageView) findViewById(R.id.myPict);
        Foto = (Button) findViewById(R.id.button);
        Foto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(DetailTanahActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DetailTanahActivity.this, new String[]{Manifest.permission.CAMERA}, 1);

                } else {
                    CharSequence options[] = new CharSequence[]{"Kamera", "Galeri"};

                    final AlertDialog.Builder builder = new AlertDialog.Builder(DetailTanahActivity.this);

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
                                            Uri photoURI = FileProvider.getUriForFile(DetailTanahActivity.this,
                                                "com.example.android.path",
                                                photoFile);
                                            takePictureIntent.putExtra(MediaStore.ACTION_IMAGE_CAPTURE, photoURI);
                                            startActivityForResult(takePictureIntent, CAMERA_REQUEST);

                                        }
                                    }
                                } catch (ActivityNotFoundException ex) {
                                    Toast.makeText(DetailTanahActivity.this, "Gagal membuka Kamera", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(DetailTanahActivity.this, "Gagal membuka Galeri", Toast.LENGTH_SHORT).show();
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
                        Bitmap gambar = Check.handleSamplingAndRotationBitmap(DetailTanahActivity.this, imageUri);
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
//        String URL = "http://192.168.3.7/aset/public/aset/test?id=" + kode_tanah;
        String URL = Server.API_URL + "get-aset-detail?aset_id=" + kode_tanah;
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    // Ambil data JSON
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject data = response.getJSONObject(i);
                        id_tanah = data.getString("id");
                        KodeAset.setText(data.getString("kode_aset"));
                        NamaAset.setText(data.getString("nama"));
                        LuasTanah.setText(data.getString("luas_tanah"));
                        HargaTanah.setText(data.getString("harga_tanah"));
                        SumberDana.setText(data.getString("id_sumberdana"));
                        StatusAset.setText(data.getString("id_status"));
                        Keterangan.setText(data.getString("keterangan"));
                        Lokasi.setText(data.getString("id_lokasi"));

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
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Perbarui_Kondisi(View view) {
        Intent intent = new Intent(DetailTanahActivity.this, PerbaruiTanahActivity.class);
        intent.putExtra("kode_tanah", kode_tanah);
        intent.putExtra("id_tanah", id_tanah);
        startActivity(intent);
    }
}
