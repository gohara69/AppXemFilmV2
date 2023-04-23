package com.example.appxemfilm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpSearchView();

        recyclerView = findViewById(R.id.recyclerView);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        ConfigureRecylerView();
        ObserveAnyChange();
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
                        Log.v("Tag", "onChanged: " + movieModel.getTitle());
                        movieRecylerAdapter.setmMovies(movieModels);
                    }
                }
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
        Toast.makeText(this, "The position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClick(String category) {

    }
}