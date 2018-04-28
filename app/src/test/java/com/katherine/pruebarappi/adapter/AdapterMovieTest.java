package com.katherine.pruebarappi.adapter;

import com.katherine.pruebarappi.model.GeneralResponse;
import com.katherine.pruebarappi.model.MovieDetailResponse;
import com.katherine.pruebarappi.model.VideoResponse;
import com.katherine.pruebarappi.res.ApiClient;
import com.katherine.pruebarappi.res.ApiServiceClientInterface;
import com.katherine.pruebarappi.util.Util;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;

public class AdapterMovieTest {

    String language = "es-CO";

    @Test
    public void getMovieDetail() {

        Integer id = 337167;
        ApiServiceClientInterface apiService = ApiClient.getClient().create(ApiServiceClientInterface.class);

        Call<MovieDetailResponse> call = apiService.getMovieDetails(id, Util.API_KEY, language);

        try {
            //Magic is here at .execute() instead of .enqueue()
            Response<MovieDetailResponse> response = call.execute();
            MovieDetailResponse movieDetailResponse =response.body();

            assertTrue(response.isSuccessful() && movieDetailResponse != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getVideo() {
        Long id = 337167L;
        ApiServiceClientInterface apiService = ApiClient.getClient().create(ApiServiceClientInterface.class);
        Call<VideoResponse> call = apiService.getVideo(id, Util.API_KEY, language);

        try {
            //Magic is here at .execute() instead of .enqueue()
            Response<VideoResponse> response = call.execute();
            assertTrue(response.isSuccessful());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}