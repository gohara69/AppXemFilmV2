package com.example.appxemfilm.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.response.MovieSearchResponse;
import com.example.appxemfilm.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import com.example.appxemfilm.movieapp.AppExecutors;

public class MovieApiClient {
    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    public static MovieApiClient getInstance()
    {
        if(instance == null)
        {
          instance= new MovieApiClient();
        }
        return instance;
    }
    private MovieApiClient()
    {
        mMovies=new MutableLiveData<>();
    }
    public LiveData<List<MovieModel>> getMovies()
    {
        return mMovies;
    }

    public void searchMovieApi(String query, int pageNumber)
    {
        if(retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable =  new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler= AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
             myHandler.cancel(true) ;

            }
        },300, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMoviesRunnable implements Runnable {
        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getMovies(query, pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1){
                        mMovies.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag","Error " + error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }
        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
            Servicey servicey = new Servicey();
            return servicey.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber,
                    "vi-VN"
            );
        }

        private void cancelRequest(){
            Log.e("Tag","Cancel Search Request");
            cancelRequest = true;
        }
    }
}