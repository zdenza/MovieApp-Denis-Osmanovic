package com.example.movie;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MovieDBAPI {

    @GET("3/search/movie")
    Call<MovieResponse> getMovies(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") String page
    );
}

