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

    @GET("movie/popular")
    Call<GeneralResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/top_rated")
    Call<GeneralResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/upcoming")
    Call<GeneralResponse> getUpcomingMovies(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/{id}")
    Call<MovieDetailResponse> getMovieDetails(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getVideo(@Path("movie_id") Long id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("search/movie")
    Call<GeneralResponse> search(@Query("query") String query, @Query("api_key") String apiKey, @Query("language") String language);

}
