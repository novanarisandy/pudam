package com.example.aplikasikelolaasetpudam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.aplikasikelolaasetpudam.Config.Server;
import com.example.aplikasikelolaasetpudam.Controllers.LoginActivity;
import com.example.aplikasikelolaasetpudam.Model.ModelAset;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailPencarianActivity extends AppCompatActivity {

    ModelAset modelAset;
    private TextView KodeAset, NamaAset, Satuan, Volume, HargaPerolehan, ThnHargaPerolehan, SumberDana, Tarif, Golongan, KondisiAset, Lokasi, Keterangan;
    CircleImageView imageView;
    private RequestOptions options;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pencarian);

        modelAset = getIntent().getParcelableExtra("detail_pencarian_aset");

        imageView = (CircleImageView) findViewById(R.id.myPict);
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

        RequestOptions options;
        options = new RequestOptions().centerCrop().placeholder(R.drawable.aset).error(R.drawable.aset);
        Glide.with(DetailPencarianActivity.this).load(Server.BASE_URL + modelAset.getImage_url())
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(options).into(imageView);
        KodeAset.setText(modelAset.getKode());
        NamaAset.setText(modelAset.getNama());
        Satuan.setText(modelAset.getSatuan());
        Volume.setText(modelAset.getVolume());
        HargaPerolehan.setText(String.valueOf(modelAset.getHarga()));
        ThnHargaPerolehan.setText(String.valueOf(modelAset.getTahun()));
        SumberDana.setText(modelAset.getSumberdana());
        Tarif.setText(String.valueOf(modelAset.getTarif()));
        Golongan.setText(modelAset.getGolongan());
        KondisiAset.setText(modelAset.getKondisi());
        Lokasi.setText(modelAset.getLokasi());
        Keterangan.setText(modelAset.getKeterangan());
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
}
