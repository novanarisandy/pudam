package com.example.aplikasikelolaasetpudam.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.aplikasikelolaasetpudam.Config.Server;
import com.example.aplikasikelolaasetpudam.DetailPencarianActivity;
import com.example.aplikasikelolaasetpudam.Model.ModelAset;
import com.example.aplikasikelolaasetpudam.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private RequestOptions options;
    private Context mContext;
    private List<ModelAset> mData;

    public RecyclerViewAdapter(Context mcontext, List<ModelAset> mData) {
        this.mContext = mcontext;
        this.mData = mData;

        options = new RequestOptions().centerCrop().placeholder(R.drawable.aset).error(R.drawable.aset);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recycler_tanggapanpencarian_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final ModelAset modelAset = mData.get(position);
        holder.myText1.setText(String.valueOf(mData.get(position).getTahun()));
        holder.myText2.setText(mData.get(position).getNama());
        holder.myText3.setText(mData.get(position).getLokasi());

        Glide.with(mContext).load(Server.BASE_URL + mData.get(position).getImage_url())
            .apply(RequestOptions.skipMemoryCacheOf(true))
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .apply(options).into(holder.myImages);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailPencarianActivity.class);
                intent.putExtra("detail_pencarian_aset", modelAset);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText1, myText2, myText3;
        ImageView myImages;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.textView);
            myText2 = itemView.findViewById(R.id.textView1);
            myText3 = itemView.findViewById(R.id.textView2);
            myImages = itemView.findViewById(R.id.imageView);
        }
    }

}
