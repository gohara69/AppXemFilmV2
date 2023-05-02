package com.example.appxemfilm.repositories;

import androidx.lifecycle.LiveData;

import com.example.appxemfilm.model.ChuDe;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.request.GenreApiClient;
import com.example.appxemfilm.request.MovieApiClient;

import java.util.List;

public class GenresRepository {
    private static GenresRepository instance;
    private GenreApiClient genreApiClient;

    public static GenresRepository getInstance() {
        if(instance==null)
        {
            instance= new GenresRepository();

        }
        return instance;
    }

    private GenresRepository()
    {
        genreApiClient= genreApiClient.getInstance();
    }
    public LiveData<List<ChuDe>> getChuDes()
    {
        return genreApiClient.getChuDes();
    }

    public void searchGenresApi() {
        genreApiClient.getChuDes();
    }
}
