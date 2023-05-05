package com.example.appxemfilm.viewmodels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.appxemfilm.R;
import com.example.appxemfilm.model.MovieModel;

public class FullImageAcitivity extends AppCompatActivity {

    ImageView btn_close, film_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_acitivity);

        btn_close = findViewById(R.id.btn_close);
        film_image = findViewById(R.id.image_view_film);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(getIntent().hasExtra("imageSrc")) {
            String imageSrc = getIntent().getStringExtra("imageSrc");
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + imageSrc)
                    .into(film_image);
        }
    }
}