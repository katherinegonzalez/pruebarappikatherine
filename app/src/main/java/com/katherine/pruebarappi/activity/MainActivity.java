package com.katherine.pruebarappi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.katherine.pruebarappi.R;
import com.katherine.pruebarappi.list.AdapterMovie;
import com.katherine.pruebarappi.list.ItemMovie;
import com.katherine.pruebarappi.model.GeneralResponse;
import com.katherine.pruebarappi.model.Movie;
import com.katherine.pruebarappi.res.ApiClient;
import com.katherine.pruebarappi.res.ApiServiceClientInterface;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String API_KEY = "f51ce15a6a2b1e875adfd9b4ba6ada24";
    private Spinner spinnerMovies;
    private RecyclerView movieList;
    private List<Movie> itemsMovie = new ArrayList<>();;
    private AdapterMovie adapterMovies;
    private String type = "Popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = (RecyclerView) findViewById(R.id.list_movies);
        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        movieList.setLayoutManager(lim);

        spinnerMovies = (Spinner) findViewById(R.id.spinner_movies);

        List<String> typesOfMoviesList = new ArrayList<>();
        typesOfMoviesList.add("Popular");
        typesOfMoviesList.add("Top Rated");
        typesOfMoviesList.add("Upcoming");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typesOfMoviesList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMovies.setAdapter(adapter);
        spinnerMovies.setOnItemSelectedListener(this);
    }

    public void inicializarAdapter(){
        adapterMovies = new AdapterMovie(this, itemsMovie);
        movieList.setAdapter(adapterMovies);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        type = adapterView.getItemAtPosition(i).toString();

        getMovies(type);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getMovies(String type){

        ApiServiceClientInterface apiService = ApiClient.getClient().create(ApiServiceClientInterface.class);

        Call<GeneralResponse> call = null;
        switch (type){
            case "Popular":
                call = apiService.getPopularMovies(API_KEY);
                break;
            case "Top Rated":
                call = apiService.getTopRatedMovies(API_KEY);
                break;
            case "Upcoming":
                call = apiService.getUpcomingMovies(API_KEY);
                break;
        }

        call.enqueue(new Callback<GeneralResponse>() {

            @Override
            public void onResponse(Call<GeneralResponse> call, final Response<GeneralResponse> response) {
                System.out.println("ENTRA A ON RESPONSE PELICULAS: " + response.body());
                System.out.println("ENTRA A ON RESPONSE RAW PELICULAS: " + response.raw());
                System.out.println("ENTRA A ON RESPONSE CODE PELICULAS: " + response.code());
                System.out.println("ENTRA A ON RESPONSE HEADERS PELICULAS: " + response.headers());

                if(response.isSuccessful()){
                    if(!response.body().getResults().isEmpty()){
                        itemsMovie = response.body().getResults();
                        inicializarAdapter();

                    }else{
                        Toast.makeText(MainActivity.this, "En este momento no hay películas para mostrar. Intente mas tarde", Toast.LENGTH_LONG).show();
                    }

                }else{

                    String error = "Ha ocurrido un error al intentar conectar con el servidor! Intente nuevamente";
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("jObjError", jObjError +"");
                        error= jObjError.get("error").toString();
                        Log.d("ERROR PELICULAS", error);
                    } catch (Exception e) {
                        Log.d("EXCEPCION ERROR", e +"");
                    }

                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();


                }


            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ha ocurrido un error al intentar conectar con el servidor! Revise su conexión e intente nuevamente", Toast.LENGTH_LONG).show();
            }
        });
    }
}
