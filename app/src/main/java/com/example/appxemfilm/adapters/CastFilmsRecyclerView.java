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

public class CastFilmsRecyclerView extends RecyclerView.Adapter<CastFilmsRecyclerView.CastFilmHolder>{
    Context context;
    List<MovieModel> modelList;

    public CastFilmsRecyclerView(Context context, List<MovieModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public CastFilmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_film_item, parent, false);
        return new CastFilmHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastFilmHolder holder, int position) {
        MovieModel movieModel = modelList.get(position);

        if(movieModel.getTitle().length() > 15){
            holder.textView.setText(movieModel.getTitle().substring(0, 15) + "..");
        } else {
            holder.textView.setText(movieModel.getTitle());
        }
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/" + movieModel.getPoster_path())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Servicey servicey = new Servicey();
                MovieApi movieApi = servicey.getMovieApi();
                Call<MovieModel> responseCall = movieApi.getMovie(
                        movieModel.getMovie_id(),
                        Credentials.API_KEY,
                        "vi-VN"
                );

                responseCall.enqueue(new Callback<MovieModel>() {
                    @Override
                    public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                        if(response.code() == 200){
                            movieModel.setBudget(response.body().getBudget());
                            movieModel.setRevenue(response.body().getRevenue());
                            movieModel.setRuntime(response.body().getRuntime());
                            movieModel.setStatus(response.body().getStatus());
                            movieModel.setBackdrop_path(response.body().getBackdrop_path());
                            Intent intent = new Intent(context, MovieDetail.class);
                            intent.putExtra("movie", movieModel);
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
        if(modelList == null){
            return 0;
        }
        return modelList.size();
    }

    public class CastFilmHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public CastFilmHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_ten_phim);
            imageView = itemView.findViewById(R.id.image_view_cast_image);
        }
    }
}
