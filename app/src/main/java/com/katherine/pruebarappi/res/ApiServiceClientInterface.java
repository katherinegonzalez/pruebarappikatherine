package com.katherine.pruebarappi.res;

import com.katherine.pruebarappi.model.GeneralResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by katherinegonzalez on 25/04/18.
 */

public interface ApiServiceClientInterface {

    @GET("movie/top_rated")
    Call<GeneralResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<GeneralResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<GeneralResponse> getUpcomingMovies(@Query("api_key") String apiKey);
}
