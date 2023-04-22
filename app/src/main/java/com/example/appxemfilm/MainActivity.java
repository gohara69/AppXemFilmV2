package com.example.appxemfilm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appxemfilm.adapters.MovieRecylerView;
import com.example.appxemfilm.adapters.OnMovieListener;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.request.Servicey;
import com.example.appxemfilm.response.MovieSearchResponse;
import com.example.appxemfilm.utils.Credentials;
import com.example.appxemfilm.utils.MovieApi;
import com.example.appxemfilm.viewmodels.MovieListViewModel;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMovieListener {
    private RecyclerView recyclerView;
    private MovieRecylerView movieRecylerAdapter;
    private MovieListViewModel movieListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        recyclerView = findViewById(R.id.recyclerView);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        ConfigureRecylerView();
        ObserveAnyChange();
        searchMovieApi("Fast",1);
    }
    private void ConfigureRecylerView(){
        movieRecylerAdapter = new MovieRecylerView(this);
        recyclerView.setAdapter(movieRecylerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void ObserveAnyChange()
    {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels) {
                        Log.v("Tag", "onChanged: " + movieModel.getTitle());
                        movieRecylerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }

    private void searchMovieApi(String query, int pageNumber) {
        movieListViewModel.searchMovieApi(query, pageNumber);
    }

    private void GetRetrofitResponse()
    {
        //#7
        //Test API Search Response
        //Kết thúc phần 7
        Servicey service = new Servicey();
        MovieApi movieApi = service.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi
                 .searchMovie(
                        Credentials.API_KEY,
                        "Action",
                         1
                );

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code() == 200){
                    Log.v(  "Tag", "The response: " + response.body().toString());
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                    for(MovieModel movie: movies){
                        Log.v(  "Tag", "The release date : " + movie.getRelease_date());
                    }
                } else {
                    try {
                        Log.v("Tag", "Error: " + response.errorBody().toString());
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }
    private void GetRetrofitResponseAccordingToID()
    {
        //#8
        //Test API Search Response
        //Kết thúc phần 8
        Servicey service = new Servicey();
        MovieApi movieApi = service.getMovieApi();

        Call<MovieModel> responseCall = movieApi
                .getMovie(
                        343611,
                        Credentials.API_KEY
                );

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.code() == 200){
                    MovieModel movie = response.body();
                    Log.v(  "Tag", "The release date : " + movie.getTitle());
                } else {
                    try {
                        Log.v("Tag", "Error: " + response.errorBody().toString());
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        Toast.makeText(this, "The position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClick(String category) {

    }
}