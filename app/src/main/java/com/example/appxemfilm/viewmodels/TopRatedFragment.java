package com.example.appxemfilm.viewmodels;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appxemfilm.R;
import com.example.appxemfilm.adapters.FilmByChuDeRecyclerView;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.request.Servicey;
import com.example.appxemfilm.response.MovieSearchResponse;
import com.example.appxemfilm.utils.Credentials;
import com.example.appxemfilm.utils.MovieApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedFragment extends Fragment {
    RecyclerView recyclerView;
    public TopRatedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_rated, container, false);
        AnhXa(view);
        getDataToTopRated();
        return view;
    }

    public void AnhXa(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    public void getDataToTopRated(){
        Servicey servicey = new Servicey();
        MovieApi movieApi = servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.getMovieTopRate(
                Credentials.API_KEY,
                "vi-VN",
                1
        );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                List<MovieModel> movies = response.body().getMovies();
                FilmByChuDeRecyclerView adapter = new FilmByChuDeRecyclerView(getContext(), movies);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }
}