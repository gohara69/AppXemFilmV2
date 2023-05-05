package com.example.appxemfilm.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieModel implements Parcelable {
    //model class for our movies
    private String title;
    private String poster_path;
    private String release_date;
    @SerializedName("id")
    private int id;
    private float vote_average;
    @SerializedName("overview")
    private String movie_overview;
    private String original_language;
    private String original_title;
    private int[] genre_ids;
    @SerializedName("budget")
    private long budget;
    @SerializedName("revenue")
    private long revenue;
    @SerializedName("runtime")
    private int runtime;
    @SerializedName("status")
    private String status;
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @SerializedName("belongs_to_collection")
    private belongs_to_collection belongs_to_collection;

    public com.example.appxemfilm.model.belongs_to_collection getBelongs_to_collection() {
        return belongs_to_collection;
    }

    public void setBelongs_to_collection(com.example.appxemfilm.model.belongs_to_collection belongs_to_collection) {
        this.belongs_to_collection = belongs_to_collection;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    //Constructor
    public MovieModel(){

    }

    public MovieModel(String title, String poster_path, String release_date, int id, float vote_average, String movie_overview, String original_language, String original_title, int[] genre_ids, int budget, int revenue, int runtime, String status, String backdrop_path) {
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.id = id;
        this.vote_average = vote_average;
        this.movie_overview = movie_overview;
        this.original_language = original_language;
        this.original_title = original_title;
        this.genre_ids = genre_ids;
        this.budget = budget;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.backdrop_path = backdrop_path;
    }

    // Getters
    protected MovieModel(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        id = in.readInt();
        vote_average = in.readFloat();
        movie_overview = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        genre_ids = in.createIntArray();
        budget = in.readLong();
        revenue = in.readLong();
        runtime = in.readInt();
        status = in.readString();
        backdrop_path = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setMovie_id(int movie_id) {
        this.id = movie_id;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public void setMovie_overview(String movie_overview) {
        this.movie_overview = movie_overview;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getMovie_id() {
        return id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getMovie_overview() {
        return movie_overview;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeInt(id);
        dest.writeFloat(vote_average);
        dest.writeString(movie_overview);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeIntArray(genre_ids);
        dest.writeLong(budget);
        dest.writeLong(revenue);
        dest.writeInt(runtime);
        dest.writeString(status);
        dest.writeString(backdrop_path);
    }
}
