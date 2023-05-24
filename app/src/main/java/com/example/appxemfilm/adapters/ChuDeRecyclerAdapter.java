package com.example.appxemfilm.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxemfilm.R;
import com.example.appxemfilm.model.ChuDe;
import com.example.appxemfilm.viewmodels.MovieDetail;
import com.example.appxemfilm.viewmodels.MoviesByCategory;

import java.util.ArrayList;

public class ChuDeRecyclerAdapter extends RecyclerView.Adapter<ChuDeRecyclerAdapter.ChuDeViewHolder> {

    Context context;
    ArrayList<ChuDe> listChuDe;

    public ChuDeRecyclerAdapter(Context context, ArrayList<ChuDe> listChuDe) {
        this.context = context;
        this.listChuDe = listChuDe;
    }

    @NonNull
    @Override
    public ChuDeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chu_de, parent, false);
        return new ChuDeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChuDeViewHolder holder, int position) {
        ChuDe chuDe = listChuDe.get(position);
        if(chuDe == null){
            return;
        }

        String name = chuDe.getName();
        if(name.startsWith("Phim ")){
            name = name.substring(5);
        }
        holder.chu_de_btn.setText("" + name);
    }

    @Override
    public int getItemCount() {
        if(listChuDe != null){
            return listChuDe.size();
        }
        return 0;
    }

    public class ChuDeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Button chu_de_btn;
        public ChuDeViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            chu_de_btn = itemView.findViewById(R.id.chu_de_btn);
            chu_de_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MoviesByCategory.class);
                    intent.putExtra("chude", listChuDe.get(getAdapterPosition()).getId() + "");
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
}
