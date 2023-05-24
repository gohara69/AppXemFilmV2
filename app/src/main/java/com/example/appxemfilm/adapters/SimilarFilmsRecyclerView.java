package com.example.appxemfilm.adapters;

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

public class SimilarFilmsRecyclerView extends RecyclerView.Adapter<SimilarFilmsRecyclerView.SimilarFilmHolder>{

    Context context;
    List<MovieModel> movies;

    public SimilarFilmsRecyclerView(Context context, List<MovieModel> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public SimilarFilmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_in_list, parent, false);
        return new SimilarFilmHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarFilmHolder holder, int position) {
        MovieModel movie = movies.get(position);
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
        return 0;
    }

    public class SimilarFilmHolder extends RecyclerView.ViewHolder {

        ImageView image_view_poster;
        TextView text_view_ten_phim;
        public SimilarFilmHolder(@NonNull View itemView) {
            super(itemView);
            image_view_poster = itemView.findViewById(R.id.image_view_poster);
            text_view_ten_phim = itemView.findViewById(R.id.text_view_ten_phim);
        }
    }
}
