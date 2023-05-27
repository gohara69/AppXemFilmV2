package com.example.appxemfilm.viewmodels;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appxemfilm.R;
import com.example.appxemfilm.adapters.FilmByChuDeRecyclerView;
import com.example.appxemfilm.adapters.FilmRecommandRecyclerView;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.request.Servicey;
import com.example.appxemfilm.response.MovieSearchResponse;
import com.example.appxemfilm.utils.Credentials;
import com.example.appxemfilm.utils.MovieApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangChieuFragment extends Fragment {

    RecyclerView recyclerView;
    FilmRecommandRecyclerView adapter;
    List<MovieModel> listMovie = new ArrayList<>();
    int pageNumber;

    public DangChieuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_chieu, container, false);
        AnhXa(view);
        pageNumber = 1;
        adapter = new FilmRecommandRecyclerView(getContext(), listMovie);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
        getDataToRecyclerPhimDangChieu();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)) {
                    pageNumber++;
                    getDataToRecyclerPhimDangChieu();
                }
            }
        });
        return view;
    }

    public void AnhXa(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    public void initRecyclerView(){
//        adapter = new FilmRecommandRecyclerView(getContext(), listMovie);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        getDataToRecyclerPhimDangChieu();
    }

    public void getDataToRecyclerPhimDangChieu() {
        Servicey servicey = new Servicey();
        MovieApi movieApi = servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.getMovieOnScreen(
                Credentials.API_KEY,
                "vi-VN",
                pageNumber
        );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    List<MovieModel> movies = response.body().getMovies();
                    for(MovieModel n: movies){
                        listMovie.add(n);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }
}