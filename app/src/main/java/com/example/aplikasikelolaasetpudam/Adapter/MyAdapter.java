package com.example.aplikasikelolaasetpudam.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.aplikasikelolaasetpudam.Config.Server;
import com.example.aplikasikelolaasetpudam.Model.ModelAset;
import com.example.aplikasikelolaasetpudam.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    RequestOptions options;
    private Context mContext;
    private List<ModelAset> mData;
    private List<ModelAset> mDataAll;
    private Filter mDataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelAset> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mDataAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ModelAset data : mDataAll) {
                    if (data.getKondisi().toLowerCase().contains(filterPattern)) {
                        filteredList.add(data);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData.clear();
            mData = ((List) results.values);
            notifyDataSetChanged();
        }
    };

    public MyAdapter(Context mcontext, List<ModelAset> mData) {
        this.mContext = mcontext;
        this.mData = mData;
        mDataAll = new ArrayList<>(mData);
//        mData.clear();
//        mData.addAll(mData);
//        mDataAll = new ArrayList<>(mData);
//        mDataAll.addAll(mData);

        options = new RequestOptions().centerCrop().placeholder(R.drawable.aset).error(R.drawable.aset);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recycler_aset_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.myText1.setText(mData.get(position).getKode());
        holder.myText2.setText(mData.get(position).getNama());

        String kondisi = mData.get(position).getKondisi();
        if(kondisi.equals("B")){
            holder.myText3.setText("Baik");
        }else if(kondisi.equals("RR")){
            holder.myText3.setText("Rusak Ringan");
        }else  if(kondisi.equals("RB")){
            holder.myText3.setText("Rusak Berat");
        }else if(kondisi.equals("BTA")){
            holder.myText3.setText("Barang Hilang/Tidak Ada");
        }else{
            holder.myText3.setText(kondisi);
        }

        holder.myText4.setText(mData.get(position).getLokasi());

        Glide.with(mContext).load(Server.BASE_URL +  mData.get(position).getImage_url())
            .apply(RequestOptions.skipMemoryCacheOf(true))
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .apply(options).into(holder.myImages);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void filterList(ArrayList<ModelAset> filteredList) {
        mData = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return mDataFilter;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText1, myText2, myText3, myText4;
        ImageView myImages;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.textView);
            myText2 = itemView.findViewById(R.id.textView1);
            myText3 = itemView.findViewById(R.id.textView2);
            myText4 = itemView.findViewById(R.id.textView3);
            myImages = itemView.findViewById(R.id.imageView);
        }
    }
}
