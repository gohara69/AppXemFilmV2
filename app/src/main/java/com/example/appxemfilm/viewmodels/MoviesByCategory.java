package com.example.appxemfilm.viewmodels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appxemfilm.R;
import com.example.appxemfilm.adapters.FilmByChuDeRecyclerView;
import com.example.appxemfilm.adapters.MovieRecylerView;
import com.example.appxemfilm.adapters.OnMovieListener;
import com.example.appxemfilm.model.ChuDe;
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

public class MoviesByCategory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String id;
    private int pageNumber;
    ArrayList<MovieModel> listMovie = new ArrayList<>();
    FilmByChuDeRecyclerView adapter;
    TextView ten_chu_de;
    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_by_category);

        id = getIntent().getStringExtra("chude");
        recyclerView = findViewById(R.id.recyclerView);
        ten_chu_de = findViewById(R.id.text_view_header);
        btn_back = findViewById(R.id.btn_back);
        pageNumber = 1;

        getNameGenres();
        adapter = new FilmByChuDeRecyclerView(this, listMovie);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        getMoviesToRecyclerView();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)) {
                    pageNumber++;
                    getMoviesToRecyclerView();
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getNameGenres(){
        SQLiteDatabase database = openOrCreateDatabase("AppFilm.db",MODE_PRIVATE, null);
        String sql = "Select name from CHUDE where id = " + id;
        Cursor c = database.rawQuery(sql, null);
        c.moveToFirst();
        String name = c.getString(0);
        c.close();
        ten_chu_de.setText(name);
    }

    public void getMoviesToRecyclerView(){
        Servicey servicey = new Servicey();
        MovieApi movieApi = servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.getMoviesByGenresId(
                Credentials.API_KEY,
                "vi-VN",
                pageNumber,
                id
        );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code() == 200){
                    List<MovieModel> listMovies = new ArrayList<>(response.body().getMovies());
                    for(MovieModel n: listMovies){
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