package com.udacity.popular_movies_stage1.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {
    private List<Movie> results;

    public void setMovielist(List<Movie> results) {
        this.results = results;
    }

    public List<Movie> getMovielist() {
        return results;
    }

    public static class Movie implements Parcelable{
        private long vote_count;
        private long id;
        private boolean video;
        private double vote_average;
        private String title;
        private double popularity;
        private String poster_path;
        private String original_language;
        private String original_title;
        private ArrayList<Integer> genre_ids = null;
        private String backdrop_path;
        private boolean adult;
        private String overview;
        private String release_date;

        public static final Creator<Movie> CREATOR = new Creator<Movie>() {
            @Override
            public Movie createFromParcel(Parcel in) {
                return new Movie(in);
            }

            @Override
            public Movie[] newArray(int size) {
                return new Movie[size];
            }
        };

        public long getVote_count() {
            return vote_count;
        }

        public long getId() {
            return id;
        }

        public boolean isVideo() {
            return video;
        }

        public double getVote_average() {
            return vote_average;
        }

        public String getTitle() {
            return title;
        }

        public double getPopularity() {
            return popularity;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public boolean isAdult() {
            return adult;
        }

        public String getOverview() {
            return overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        @Override
        public int describeContents() {
            return 0;
        }

public Movie( Parcel in){
    vote_count = in.readLong();
    id = in.readLong();
    //boolean video
    video = (Boolean)in.readValue( null );
    vote_average=in.readDouble();
    title=in.readString();
    popularity=in.readDouble();
    poster_path=in.readString();
    original_language=in.readString();
    original_title=in.readString();
    // List<Integer> genre_ids
    genre_ids=(ArrayList<Integer>) in.readSerializable();
    backdrop_path=in.readString();
    //boolean adult
    adult=(Boolean)in.readValue( null );
    overview=in.readString();
    release_date=in.readString();
}

        @Override
        public void writeToParcel(Parcel destParcel, int flags) {
            destParcel.writeLong(vote_count);
            destParcel.writeLong(id);
            destParcel.writeValue(video);
            destParcel.writeDouble(vote_average);
            destParcel.writeString(title);
            destParcel.writeDouble(popularity);
            destParcel.writeString(poster_path);
            destParcel.writeString(original_language);
            destParcel.writeString(original_title);
            destParcel.writeSerializable(genre_ids);
            destParcel.writeString(backdrop_path);
            destParcel.writeValue(adult);
            destParcel.writeString(overview);
            destParcel.writeString(release_date);
        }
    }
}
