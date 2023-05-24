package com.example.appxemfilm.adapters;

import android.content.ContentProvider;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxemfilm.R;
import com.example.appxemfilm.model.FilmVideoUrl;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class VideoRecyclerView extends RecyclerView.Adapter<VideoRecyclerView.VideoHolder>{
    Context context;
    List<FilmVideoUrl> listYoutubeUrl;
    Lifecycle lifecycle;

    public VideoRecyclerView(Context context, List<FilmVideoUrl> listYoutubeUrl, Lifecycle lifecycle) {
        this.context = context;
        this.listYoutubeUrl = listYoutubeUrl;
        this.lifecycle = lifecycle;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_view_item, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
        FilmVideoUrl url = listYoutubeUrl.get(position);
        holder.tieu_de.setText(url.getName());
        lifecycle.addObserver(holder.player);
        holder.player.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(url.getKey(), 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listYoutubeUrl == null)
            return 0;
        return listYoutubeUrl.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        YouTubePlayerView player;
        TextView tieu_de;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            player = itemView.findViewById(R.id.video_player);
            tieu_de = itemView.findViewById(R.id.text_view_tieu_de);
        }


    }
}
