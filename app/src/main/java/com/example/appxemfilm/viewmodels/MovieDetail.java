package com.example.appxemfilm.viewmodels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.appxemfilm.adapters.ChuDeRecyclerAdapter;
import com.example.appxemfilm.adapters.MovieViewHolder;
import com.example.appxemfilm.model.ChuDe;
import com.example.appxemfilm.model.MovieModel;

import java.util.ArrayList;

public class MovieDetail extends AppCompatActivity {

    ImageView imageView, back_btn;
    TextView text_view_ten_film, ten_khac_text_view, text_view_the_loai;
    RecyclerView recyclerView;
    int id;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        imageView = findViewById(R.id.movie_image_view);
        text_view_ten_film = findViewById(R.id.text_view_ten_film);
        ten_khac_text_view = findViewById(R.id.ten_khac_text_view);
        text_view_the_loai = findViewById(R.id.text_view_the_loai);
        back_btn = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.recyclerView_chu_de);

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

    private void getDataFromIntent() {
        if(getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            text_view_ten_film.setText(movieModel.getTitle());
            ten_khac_text_view.setText(movieModel.getOriginal_title());
            text_view_the_loai.setText(movieModel.getRelease_date());
            int[] arrChuDe = movieModel.getGenre_ids();
            ArrayList<ChuDe> chuDes = new ArrayList<>();
            for(int i = 0 ; i < arrChuDe.length ; i++){
                String sql = "Select name from CHUDE where id = " + arrChuDe[i];
                Cursor c = database.rawQuery(sql, null);
                c.moveToFirst();
                String name = c.getString(0);
                c.close();
                chuDes.add(new ChuDe(arrChuDe[i], name));
            }

            ChuDeRecyclerAdapter adapter = new ChuDeRecyclerAdapter(this, chuDes);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(horizontalLayoutManagaer);
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + movieModel.getPoster_path())
                    .into(imageView);
        }
    }
}