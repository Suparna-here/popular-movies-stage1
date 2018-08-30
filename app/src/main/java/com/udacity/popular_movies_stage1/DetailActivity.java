package com.udacity.popular_movies_stage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.popular_movies_stage1.model.MovieResponse;
import com.udacity.popular_movies_stage1.utils.ServiceGenerator;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_DATA = "extra_data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView moviePosterIV = findViewById(R.id.iv_poster_thumbnail);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        ArrayList<MovieResponse.Movie> movieArrayList = intent.getParcelableArrayListExtra(EXTRA_DATA);
        MovieResponse.Movie movie=movieArrayList.get(0);

       if (movieArrayList == null) {
            // EXTRA_DATA not found in intent
            closeOnError();
            return;
        }
        if (movie == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI(movie);
        String posteUrl= ServiceGenerator.POSTER_URL+movie.getPoster_path();
        Picasso.get()
                .load(posteUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(moviePosterIV);
        setTitle(movie.getTitle());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //** Populate UI with Movie details *//
    private void populateUI(MovieResponse.Movie movie) {
        TextView movieReleaseDate_tv = findViewById(R.id.tv_movie_release_date);
        TextView movieVoteAverage_tv = findViewById(R.id.tv_movie_vote_average);
        TextView moviePlotSynopsis_tv = findViewById(R.id.tv_movie_plot_synopsis);

        if (TextUtils.isEmpty(movie.getRelease_date()))
            movieReleaseDate_tv.setText(getString(R.string.detail_release_date_text));
        else movieReleaseDate_tv.setText(movie.getRelease_date());
        if (TextUtils.isEmpty(Double.toString(movie.getVote_average())))
            movieVoteAverage_tv.setText(getString(R.string.detail_vote_average_text));
        else movieVoteAverage_tv.setText(Double.toString(movie.getVote_average()));
        if (TextUtils.isEmpty(movie.getOverview()))
        moviePlotSynopsis_tv.setText(getString(R.string.detail_plot_synopsis_text));
        else moviePlotSynopsis_tv.setText(movie.getOverview());
    }
}
