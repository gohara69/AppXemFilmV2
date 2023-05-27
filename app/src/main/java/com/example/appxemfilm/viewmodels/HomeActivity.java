package com.example.appxemfilm.viewmodels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.example.appxemfilm.MainActivity;
import com.example.appxemfilm.R;
import com.example.appxemfilm.adapters.CastRecyclerView;
import com.example.appxemfilm.adapters.ChuDeRecyclerAdapter;
import com.example.appxemfilm.adapters.FilmByChuDeRecyclerView;
import com.example.appxemfilm.adapters.FilmRecommandRecyclerView;
import com.example.appxemfilm.databinding.ActivityHomeBinding;
import com.example.appxemfilm.model.CastModel;
import com.example.appxemfilm.model.ChuDe;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.model.account;
import com.example.appxemfilm.request.Servicey;
import com.example.appxemfilm.response.CastResponse;
import com.example.appxemfilm.response.GenreResponse;
import com.example.appxemfilm.response.MovieSearchResponse;
import com.example.appxemfilm.utils.AccountApi;
import com.example.appxemfilm.utils.CastApi;
import com.example.appxemfilm.utils.Credentials;
import com.example.appxemfilm.utils.GenreApi;
import com.example.appxemfilm.utils.MovieApi;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_DANG_CHIEU = 2;
    private static final int FRAGMENT_HOT = 3;
    private static final int FRAGMENT_TOP_RATE = 4;
    private int currentFragment = FRAGMENT_HOME;
    public static String session_id;
    TextView text_view_header, nav_header_name;
    RecyclerView recycler_dang_chieu, recycler_trending, recycler_chu_de, recycler_phim_hot;
    RecyclerView recycler_popular_person, recycler_top_reated;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView btn_scroll_draw, image_nav_header, btn_search;
    ActivityHomeBinding homeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());

        if (getIntent().hasExtra("session")) {
            session_id = getIntent().getStringExtra("session");
        }
        AnhXa();
//        getDataToRecyclerPhimDangChieu();
//        getDataToRecyclerPhimTrending();
//        getDataToRecyclerChuDe();
//        getDataToRecyclerPhimHot();
//        getDataToRecyclerHotCast();
//        getDataToTopRated();
        getDataToDrawer();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_dang_chieu:{
                        if(currentFragment != FRAGMENT_DANG_CHIEU){
                            replaceFragment(new DangChieuFragment());
                            currentFragment = FRAGMENT_DANG_CHIEU;
                            text_view_header.setText("Phim đang chiếu");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                    case R.id.item_hot: {
                        if(currentFragment != FRAGMENT_HOT){
                            replaceFragment(new PhimHotFragment());
                            currentFragment = FRAGMENT_HOT;
                            text_view_header.setText("Phim hot");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                    case R.id.item_home: {
                        if(currentFragment != FRAGMENT_HOME){
                            replaceFragment(new HomeFragment());
                            currentFragment = FRAGMENT_HOME;
                            text_view_header.setText("Trang chủ");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                    case R.id.item_top_rate: {
                        if(currentFragment != FRAGMENT_TOP_RATE){
                            replaceFragment(new TopRatedFragment());
                            currentFragment = FRAGMENT_TOP_RATE;
                            text_view_header.setText("Trang top rate");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                    case R.id.item_exit:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        logout(HomeActivity.this);
                        return true;
                }
                return false;
            }
        });
        homeBinding.btnScrollDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        replaceFragment(new HomeFragment());
        homeBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void logout(HomeActivity homeActivity){
        AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity);
        builder.setTitle("Thoát");
        builder.setMessage("Bạn có thực sự muốn thoát?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
            }
        });
        builder.setNegativeButton("Không", null);
        builder.show();
    }

    public void AnhXa() {
        text_view_header = findViewById(R.id.text_view_header);
        recycler_dang_chieu = findViewById(R.id.recycler_dang_chieu);
//        recycler_trending = findViewById(R.id.recycler_trending);
//        recycler_chu_de = findViewById(R.id.recycler_chu_de);
//        recycler_phim_hot = findViewById(R.id.recycler_phim_hot);
//        recycler_popular_person = findViewById(R.id.recycler_popular_person);
//        recycler_top_reated = findViewById(R.id.recycler_top_reated);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        btn_scroll_draw = findViewById(R.id.btn_scroll_draw);
        View headerview = navigationView.getHeaderView(0);
        nav_header_name = (TextView) headerview.findViewById(R.id.nav_header_name);
        image_nav_header = (ImageView) headerview.findViewById(R.id.image_nav_header);
        //btn_search = findViewById(R.id.btn_search);
    }

    public void getDataToRecyclerPhimDangChieu() {
        Servicey servicey = new Servicey();
        MovieApi movieApi = servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.getMovieOnScreen(
                Credentials.API_KEY,
                "vi-VN",
                1
        );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    List<MovieModel> movies = response.body().getMovies();
                    FilmRecommandRecyclerView adapter = new FilmRecommandRecyclerView(HomeActivity.this, movies);
                    recycler_dang_chieu.setAdapter(adapter);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    recycler_dang_chieu.setLayoutManager(horizontalLayoutManagaer);
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

    public void getDataToRecyclerPhimTrending() {
        Servicey servicey = new Servicey();
        MovieApi movieApi = servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.getMovieOnTrend(
                Credentials.API_KEY,
                "vi-VN",
                1
        );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    List<MovieModel> movies = response.body().getMovies();
                    FilmByChuDeRecyclerView adapter = new FilmByChuDeRecyclerView(HomeActivity.this, movies);
                    recycler_trending.setAdapter(adapter);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    recycler_trending.setLayoutManager(horizontalLayoutManagaer);
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

    public void getDataToRecyclerChuDe() {
        Servicey servicey = new Servicey();
        GenreApi movieApi = servicey.getGenreApi();
        Call<GenreResponse> responseCall = movieApi.getGenres(
                Credentials.API_KEY,
                "vi-VN"
        );
        responseCall.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                List<ChuDe> chuDes = response.body().getChuDes();
                ChuDeRecyclerAdapter adapter = new ChuDeRecyclerAdapter(HomeActivity.this, chuDes);
                recycler_chu_de.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recycler_chu_de.setLayoutManager(horizontalLayoutManagaer);
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {

            }
        });
    }

    public void getDataToRecyclerPhimHot() {
        Servicey servicey = new Servicey();
        MovieApi movieApi = servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.getMovieOnPopular(
                Credentials.API_KEY,
                "vi-VN",
                1,
                "VN"
        );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                List<MovieModel> movies = response.body().getMovies();
                FilmByChuDeRecyclerView adapter = new FilmByChuDeRecyclerView(HomeActivity.this, movies);
                recycler_phim_hot.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recycler_phim_hot.setLayoutManager(horizontalLayoutManagaer);
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

    public void getDataToRecyclerHotCast() {
        Servicey servicey = new Servicey();
        CastApi movieApi = servicey.getCastApi();
        Call<CastResponse> responseCall = movieApi.getPopularCast(
                Credentials.API_KEY,
                "vi-VN",
                1
        );
        responseCall.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                List<CastModel> castModels = response.body().getResults();
                CastRecyclerView adapter = new CastRecyclerView(HomeActivity.this, castModels);
                recycler_popular_person.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recycler_popular_person.setLayoutManager(horizontalLayoutManagaer);
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {

            }
        });
    }

    public void getDataToTopRated(){
        Servicey servicey = new Servicey();
        MovieApi movieApi = servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.getMovieTopRate(
                Credentials.API_KEY,
                "vi-VN",
                1
        );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                List<MovieModel> movies = response.body().getMovies();
                FilmByChuDeRecyclerView adapter = new FilmByChuDeRecyclerView(HomeActivity.this, movies);
                recycler_top_reated.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recycler_top_reated.setLayoutManager(horizontalLayoutManagaer);
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Bạn có muốn thoát ứng dụng?")
                    .setCancelable(false)
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("Không",null)
                    .show();
        }
    }

    public void getDataToDrawer(){
        Servicey servicey = new Servicey();
        AccountApi movieApi = servicey.getAccountApi();
        Call<account> responseCall = movieApi.getAccout(
                Credentials.API_KEY,
                HomeActivity.session_id
        );
        responseCall.enqueue(new Callback<account>() {
            @Override
            public void onResponse(Call<account> call, Response<account> response) {
                if(response.code() == 200){
                    account account = response.body();
                    nav_header_name.setText(account.getUsername());
                    Glide.with(HomeActivity.this)
                            .load("https://image.tmdb.org/t/p/w500/" + account.getAvatar().getTmdb().getAvatar_path())
                            .into(image_nav_header);
                }
            }

            @Override
            public void onFailure(Call<account> call, Throwable t) {

            }
        });
    }

    public void replaceFragment (Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}