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
import com.example.appxemfilm.adapters.CastRecyclerView;
import com.example.appxemfilm.adapters.ChuDeRecyclerAdapter;
import com.example.appxemfilm.adapters.FilmByChuDeRecyclerView;
import com.example.appxemfilm.adapters.FilmRecommandRecyclerView;
import com.example.appxemfilm.adapters.MoviesRecyclerView;
import com.example.appxemfilm.model.CastModel;
import com.example.appxemfilm.model.ChuDe;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.request.Servicey;
import com.example.appxemfilm.response.CastResponse;
import com.example.appxemfilm.response.GenreResponse;
import com.example.appxemfilm.response.MovieSearchResponse;
import com.example.appxemfilm.utils.CastApi;
import com.example.appxemfilm.utils.Credentials;
import com.example.appxemfilm.utils.GenreApi;
import com.example.appxemfilm.utils.MovieApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment{
    RecyclerView recycler_dang_chieu, recycler_trending, recycler_chu_de;
    RecyclerView recycler_phim_hot, recycler_popular_person, recycler_top_reated;
    public HomeFragment() {
    }

    public void AnhXa(View view){
        recycler_dang_chieu = view.findViewById(R.id.recycler_dang_chieu);
        recycler_trending = view.findViewById(R.id.recycler_trending);
        recycler_chu_de = view.findViewById(R.id.recycler_chu_de);
        recycler_phim_hot = view.findViewById(R.id.recycler_phim_hot);
        recycler_popular_person = view.findViewById(R.id.recycler_popular_person);
        recycler_top_reated = view.findViewById(R.id.recycler_top_reated);
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
        getDataToRecyclerPhimTrending();
        getDataToRecyclerChuDe();
        getDataToRecyclerPhimHot();
        getDataToRecyclerHotCast();
        getDataToTopRated();
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
                if (response.code() == 200) {
                    List<MovieModel> movies = response.body().getMovies();
                    FilmRecommandRecyclerView adapter = new FilmRecommandRecyclerView(getContext(), movies);
                    recycler_dang_chieu.setAdapter(adapter);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recycler_dang_chieu.setLayoutManager(horizontalLayoutManagaer);
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

    public void getDataToRecyclerPhimTrending() {
        Servicey servicey = new Servicey();
        MovieApi movieApi = servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.getMovieOnTrend(
                Credentials.API_KEY,
                "vi-VN",
                1
        );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    List<MovieModel> movies = response.body().getMovies();
                    FilmByChuDeRecyclerView adapter = new FilmByChuDeRecyclerView(getContext(), movies);
                    recycler_trending.setAdapter(adapter);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recycler_trending.setLayoutManager(horizontalLayoutManagaer);
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }
    public void getDataToRecyclerChuDe() {
        Servicey servicey = new Servicey();
        GenreApi movieApi = servicey.getGenreApi();
        Call<GenreResponse> responseCall = movieApi.getGenres(
                Credentials.API_KEY,
                "vi-VN"
        );
        responseCall.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                List<ChuDe> chuDes = response.body().getChuDes();
                ChuDeRecyclerAdapter adapter = new ChuDeRecyclerAdapter(getContext(), chuDes);
                recycler_chu_de.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recycler_chu_de.setLayoutManager(horizontalLayoutManagaer);
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {

            }
        });
    }
    public void getDataToRecyclerPhimHot() {
        Servicey servicey = new Servicey();
        MovieApi movieApi = servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.getMovieOnPopular(
                Credentials.API_KEY,
                "vi-VN",
                1,
                "VN"
        );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                List<MovieModel> movies = response.body().getMovies();
                FilmByChuDeRecyclerView adapter = new FilmByChuDeRecyclerView(getContext(), movies);
                recycler_phim_hot.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recycler_phim_hot.setLayoutManager(horizontalLayoutManagaer);
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }
    public void getDataToRecyclerHotCast() {
        Servicey servicey = new Servicey();
        CastApi movieApi = servicey.getCastApi();
        Call<CastResponse> responseCall = movieApi.getPopularCast(
                Credentials.API_KEY,
                "vi-VN",
                1
        );
        responseCall.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                List<CastModel> castModels = response.body().getResults();
                CastRecyclerView adapter = new CastRecyclerView(getContext(), castModels);
                recycler_popular_person.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recycler_popular_person.setLayoutManager(horizontalLayoutManagaer);
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {

            }
        });
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
                recycler_top_reated.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recycler_top_reated.setLayoutManager(horizontalLayoutManagaer);
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