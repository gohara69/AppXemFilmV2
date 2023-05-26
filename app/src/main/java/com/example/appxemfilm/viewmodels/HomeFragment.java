package com.example.appxemfilm.viewmodels;

import android.app.Service;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appxemfilm.R;
import com.example.appxemfilm.adapters.FilmRecommandRecyclerView;
import com.example.appxemfilm.adapters.MoviesRecyclerView;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.request.Servicey;
import com.example.appxemfilm.response.MovieSearchResponse;
import com.example.appxemfilm.utils.Credentials;
import com.example.appxemfilm.utils.MovieApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment{
    RecyclerView recycler_dang_chieu;
    public HomeFragment() {
    }

    public void AnhXa(View view){
        recycler_dang_chieu = view.findViewById(R.id.recycler_dang_chieu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        AnhXa(view);
        initRecyclerDangChieu();
        getDataToRecyclerDangChieu();
        return view;
    }

    public void initRecyclerDangChieu(){
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        recycler_dang_chieu.setLayoutManager(horizontalLayoutManagaer);
        recycler_dang_chieu.setAdapter(new FilmRecommandRecyclerView(getContext(), null));
        recycler_dang_chieu.setNestedScrollingEnabled(false);
    }

    public void getDataToRecyclerDangChieu(){
        Servicey servicey = new Servicey();
        MovieApi movieApi = servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.getMovieOnScreen(
                Credentials.API_KEY,
                "vi-VN",
                1
        );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code() == 200){
                    List<MovieModel> movies = response.body().getMovies();
                    MoviesRecyclerView adapter = new MoviesRecyclerView(movies);
                    recycler_dang_chieu.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}