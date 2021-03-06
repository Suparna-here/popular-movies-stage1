package com.udacity.popular_movies_stage1.utils;

import com.udacity.popular_movies_stage1.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MovieService {
    public interface MovieAPI {

        @GET("3/movie/top_rated?")
        Call<MovieResponse> getTopRatedMovieList(
        @Query("api_key") String api_key );


        @GET("3/movie/popular?")
        Call<MovieResponse> getPopularMovieList(
                @Query("api_key") String api_key );
    }
}
