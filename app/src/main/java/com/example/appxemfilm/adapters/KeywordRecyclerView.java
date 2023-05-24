package com.example.appxemfilm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxemfilm.R;
import com.example.appxemfilm.model.Keyword;

import java.util.List;

public class KeywordRecyclerView extends RecyclerView.Adapter<KeywordRecyclerView.KeywordHolder>{
    Context context;
    List<Keyword> listKeyword;

    public KeywordRecyclerView(Context context, List<Keyword> listKeyword) {
        this.context = context;
        this.listKeyword = listKeyword;
    }

    @NonNull
    @Override
    public KeywordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.keyword_item, parent, false);
        return new KeywordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeywordHolder holder, int position) {
        Keyword keyword = listKeyword.get(position);
        holder.textView.setText(keyword.getName());
    }

    @Override
    public int getItemCount() {
        if(listKeyword != null){
            return listKeyword.size();
        }
        return 0;
    }

    public class KeywordHolder extends RecyclerView.ViewHolder {
        Button textView;

        public KeywordHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_keyword);
        }
    }
}
