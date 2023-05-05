package com.example.appxemfilm.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemfilm.R;
import com.example.appxemfilm.model.CastImage;
import com.example.appxemfilm.viewmodels.FullImageAcitivity;

import java.util.List;

public class CastImageRecyclerView extends RecyclerView.Adapter<CastImageRecyclerView.CastImageHolder>{
    Context context;
    List<CastImage> listCastImages;

    public CastImageRecyclerView(Context context, List<CastImage> listCastImages) {
        this.context = context;
        this.listCastImages = listCastImages;
    }

    @NonNull
    @Override
    public CastImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_image_item, parent, false);
        return new CastImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastImageHolder holder, int position) {
        CastImage castImage = listCastImages.get(position);
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/" + castImage.getFile_path())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullImageAcitivity.class);
                intent.putExtra("imageSrc", castImage.getFile_path());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listCastImages == null){
            return 0;
        }
        return listCastImages.size();
    }

    public class CastImageHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public CastImageHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_cast_image);
        }
    }
}
