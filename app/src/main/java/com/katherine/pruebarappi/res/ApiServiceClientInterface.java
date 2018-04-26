package com.katherine.pruebarappi.res;

import com.katherine.pruebarappi.model.GeneralResponse;
import com.katherine.pruebarappi.model.MovieDetailResponse;
import com.katherine.pruebarappi.model.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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

    @GET("movie/{id}")
    Call<MovieDetailResponse> getMovieDetails(@Path("id") Long id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getVideo(@Path("movie_id") Long id, @Query("api_key") String apiKey);
}
