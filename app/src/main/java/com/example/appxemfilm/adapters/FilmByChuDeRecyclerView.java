package com.example.appxemfilm.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
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
import com.example.appxemfilm.model.ChuDe;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.viewmodels.MovieDetail;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FilmByChuDeRecyclerView extends RecyclerView.Adapter<FilmByChuDeRecyclerView.FilmByChuDeViewHolder> {
    Context context;
    List<MovieModel> listMovie;
    private OnMovieListener onMovieListener;

    public FilmByChuDeRecyclerView(Context context, ArrayList<MovieModel> listMovie) {
        this.context = context;
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public FilmByChuDeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new FilmByChuDeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmByChuDeViewHolder holder, int position) {
        MovieModel movie = listMovie.get(position);
        if(movie == null){
            return;
        }
        if(listMovie.size() == 1){
            return;
        }
        context = holder.itemView.getContext();
        holder.text_view_ten_phim.setText(movie.getTitle());
        Glide.with(holder.image_view_poster.getContext())
                .load("https://image.tmdb.org/t/p/w500/"
                        + movie.getPoster_path())
                .into(holder.image_view_poster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetail.class);
                intent.putExtra("movie", movie);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listMovie != null){
            return listMovie.size();
        }
        return 0;
    }

    public class FilmByChuDeViewHolder extends RecyclerView.ViewHolder{
        ImageView image_view_poster;
        TextView text_view_ten_phim;
        public FilmByChuDeViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            image_view_poster = itemView.findViewById(R.id.image_view_poster);
            text_view_ten_phim = itemView.findViewById(R.id.text_view_ten_phim);
        }
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.listMovie = mMovies;
        notifyDataSetChanged();
    }

    public MovieModel getSelectedMovie(int position) {
        if(listMovie != null) {
            if(listMovie.size() > 0){
                return listMovie.get(position);
            }
        }
        return null;
    }
}
