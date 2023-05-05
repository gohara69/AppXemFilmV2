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
import com.example.appxemfilm.MainActivity;
import com.example.appxemfilm.R;
import com.example.appxemfilm.model.ChuDe;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.request.Servicey;
import com.example.appxemfilm.utils.Credentials;
import com.example.appxemfilm.utils.MovieApi;
import com.example.appxemfilm.viewmodels.MovieDetail;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmByChuDeRecyclerView extends RecyclerView.Adapter<FilmByChuDeRecyclerView.FilmByChuDeViewHolder> {
    Context context;
    List<MovieModel> listMovie;

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
                Servicey servicey = new Servicey();
                MovieApi movieApi = servicey.getMovieApi();
                Call<MovieModel> responseCall = movieApi.getMovie(
                        movie.getMovie_id(),
                        Credentials.API_KEY,
                        "vi-VN"
                );

                responseCall.enqueue(new Callback<MovieModel>() {
                    @Override
                    public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                        if(response.code() == 200){
                            movie.setBudget(response.body().getBudget());
                            movie.setRevenue(response.body().getRevenue());
                            movie.setRuntime(response.body().getRuntime());
                            movie.setStatus(response.body().getStatus());
                            movie.setBackdrop_path(response.body().getBackdrop_path());
                            Intent intent = new Intent(context, MovieDetail.class);
                            intent.putExtra("movie", movie);
                            intent.putExtra("belong", response.body().getBelongs_to_collection());
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieModel> call, Throwable t) {

                    }
                });
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
}
