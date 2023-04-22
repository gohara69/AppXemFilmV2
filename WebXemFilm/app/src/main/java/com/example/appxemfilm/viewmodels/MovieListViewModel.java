package com.example.appxemfilm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.repositories.MovieRepository;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel  extends ViewModel {
    private MovieRepository movieRepository;

    public MovieListViewModel()
    {
        movieRepository= MovieRepository.getInstance();

    }
    public LiveData<List<MovieModel>>getMovies()
    {
        return movieRepository.getMovies() ;
    }
}
