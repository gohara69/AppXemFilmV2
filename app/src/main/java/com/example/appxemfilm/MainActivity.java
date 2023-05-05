package com.example.appxemfilm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;

import com.example.appxemfilm.adapters.MovieRecylerView;
import com.example.appxemfilm.adapters.OnMovieListener;
import com.example.appxemfilm.model.ChuDe;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.request.Servicey;
import com.example.appxemfilm.response.GenreResponse;
import com.example.appxemfilm.response.MovieResponse;
import com.example.appxemfilm.response.MovieSearchResponse;
import com.example.appxemfilm.utils.Credentials;
import com.example.appxemfilm.utils.GenreApi;
import com.example.appxemfilm.utils.MovieApi;
import com.example.appxemfilm.viewmodels.MovieDetail;
import com.example.appxemfilm.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMovieListener {
    private RecyclerView recyclerView;
    private MovieRecylerView movieRecylerAdapter;
    private MovieListViewModel movieListViewModel;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpSearchView();

        recyclerView = findViewById(R.id.recyclerView);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        ConfigureRecylerView();
        ObserveAnyChange();

        database = openOrCreateDatabase("AppFilm.db",MODE_PRIVATE, null);
        try{
            String sql = "CREATE TABLE CHUDE(id INTEGER primary key, name TEXT)";
            database.execSQL(sql);
        } catch (Exception e){
            Log.v("Error: ", "Table đã tồn tại");
        }
        getAllGenre();
    }
    private void ConfigureRecylerView(){
        movieRecylerAdapter = new MovieRecylerView(this);
        recyclerView.setAdapter(movieRecylerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)) {
                    movieListViewModel.searchNextPage();
                }
            }
        });
    }
    private void ObserveAnyChange()
    {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels) {
                        movieRecylerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }

    public void getAllGenre(){
        Servicey servicey = new Servicey();
        GenreApi genreApi = servicey.getGenreApi();
        Call<GenreResponse> responseCall = genreApi.getGenres(
                Credentials.API_KEY,
                "vi-VN"
        );
        responseCall.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                if(response.code() == 200){
                    List<ChuDe> listChuDe = new ArrayList<>(response.body().getChuDes());
                    for(ChuDe cd: listChuDe){
                        ContentValues chuDeValue = new ContentValues();
                        chuDeValue.put("id",cd.getId());
                        chuDeValue.put("name", cd.getName());
                        if(database.insert("CHUDE", null, chuDeValue) == -1){
                            Log.v("Error: ", "Lỗi khi insert vào bảng");
                        } else {
                            Log.v("Tag: ", "insert thành công");
                        }
                    }
                } else {
                    Log.v("Tag: ", "Error " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {

            }
        });
    }

    private void setUpSearchView(){
        final SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(
                        query,
                        1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        Servicey servicey = new Servicey();
        MovieApi movieApi = servicey.getMovieApi();
        MovieModel movieModel = movieRecylerAdapter.getSelectedMovie(position);
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
                    Intent intent = new Intent(MainActivity.this, MovieDetail.class);
                    intent.putExtra("movie", movieModel);
                    intent.putExtra("belong", response.body().getBelongs_to_collection());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCategoryClick(String category) {

    }
}