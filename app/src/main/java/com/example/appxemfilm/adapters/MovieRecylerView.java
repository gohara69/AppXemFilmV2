package com.example.appxemfilm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemfilm.R;
import com.example.appxemfilm.model.MovieModel;

import java.util.List;

public class MovieRecylerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MovieViewHolder)holder).title.setText(mMovies.get(position).getTitle());
        ((MovieViewHolder)holder).release_date.setText(mMovies.get(position).getRelease_date());
        ((MovieViewHolder)holder).duration.setText(mMovies.get(position).getOriginal_language());
        ((MovieViewHolder)holder).ratingBar.setRating((mMovies.get(position).getVote_average())/2);

        Glide.with(((MovieViewHolder) holder).imageView.getContext())
                .load("https://image.tmdb.org/t/p/w500/"
                        + mMovies.get(position).getPoster_path())
                .into(((MovieViewHolder) holder).imageView);
    }

    public MovieRecylerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @Override
    public int getItemCount() {
        if(mMovies != null){
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    public MovieModel getSelectedMovie(int position) {
        if(mMovies != null) {
            if(mMovies.size() > 0){
                return mMovies.get(position);
            }
        }
        return null;
    }
}
