package com.katherine.pruebarappi.activity;

import com.katherine.pruebarappi.model.GeneralResponse;
import com.katherine.pruebarappi.res.ApiClient;
import com.katherine.pruebarappi.res.ApiServiceClientInterface;
import com.katherine.pruebarappi.util.Util;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

public class MainActivityTest {

    String language = "es-CO";
    @Test
    public void getMovies() {

        for(int i=0; i<3; i++){
            ApiServiceClientInterface apiService = ApiClient.getClient().create(ApiServiceClientInterface.class);

            Call<GeneralResponse> call = null;
            switch (i){
                case 0:
                    call = apiService.getPopularMovies(Util.API_KEY, language);
                    break;
                case 1:
                    call = apiService.getTopRatedMovies(Util.API_KEY, language);
                    break;
                case 2:
                    call = apiService.getUpcomingMovies(Util.API_KEY, language);
                    break;
            }

        try {
            Response<GeneralResponse> response = call.execute();
            GeneralResponse generalResponse = response.body();
            assertTrue(response.isSuccessful());

        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }

    @Test
    public void search() {
        String query = "a";
        ApiServiceClientInterface apiService = ApiClient.getClient().create(ApiServiceClientInterface.class);

        Call<GeneralResponse> call = apiService.search(query, Util.API_KEY, language);

        try {
            //Magic is here at .execute() instead of .enqueue()
            Response<GeneralResponse> response = call.execute();
            GeneralResponse generalResponse = response.body();

            assertTrue(response.isSuccessful());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}