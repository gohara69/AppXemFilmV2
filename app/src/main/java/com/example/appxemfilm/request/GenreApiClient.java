package com.example.appxemfilm.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appxemfilm.model.ChuDe;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.movieapp.AppExecutors;
import com.example.appxemfilm.response.GenreResponse;
import com.example.appxemfilm.response.MovieSearchResponse;
import com.example.appxemfilm.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class GenreApiClient {
    private MutableLiveData<List<ChuDe>> chuDes;
    private static GenreApiClient instance;
    private RetrieveGenresRunnable retrieveGenresRunnable;

    public static GenreApiClient getInstance()
    {
        if(instance == null)
        {
            instance= new GenreApiClient();
        }
        return instance;
    }

    private GenreApiClient()
    {
        chuDes=new MutableLiveData<>();
    }
    public LiveData<List<ChuDe>> getChuDes()
    {
        return chuDes;
    }

    public void searchGenreApi()
    {
        if(retrieveGenresRunnable != null) {
            retrieveGenresRunnable = null;
        }

        retrieveGenresRunnable =  new GenreApiClient.RetrieveGenresRunnable();

        final Future myHandler= AppExecutors.getInstance().networkIO().submit(retrieveGenresRunnable);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true) ;

            }
        },300, TimeUnit.MILLISECONDS);
    }

    private class RetrieveGenresRunnable implements Runnable {
        boolean cancelRequest;

        public RetrieveGenresRunnable() {
            cancelRequest = false;
        }
        @Override
        public void run() {
            try {
                Response response = getChuDes().execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<ChuDe> list = new ArrayList<>(((GenreResponse)response.body()).getChuDes());
                    List<ChuDe> currentChuDes = chuDes.getValue();
                    currentChuDes.addAll(list);
                    chuDes.postValue(currentChuDes);
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag","Error " + error);
                    chuDes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                chuDes.postValue(null);
            }
        }

        private Call<GenreResponse> getChuDes(){
            Servicey servicey = new Servicey();
            return servicey.getGenreApi().getGenres(
                    Credentials.API_KEY,
                    "vi-VN"
            );
        }
    }
}
