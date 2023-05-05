package com.example.appxemfilm.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemfilm.R;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.viewmodels.FullImageAcitivity;

import java.util.List;

public class FilmImageRecyclerView extends RecyclerView.Adapter<FilmImageRecyclerView.FilmImageHolder> {
    Context context;
    List<String> listImageSource;

    public FilmImageRecyclerView(Context context, List<String> listImageSource) {
        this.context = context;
        this.listImageSource = listImageSource;
    }

    @NonNull
    @Override
    public FilmImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_image_item, parent, false);
        return new FilmImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmImageHolder holder, int position) {
        String imageSource = listImageSource.get(position);
        if(imageSource == null || imageSource == ""){
            return;
        }
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/" + imageSource)
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullImageAcitivity.class);
                intent.putExtra("imageSrc", imageSource);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listImageSource != null){
            return listImageSource.size();
        }
        return 0;
    }

    public class FilmImageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public FilmImageHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            imageView = itemView.findViewById(R.id.image_view_film_image);
        }
    }
}
