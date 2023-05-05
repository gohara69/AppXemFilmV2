package com.example.appxemfilm.viewmodels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appxemfilm.R;
import com.example.appxemfilm.adapters.CastFilmsRecyclerView;
import com.example.appxemfilm.adapters.CastImageRecyclerView;
import com.example.appxemfilm.model.CastImage;
import com.example.appxemfilm.model.CastModel;
import com.example.appxemfilm.model.MovieModel;
import com.example.appxemfilm.request.Servicey;
import com.example.appxemfilm.response.CastImageResponse;
import com.example.appxemfilm.response.FilmByCastResponse;
import com.example.appxemfilm.utils.CastApi;
import com.example.appxemfilm.utils.Credentials;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastDetail extends AppCompatActivity {

    ImageView btn_back, image_cast;
    TextView text_view_header, text_view_sex, text_view_ngay_sinh, text_view_nghe_nghiep;
    TextView text_view_noi_sinh;
    CastModel castModel;
    RecyclerView recycler_view_hinh_anh, recycler_view_phim_da_dong;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_detail);

        anhXa();
        if(getIntent().hasExtra("cast")){
            castModel = getIntent().getParcelableExtra("cast");
            getCastDetail();
        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setDataToView();
    }

    public void anhXa(){
        btn_back = findViewById(R.id.btn_back);
        text_view_header = findViewById(R.id.text_view_header);
        image_cast = findViewById(R.id.image_cast);
        text_view_sex = findViewById(R.id.text_view_sex);
        text_view_ngay_sinh = findViewById(R.id.text_view_ngay_sinh);
        text_view_nghe_nghiep = findViewById(R.id.text_view_nghe_nghiep);
        text_view_noi_sinh = findViewById(R.id.text_view_noi_sinh);
        recycler_view_hinh_anh = findViewById(R.id.recycler_view_hinh_anh);
        recycler_view_phim_da_dong = findViewById(R.id.recycler_view_phim_da_dong);
        context = this;
    }

    public void setDataToView(){
        text_view_header.setText(castModel.getName());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/" + castModel.getProfile_path())
                .into(image_cast);
        text_view_sex.setText(castModel.getGender() == 2 ? "Nam" : "Nữ");
        text_view_nghe_nghiep.setText(castModel.getKnown_for_department());
        setDataToRecyclerViewCastImages();
        setDataToRecyclerViewPhimDaDong();
    }

    public void getCastDetail(){
        Servicey servicey = new Servicey();
        CastApi castApi = servicey.getCastApi();
        Call<CastModel> castModelCall = castApi.getCast(
                castModel.getId(),
                Credentials.API_KEY,
                "vi-VN"
        );
        castModelCall.enqueue(new Callback<CastModel>() {
            @Override
            public void onResponse(Call<CastModel> call, Response<CastModel> response) {
                    CastModel cast = response.body();
                    castModel.setGender(cast.getGender());
                    if(cast.getBirthday() == null){
                        text_view_ngay_sinh.setText("Không tìm thấy dữ liệu");
                    } else {
                        text_view_ngay_sinh.setText(cast.getBirthday());
                    }

                    if(cast.getBirthday() == null){
                        text_view_noi_sinh.setText("Không tìm thấy dữ liệu");
                    } else {
                        text_view_noi_sinh.setText(cast.getPlace_of_birth());
                    }

                    if(cast.getBirthday() == null){
                        castModel.setKnown_for_department("Không tìm thấy dữ liệu");
                    } else {
                        castModel.setKnown_for_department(cast.getKnown_for_department());
                    }
            }

            @Override
            public void onFailure(Call<CastModel> call, Throwable t) {

            }
        });
    }

    public void setDataToRecyclerViewCastImages(){
        Servicey servicey = new Servicey();
        CastApi castApi = servicey.getCastApi();
        Call<CastImageResponse> responseCall = castApi.getCastImages(
                castModel.getId(),
                Credentials.API_KEY
        );
        responseCall.enqueue(new Callback<CastImageResponse>() {
            @Override
            public void onResponse(Call<CastImageResponse> call, Response<CastImageResponse> response) {
                if(response.code() == 200){
                    List<CastImage> listCastImages = response.body().getCastImages();
                    CastImageRecyclerView adapter = new CastImageRecyclerView(context, listCastImages);
                    recycler_view_hinh_anh.setAdapter(adapter);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    recycler_view_hinh_anh.setLayoutManager(horizontalLayoutManagaer);
                }
            }

            @Override
            public void onFailure(Call<CastImageResponse> call, Throwable t) {

            }
        });
    }

    public void setDataToRecyclerViewPhimDaDong(){
        Servicey servicey = new Servicey();
        CastApi castApi = servicey.getCastApi();
        Call<FilmByCastResponse> responseCall = castApi.getFilmsByCast(
                castModel.getId(),
                Credentials.API_KEY,
                "vi-VN"
        );
        responseCall.enqueue(new Callback<FilmByCastResponse>() {
            @Override
            public void onResponse(Call<FilmByCastResponse> call, Response<FilmByCastResponse> response) {
                if(response.code() == 200){
                    List<MovieModel> listMovie = response.body().getMovies();
                    CastFilmsRecyclerView adapter = new CastFilmsRecyclerView(context, listMovie);
                    recycler_view_phim_da_dong.setAdapter(adapter);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    recycler_view_phim_da_dong.setLayoutManager(horizontalLayoutManagaer);
                }
            }

            @Override
            public void onFailure(Call<FilmByCastResponse> call, Throwable t) {

            }
        });
    }
}