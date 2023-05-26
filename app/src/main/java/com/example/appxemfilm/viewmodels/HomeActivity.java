package com.example.appxemfilm.viewmodels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appxemfilm.R;
import com.example.appxemfilm.adapters.CastRecyclerView;
import com.example.appxemfilm.adapters.ChuDeRecyclerAdapter;
import com.example.appxemfilm.adapters.FilmByChuDeRecyclerView;
import com.example.appxemfilm.adapters.FilmRecommandRecyclerView;
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
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    public static String session_id;
    TextView text_view_header;
    RecyclerView recycler_dang_chieu, recycler_trending, recycler_chu_de, recycler_phim_hot;
    RecyclerView recycler_popular_person, recycler_top_reated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (getIntent().hasExtra("session")) {
            session_id = getIntent().getStringExtra("session");
        }
        AnhXa();
        getDataToRecyclerPhimDangChieu();
        getDataToRecyclerPhimTrending();
        getDataToRecyclerChuDe();
        getDataToRecyclerPhimHot();
        getDataToRecyclerHotCast();
        getDataToTopRated();
        Log.e("Tag",session_id);
    }

    public void AnhXa() {
        text_view_header = findViewById(R.id.text_view_header);
        recycler_dang_chieu = findViewById(R.id.recycler_dang_chieu);
        recycler_trending = findViewById(R.id.recycler_trending);
        recycler_chu_de = findViewById(R.id.recycler_chu_de);
        recycler_phim_hot = findViewById(R.id.recycler_phim_hot);
        recycler_popular_person = findViewById(R.id.recycler_popular_person);
        recycler_top_reated = findViewById(R.id.recycler_top_reated);
    }

    public void getDataToRecyclerPhimDangChieu() {
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
                    FilmRecommandRecyclerView adapter = new FilmRecommandRecyclerView(HomeActivity.this, movies);
                    recycler_dang_chieu.setAdapter(adapter);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
                    FilmByChuDeRecyclerView adapter = new FilmByChuDeRecyclerView(HomeActivity.this, movies);
                    recycler_trending.setAdapter(adapter);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
                ChuDeRecyclerAdapter adapter = new ChuDeRecyclerAdapter(HomeActivity.this, chuDes);
                recycler_chu_de.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
                FilmByChuDeRecyclerView adapter = new FilmByChuDeRecyclerView(HomeActivity.this, movies);
                recycler_phim_hot.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
                CastRecyclerView adapter = new CastRecyclerView(HomeActivity.this, castModels);
                recycler_popular_person.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
                FilmByChuDeRecyclerView adapter = new FilmByChuDeRecyclerView(HomeActivity.this, movies);
                recycler_top_reated.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recycler_top_reated.setLayoutManager(horizontalLayoutManagaer);
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Bạn có muốn thoát ứng dụng?")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
}