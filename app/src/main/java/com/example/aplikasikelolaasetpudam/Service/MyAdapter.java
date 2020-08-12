package com.example.aplikasikelolaasetpudam.Service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasikelolaasetpudam.DetailPencarianActivity;
import com.example.aplikasikelolaasetpudam.R;

import static com.android.volley.VolleyLog.TAG;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String data1[], data2[], data3[];
    int images[];
    Context context;

    public MyAdapter(Context ct, String s1[], String s2[], String s3[], int img[]) {
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        images = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tanggapan_pencarian, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);
        holder.myText3.setText(data3[position]);
        holder.myImages.setImageResource(images[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailPencarianActivity.class);
                context.startActivity(intent);

//                intent.putExtra(, )
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText1, myText2, myText3;
        ImageView myImages;
//        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.textView);
            myText2 = itemView.findViewById(R.id.textView1);
            myText3 = itemView.findViewById(R.id.textView2);
            myImages = itemView.findViewById(R.id.imageView);
//            parentLayout = itemView.findViewById(R.id.detaipencarian);
        }
    }
}
