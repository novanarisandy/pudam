package com.example.aplikasikelolaasetpudam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasikelolaasetpudam.Controllers.SessionManager;
import com.example.aplikasikelolaasetpudam.Service.Check;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerbaruiKondisiActivity extends AppCompatActivity {

    private EditText KodeAset, NamaAset, TglPerbarui, Keterangan;
    Spinner KondisiAset;
    private ProgressBar Loading;
    private static String URL_KONDISI = "";
    SessionManager sessionManager;
    // Tambahkan Foto
    CircleImageView imageView;
    Button Foto;
    private static final int CAMERA_REQUEST = 1;
    private static final int SELECT_FILE = 2;
    String currentPhotoPath;
    public File camera = null;
    // Perbarui Tanggal
    DatePickerDialog picker;
    EditText eText;
    Button btnGet;
    TextView tvw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perbarui_kondisi);

        sessionManager = new SessionManager(this);

        Loading = (ProgressBar) findViewById(R.id.loading);
        imageView = (CircleImageView) findViewById(R.id.myPict);
        Foto = (Button) findViewById(R.id.button3);
        KodeAset = (EditText) findViewById(R.id.editText);
        NamaAset = (EditText) findViewById(R.id.editText1);
        TglPerbarui = (EditText) findViewById(R.id.editText2);
        KondisiAset = (Spinner) findViewById(R.id.spinner);
        Keterangan = (EditText) findViewById(R.id.editText3);
        Foto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PerbaruiKondisiActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PerbaruiKondisiActivity.this, new String[]{Manifest.permission.CAMERA}, 1);

                } else {
                    CharSequence options[] = new CharSequence[]{"Kamera", "Galeri"};

                    final AlertDialog.Builder builder = new AlertDialog.Builder(PerbaruiKondisiActivity.this);

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
                                            Uri photoURI = FileProvider.getUriForFile(PerbaruiKondisiActivity.this,
                                                    "com.example.android.path",
                                                    photoFile);
                                            takePictureIntent.putExtra(MediaStore.ACTION_IMAGE_CAPTURE, photoURI);
                                            startActivityForResult(takePictureIntent, CAMERA_REQUEST);

                                        }
                                    }
                                } catch (ActivityNotFoundException ex) {
                                    Toast.makeText(PerbaruiKondisiActivity.this, "Gagal membuka Kamera", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(PerbaruiKondisiActivity.this, "Gagal membuka Galeri", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                    builder.show();
                }
            }
        });
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
                picker = new DatePickerDialog(PerbaruiKondisiActivity.this,
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
                        Bitmap gambar = Check.handleSamplingAndRotationBitmap(PerbaruiKondisiActivity.this, imageUri);
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
}