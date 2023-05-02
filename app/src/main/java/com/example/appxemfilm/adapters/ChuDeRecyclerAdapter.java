package com.example.appxemfilm.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxemfilm.R;
import com.example.appxemfilm.model.ChuDe;

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
            chu_de_btn = itemView.findViewById(R.id.chu_de_btn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.e("Tag: ", listChuDe.get(getAdapterPosition()).getId() + "");
        }
    }
}
