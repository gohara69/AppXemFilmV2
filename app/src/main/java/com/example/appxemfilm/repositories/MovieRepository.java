package com.example.appxemfilm.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    private static MovieRepository instance;
    private MovieApiClient movieApiClient;
    public static MovieRepository getInstance() {
        if(instance==null)
        {
            instance= new MovieRepository();

        }
        return instance;
    }

    private MovieRepository()
    {
        movieApiClient= MovieApiClient.getInstance();
    }
    public LiveData<List<MovieModel>> getMovies()
{
    return movieApiClient.getMovies();
}

    public void searchMovieApi(String query, int pageNumber) {
        movieApiClient.searchMovieApi(query, pageNumber);
    }
}