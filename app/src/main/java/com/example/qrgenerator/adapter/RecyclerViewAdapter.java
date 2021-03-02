package com.example.qrgenerator.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrgenerator.R;
import com.example.qrgenerator.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private final List<DataModel> dataModelList;
    private final LayoutInflater layoutInflater;
    private ItemClickListener clickListener;

    public RecyclerViewAdapter(Context mContext, List<DataModel> dataModelList, ItemClickListener clickListener) {
        this.dataModelList = dataModelList;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameTextView.setText(dataModelList.get(position).getName());
        holder.addressTextView.setText(dataModelList.get(position).getAddress());
        holder.itemView.setOnClickListener(view -> {
            clickListener.onItemClick(dataModelList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameTextView;
        TextView addressTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.txt_name);
            addressTextView = itemView.findViewById(R.id.txt_address);
        }
    }

    public interface ItemClickListener{
        void onItemClick(DataModel dataModel);
    }
}
