package com.example.appxemfilm.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    private static MovieRepository instance;
    private MovieApiClient movieApiClient;
    private MutableLiveData<List<MovieModel>>mMovies;
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
        //mMovies=new MutableLiveData<>();
    }

public LiveData<List<MovieModel>>getMovies()
{
    return movieApiClient.getMovies();
}

}
