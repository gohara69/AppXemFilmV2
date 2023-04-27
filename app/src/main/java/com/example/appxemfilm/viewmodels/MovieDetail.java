package com.example.appxemfilm.viewmodels;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appxemfilm.R;
import com.example.appxemfilm.adapters.MovieViewHolder;
import com.example.appxemfilm.model.MovieModel;

public class MovieDetail extends AppCompatActivity {

    ImageView imageView, movie_detail_image_view;
    TextView text_view_ten_film, ten_khac_text_view, text_view_the_loai, text_view_tong_quan;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        imageView = findViewById(R.id.movie_image_view);
        movie_detail_image_view = findViewById(R.id.film_detail_imageView);
        text_view_ten_film = findViewById(R.id.text_view_ten_film);
        ten_khac_text_view = findViewById(R.id.ten_khac_text_view);
        text_view_the_loai = findViewById(R.id.text_view_the_loai);

        imageView.setBackgroundColor(Color.rgb(211,211,211));
        movie_detail_image_view.setBackgroundColor(Color.rgb(52, 52, 52));
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        if(getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            text_view_ten_film.setText(movieModel.getTitle());
            ten_khac_text_view.setText(movieModel.getOriginal_title());
            text_view_the_loai.setText(movieModel.getRelease_date());
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + movieModel.getPoster_path())
                    .into(imageView);
        }
    }
}