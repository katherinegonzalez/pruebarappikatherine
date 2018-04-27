package com.katherine.pruebarappi.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.katherine.pruebarappi.R;
import com.katherine.pruebarappi.list.AdapterMovie;
import com.katherine.pruebarappi.model.GeneralResponse;
import com.katherine.pruebarappi.model.Movie;
import com.katherine.pruebarappi.res.ApiClient;
import com.katherine.pruebarappi.res.ApiServiceClientInterface;
import com.katherine.pruebarappi.storage.SaveInCache;
import com.katherine.pruebarappi.util.ConvertGson;
import com.katherine.pruebarappi.util.Dialogs;
import com.katherine.pruebarappi.util.NetValidation;
import com.katherine.pruebarappi.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TextWatcher {


    private Spinner spinnerMovies;
    private RecyclerView movieList;
    private List<Movie> itemsMovie = new ArrayList<>();
    private List<Movie> itemsMoviePopular = new ArrayList<>();
    private List<Movie> itemsMovieTopRated = new ArrayList<>();
    private List<Movie> itemsMovieUpcoming = new ArrayList<>();
    private AdapterMovie adapterMovies;
    private String type = "Popular";
    private TextView searchFilter;
    private NetValidation netValidation = new NetValidation();
    private SaveInCache saveInCache = new SaveInCache();
    ConvertGson convertGson = new ConvertGson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //https://developers.themoviedb.org/3/movies/get-movie-videos

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

        searchFilter = (TextView) findViewById(R.id.search_filter);
        searchFilter.addTextChangedListener(this);
    }

    public void inicializarAdapter(){
        adapterMovies = new AdapterMovie(this, itemsMovie);
        movieList.setAdapter(adapterMovies);

        if(Util.pDialog != null)
            Util.pDialog.dismiss();

    }

    public void saveData(GeneralResponse generalResponse, String filename){
        String jsonMovieList = convertGson.serializingGson(generalResponse);
        saveInCache.saveInCache(this, filename, jsonMovieList);

    }

    public String filename(){
        String filename = "";
        switch (type){
            case "Popular":
                filename = "moviefilepopular";
                break;
            case "Top Rated":
                filename = "moviefiletoprated";
                break;
            case "Upcoming":
                filename = "moviefileupcoming";
                break;
        }
        return filename;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        type = adapterView.getItemAtPosition(i).toString();

        if(netValidation.isNet(MainActivity.this)){ //Si hay internet
            new Movies().execute();
        }else{
            if(!saveInCache.getDataInCache(this, filename()).isEmpty()){ //Si no hay internet reviso los datos que hay en cahche
                GeneralResponse generalResponse = convertGson.deserializingGson(saveInCache.getDataInCache(this, filename()));
                itemsMovie = generalResponse.getResults();
                inicializarAdapter();
            }else{
                Toast.makeText(MainActivity.this, "En este momento no hay películas para mostrar en esta categoría. Intente más tarde cuando tenga conexión a internet", Toast.LENGTH_LONG).show();
                itemsMovie = new ArrayList<>();
                inicializarAdapter();

            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getMovies(String type){

        ApiServiceClientInterface apiService = ApiClient.getClient().create(ApiServiceClientInterface.class);

        Call<GeneralResponse> call = null;
        switch (type){
            case "Popular":
                call = apiService.getPopularMovies(Util.API_KEY);
                break;
            case "Top Rated":
                call = apiService.getTopRatedMovies(Util.API_KEY);
                break;
            case "Upcoming":
                call = apiService.getUpcomingMovies(Util.API_KEY);
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

                        saveData(response.body(), filename()); //Save data in chaché

                        itemsMovie = response.body().getResults();
                        inicializarAdapter();

                    }else{
                        if(Util.pDialog != null)
                            Util.pDialog.dismiss();
                        Toast.makeText(MainActivity.this, "En este momento no hay películas para mostrar. Intente mas tarde", Toast.LENGTH_LONG).show();
                    }

                }else{
                    if(Util.pDialog != null)
                        Util.pDialog.dismiss();

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
                if(Util.pDialog != null)
                    Util.pDialog.dismiss();
                Toast.makeText(MainActivity.this, "Ha ocurrido un error al intentar conectar con el servidor! Revise su conexión e intente nuevamente", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //adapterMovies.getFilter().filter(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        adapterMovies.getFilter().filter(editable.toString());
        // refreshing recycler view
        adapterMovies.notifyDataSetChanged();
        movieList.setAdapter(adapterMovies);

    }

    public class Movies extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Dialogs.definirProgressDialog(MainActivity.this);
            Util.pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            getMovies(type);

            return null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Util.pDialog != null)
            Util.pDialog.dismiss();
    }

}
