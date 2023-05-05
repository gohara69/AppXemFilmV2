package com.example.appxemfilm.viewmodels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.appxemfilm.R;
import com.example.appxemfilm.model.CastModel;

public class CastDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_detail);

//        if(getIntent().hasExtra("cast")){
//            CastModel cast = getIntent().getParcelableExtra("cast");
//            Log.v("Tag", cast.getKnown_for_department());
//            Log.v("Tag", cast.getBirthday());
//            Log.v("Tag", cast.getBiography());
//
//        }
    }
}