package com.example.appxemfilm.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemfilm.R;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.request.Servicey;
import com.example.appxemfilm.utils.Credentials;
import com.example.appxemfilm.utils.MovieApi;
import com.example.appxemfilm.viewmodels.MovieDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRecyclerView extends RecyclerView.Adapter<MoviesRecyclerView.MoviesHolder>{

    Context context;
    List<MovieModel> movies;

    public MoviesRecyclerView(List<MovieModel> movies){
        this.movies = movies;
    }
    @NonNull
    @Override
    public MoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_in_list, parent, false);
        return new MoviesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesHolder holder, int position) {
        MovieModel movie = movies.get(position);
        holder.textView.setText(movie.getTitle());
        Glide.with(holder.imageView.getContext())
                .load("https://image.tmdb.org/t/p/w500/"
                        + movie.getPoster_path())
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Tag", "Đã click");
            }
        });
    }

    @Override
    public int getItemCount() {
        if(movies != null){
            return movies.size();
        }
        return 0;
    }

    public class MoviesHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public MoviesHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_poster);
            textView = itemView.findViewById(R.id.text_view_ten_phim);
        }
    }
}
