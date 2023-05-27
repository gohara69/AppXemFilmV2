package com.example.appxemfilm.viewmodels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.example.appxemfilm.MainActivity;
import com.example.appxemfilm.R;
import com.example.appxemfilm.adapters.CastRecyclerView;
import com.example.appxemfilm.adapters.ChuDeRecyclerAdapter;
import com.example.appxemfilm.adapters.FilmImageRecyclerView;
import com.example.appxemfilm.adapters.FilmRecommandRecyclerView;
import com.example.appxemfilm.adapters.KeywordRecyclerView;
import com.example.appxemfilm.adapters.MovieViewHolder;
import com.example.appxemfilm.adapters.SimilarFilmsRecyclerView;
import com.example.appxemfilm.adapters.VideoRecyclerView;
import com.example.appxemfilm.model.CastModel;
import com.example.appxemfilm.model.ChuDe;
import com.example.appxemfilm.model.FilmVideoUrl;
import com.example.appxemfilm.model.Keyword;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.model.belongs_to_collection;
import com.example.appxemfilm.request.Servicey;
import com.example.appxemfilm.response.CastResponse;
import com.example.appxemfilm.response.FilmVideoResponse;
import com.example.appxemfilm.response.KeywordResponse;
import com.example.appxemfilm.response.MovieSearchResponse;
import com.example.appxemfilm.utils.CastApi;
import com.example.appxemfilm.utils.Credentials;
import com.example.appxemfilm.utils.MovieApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetail extends AppCompatActivity {

    ImageView imageView, back_btn;
    TextView text_view_ten_film, text_view_status, text_view_duriation, text_view_release_date;
    TextView text_view_budget, text_view_income, text_view_overview;
    RecyclerView recyclerViewChuDe, recyclerViewFilmImage, recyclerViewCastList, recycler_video_list;
    RecyclerView recyclerKeyword, recycler_khuyen_nghi;
    Context context;
    int id;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        anhXa();

        imageView.setBackgroundColor(Color.rgb(211,211,211));
        database = openOrCreateDatabase("AppFilm.db",MODE_PRIVATE, null);

        getDataFromIntent();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void anhXa(){
        imageView = findViewById(R.id.movie_image_view);
        text_view_ten_film = findViewById(R.id.text_view_ten_film);
        back_btn = findViewById(R.id.back_btn);
        recyclerViewChuDe = findViewById(R.id.recyclerView_chu_de);
        text_view_status = findViewById(R.id.text_view_status);
        text_view_duriation = findViewById(R.id.text_view_duriation);
        text_view_release_date = findViewById(R.id.text_view_release_date);
        text_view_budget = findViewById(R.id.text_view_budget);
        text_view_income = findViewById(R.id.text_view_income);
        text_view_overview = findViewById(R.id.text_view_overview);
        recyclerViewFilmImage = findViewById(R.id.recycler_view_film_image);
        recyclerViewCastList = findViewById(R.id.recycler_cast_list);
        recycler_video_list = findViewById(R.id.recycler_video_list);
        recyclerKeyword = findViewById(R.id.recycler_keyword);
        recycler_khuyen_nghi = findViewById(R.id.recycler_khuyen_nghi);
        context = this;
    }

    private void getDataFromIntent() {
        belongs_to_collection belong = new belongs_to_collection();
        if(getIntent().hasExtra("belong")){
            belongs_to_collection temp = (belongs_to_collection) getIntent().getSerializableExtra("belong");
            if(temp != null){
                if(temp.getBackdrop_path() != null && temp.getBackdrop_path() != ""){
                    belong.setBackdrop_path(temp.getBackdrop_path());
                }
                if(temp.getPoster_path() != null && temp.getPoster_path() != ""){
                    belong.setPoster_path(temp.getPoster_path());
                }
            }
        }
        if(getIntent().hasExtra("movie")) {
            setDataToView();
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            int[] arrChuDe = movieModel.getGenre_ids();
            List<ChuDe> chuDes = new ArrayList<>();
            for(int i = 0 ; i < arrChuDe.length ; i++){
                String sql = "Select name from CHUDE where id = " + arrChuDe[i];
                Cursor c = database.rawQuery(sql, null);
                c.moveToFirst();
                String name = c.getString(0);
                c.close();
                chuDes.add(new ChuDe(arrChuDe[i], name));
            }

            ChuDeRecyclerAdapter adapter = new ChuDeRecyclerAdapter(this, chuDes);
            recyclerViewChuDe.setAdapter(adapter);
            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewChuDe.setLayoutManager(horizontalLayoutManagaer);

            setDataToRecyclerViewFilmImage(movieModel, belong);
            setDataToRecyclerViewCastList(movieModel);
            setDataToRecyclerViewListVideo(movieModel);
            getDataToRecyclerViewRecommendation(movieModel);
            getKeyWords(movieModel);
        }
    }

    public void setDataToRecyclerViewFilmImage(MovieModel movieModel, belongs_to_collection belong){
        List<String> listImage = new ArrayList<>();
        if(movieModel.getBackdrop_path() != "" && movieModel.getBackdrop_path() != null){
            listImage.add(movieModel.getBackdrop_path());
        }
        if(movieModel.getPoster_path() != "" && movieModel.getPoster_path() != null){
            listImage.add(movieModel.getPoster_path());
        }
        if(belong.getPoster_path() != null && belong.getPoster_path() != ""){
            listImage.add(belong.getPoster_path());
        }
        if(belong.getBackdrop_path() != null && belong.getBackdrop_path() != ""){
            listImage.add(belong.getBackdrop_path());
        }
        FilmImageRecyclerView adapter = new FilmImageRecyclerView(this, listImage);
        recyclerViewFilmImage.setAdapter(adapter);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFilmImage.setLayoutManager(horizontalLayoutManagaer);
    }

    public void setDataToView(){
        MovieModel movieModel = getIntent().getParcelableExtra("movie");
        text_view_ten_film.setText(movieModel.getTitle());
        text_view_status.setText(movieModel.getStatus());
        text_view_duriation.setText(movieModel.getRuntime() + " phút");
        text_view_release_date.setText(movieModel.getRelease_date());
        if(movieModel.getBudget() != 0){
            text_view_budget.setText(movieModel.getBudget() + "$");
        } else {
            text_view_budget.setText("Không có số liệu");
        }
        if(movieModel.getRevenue() != 0){
            text_view_income.setText(movieModel.getRevenue() + "$");
        } else {
            text_view_income.setText("Không có số liệu");
        }

        if(movieModel.getMovie_overview().length() > 0){
            text_view_overview.setText(movieModel.getMovie_overview());
        } else {
            text_view_overview.setText("Không có thông tin");
        }
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/" + movieModel.getPoster_path())
                .into(imageView);
    }

    public void setDataToRecyclerViewCastList(MovieModel movieModel){
        Servicey servicey = new Servicey();
        CastApi castApi = servicey.getCastApi();
        Call<CastResponse> responseCall = castApi.getCasts(
                movieModel.getMovie_id(),
                Credentials.API_KEY,
                "vi-VN"
        );
        responseCall.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                if(response.code() == 200){
                    List<CastModel> listCast = new ArrayList<>(response.body().getCasts());
                    CastRecyclerView adapter = new CastRecyclerView(context, listCast);
                    recyclerViewCastList.setAdapter(adapter);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewCastList.setLayoutManager(horizontalLayoutManagaer);
                }
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {

            }
        });
    }

    public void setDataToRecyclerViewListVideo(MovieModel movieModel){
        Servicey servicey = new Servicey();
        MovieApi movieApi = servicey.getMovieApi();
        Call<FilmVideoResponse> responseCall = movieApi.getFilmVideos(
                movieModel.getMovie_id(),
                Credentials.API_KEY
        );
        responseCall.enqueue(new Callback<FilmVideoResponse>() {
            @Override
            public void onResponse(Call<FilmVideoResponse> call, Response<FilmVideoResponse> response) {
                if(response.code() == 200){
                    List<FilmVideoUrl> listUrl = response.body().getListUrl();
                    if(listUrl != null){
                        VideoRecyclerView adapter = new VideoRecyclerView(context, listUrl, getLifecycle());
                        recycler_video_list.setAdapter(adapter);
                        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                        recycler_video_list.setLayoutManager(horizontalLayoutManagaer);
                    }
                }
            }

            @Override
            public void onFailure(Call<FilmVideoResponse> call, Throwable t) {

            }
        });
    }

    public void getKeyWords(MovieModel movieModel){
        Servicey servicey = new Servicey();
        MovieApi movieApi = servicey.getMovieApi();
        Call<KeywordResponse> responseCall = movieApi.getKeywords(
                movieModel.getMovie_id(),
                Credentials.API_KEY,
                "vi-VN"
        );
        responseCall.enqueue(new Callback<KeywordResponse>() {
            @Override
            public void onResponse(Call<KeywordResponse> call, Response<KeywordResponse> response) {
                List<Keyword> listKeyword = response.body().getListKeyword();
                KeywordRecyclerView adapter = new KeywordRecyclerView(context, listKeyword);
                recyclerKeyword.setAdapter(adapter);

                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                recyclerKeyword.setLayoutManager(horizontalLayoutManagaer);
            }

            @Override
            public void onFailure(Call<KeywordResponse> call, Throwable t) {

            }
        });
    }

    public void getDataToRecyclerViewRecommendation(MovieModel movieModel){
        Servicey servicey = new Servicey();
        MovieApi movieApi = servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.getRecommendations(
                movieModel.getMovie_id(),
                Credentials.API_KEY,
                1,
                "vi-VN"
        );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                List<MovieModel> movies = response.body().getMovies();
                FilmRecommandRecyclerView adapter = new FilmRecommandRecyclerView(context, movies);
                recycler_khuyen_nghi.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                recycler_khuyen_nghi.setLayoutManager(horizontalLayoutManagaer);
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }
}