package com.example.aplikasikelolaasetpudam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasikelolaasetpudam.Controllers.LoginActivity;

public class DataAsetActivity extends AppCompatActivity {

    ListView listView;
    String mKode[] = {"Kode Aset", "Kode Aset"};
    String mNama[] = {"Nama Aset", "Nama Aset"};
    String mStatus[] = {"Status Aset", "Status Aset"};
    String mLokasi[] = {"Lokasi Aset", "Lokasi Aset"};
    int images[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher_round};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_aset);

        listView = findViewById(R.id.listView);

        MyAdapter adapter = new MyAdapter(this, mKode, mNama, mStatus, mLokasi, images);
        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    Intent intent = new Intent(getApplicationContext(), DetailPencarianActivity.class);
//
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("image", images[0]);
//                    intent.putExtras(bundle);
//
//                    intent.putExtra("kode", mKode[0]);
//                    intent.putExtra("nama", mNama[0]);
//                    intent.putExtra("status", mStatus[0]);
//                    intent.putExtra("lokasi", mLokasi[0]);
//
//                    intent.putExtra("position", ""+0);
//                    startActivity(intent);
//
//                }
//            }
//        });
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String nKode[];
        String nNama[];
        String nStatus[];
        String nLokasi[];
        int nImages[];

        MyAdapter (Context c, String kode[], String nama[], String status[], String lokasi[], int images[]) {
            super(c, R.layout.data_aset, R.id.textView, kode);
            this.context = c;
            this.nKode = kode;
            this.nNama = nama;
            this.nStatus = status;
            this.nLokasi = lokasi;
            this.nImages = images;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View data_aset = layoutInflater.inflate(R.layout.data_aset, parent, false);
            ImageView images = data_aset.findViewById(R.id.imageView);
            TextView myKode = data_aset.findViewById(R.id.textView);
            TextView myNama = data_aset.findViewById(R.id.textView1);
            TextView myStatus = data_aset.findViewById(R.id.textView2);
            TextView myLokasi = data_aset.findViewById(R.id.textView3);

            images.setImageResource(nImages[position]);
            myKode.setText(nKode[position]);
            myNama.setText(nNama[position]);
            myStatus.setText(nStatus[position]);
            myLokasi.setText(nLokasi[position]);

            return data_aset;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
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
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = super.getView(position, convertView, parent);
//        if (position % 2 == 1) {
//            view.setBackgroundColor(Color.BLUE);
//        } else {
//            view.setBackgroundColor(Color.CYAN);
//        }
//        return view;
//    }
}
