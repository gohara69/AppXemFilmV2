package com.example.appxemfilm.adapters;

import android.animation.LayoutTransition;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemfilm.R;
import com.example.appxemfilm.model.CastModel;
import com.example.appxemfilm.request.Servicey;
import com.example.appxemfilm.utils.CastApi;
import com.example.appxemfilm.utils.Credentials;
import com.example.appxemfilm.viewmodels.CastDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastRecyclerView extends RecyclerView.Adapter<CastRecyclerView.CastHolder> {
    Context context;
    List<CastModel> listCast;

    public CastRecyclerView(Context context, List<CastModel> listCast) {
        this.context = context;
        this.listCast = listCast;
    }

    @NonNull
    @Override
    public CastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item, parent, false);
        return new CastHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastHolder holder, int position) {
        CastModel cast = listCast.get(position);
        holder.ten_dien_vien.setText(cast.getName());
        holder.ten_vai_dien.setText(cast.getCharacter());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/" + cast.getProfile_path())
                .into(holder.image_view_dien_vien);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Tag", cast.getCast_id() + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listCast == null){
            return 0;
        }
        return listCast.size();
    }

    public class CastHolder extends RecyclerView.ViewHolder {

        ImageView image_view_dien_vien;
        TextView ten_dien_vien, ten_vai_dien;
        public CastHolder(@NonNull View itemView) {
            super(itemView);
            image_view_dien_vien = itemView.findViewById(R.id.cast_image_view);
            ten_dien_vien = itemView.findViewById(R.id.text_view_ten);
            ten_vai_dien = itemView.findViewById(R.id.text_view_vai_dien);
        }
    }
}
