package com.example.appxemfilm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemfilm.model.ChuDe;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.repositories.GenresRepository;
import com.example.appxemfilm.repositories.MovieRepository;

import java.util.List;

public class GenresListViewModel extends ViewModel {
    private MutableLiveData<List<ChuDe>> mMovies = new MutableLiveData<>();
    private GenresRepository genresRepository;

    public GenresListViewModel()
    {
        genresRepository = GenresRepository.getInstance();
    }
    public LiveData<List<ChuDe>> getChuDes()
    {
        return genresRepository.getChuDes();
    }

    public void searchChuDes() {
        genresRepository.getChuDes();
    }
}