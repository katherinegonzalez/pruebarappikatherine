package com.katherine.pruebarappi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.katherine.pruebarappi.R;
import com.katherine.pruebarappi.activity.DetailMoviePagerActivity;
import com.katherine.pruebarappi.model.Movie;
import com.katherine.pruebarappi.model.MovieDetailResponse;
import com.katherine.pruebarappi.model.VideoResponse;
import com.katherine.pruebarappi.res.ApiClient;
import com.katherine.pruebarappi.res.ApiServiceClientInterface;
import com.katherine.pruebarappi.storage.SaveInCache;
import com.katherine.pruebarappi.util.ConvertGson;
import com.katherine.pruebarappi.util.Dialogs;
import com.katherine.pruebarappi.util.NetValidation;
import com.katherine.pruebarappi.util.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by katherinegonzalez on 25/04/18.
 */

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.AdapterMovieViewHolder> implements Filterable{

    private Activity activity;
    private List<Movie> itemsMovies;
    private List<Movie> itemsMoviesFiltered;
    private String filename; //En caso de que no haya conexión a internet
    private SaveInCache saveInCache = new SaveInCache();
    private ConvertGson convertGson = new ConvertGson();
    private Dialogs dialogs = new Dialogs();
    private String language = "es-CO";

    public AdapterMovie(Activity activity, List<Movie> itemsMovies, String filename) {
        this.activity = activity;
        this.itemsMovies = itemsMovies;
        this.itemsMoviesFiltered = itemsMovies;
        this.filename = filename;
    }

    @Override
    public AdapterMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_movie, parent, false);
        return new AdapterMovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterMovieViewHolder holder, int position) {
        final Movie movie = itemsMoviesFiltered.get(position);

        holder.txtTitle.setText(movie.getTitle());
        holder.txtScore.setText(movie.getVoteAverage().toString());

        String language = "";

        if(movie.getPosterPath() != null){
            if(!movie.getPosterPath().equals("")){
                Picasso.with(activity).load("https://image.tmdb.org/t/p/w500"+ movie.getPosterPath()).into(holder.imageMovie);
            }
        }

        if(movie.getOriginalLanguage().equals("en")){
           language = "Inglés";
        }
        if(movie.getOriginalLanguage().equals("es")){
            language = "Español";
        }
        holder.txtLanguage.setText(language);

        holder.layoutMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetValidation netValidation = new NetValidation();
                Integer movieId = movie.getId();
                if(netValidation.isNet(activity)) { //Si hay internet
                    dialogs.definirProgressDialog(activity);
                    Util.pDialog.show();
                    getMovieDetail(movieId);
                }else{

                    if(!saveInCache.getDataInCache(activity, filename).isEmpty()){ //Si no hay internet reviso los datos que hay en cahche
                        Util.movieDetail = new Movie();
                        Util.movieDetail = convertGson.getDetailFromDesearilizingGson( movieId, saveInCache.getDataInCache(activity, filename));
                        if(Util.movieDetail != null){
                            //Si no hay internet no se muestra un video
                            Intent myIntent = new Intent(activity, DetailMoviePagerActivity.class);
                            activity.startActivity(myIntent);
                        }else{
                            Toast.makeText(activity, "En este momento no es posible mostrar el detalle de esta película. Intente más tarde cuando tenga conexión a internet", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(activity, "En este momento no es posible mostrar el detalle de esta película. Intente más tarde cuando tenga conexión a internet", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsMoviesFiltered.size();
    }

    @Override
    public Filter getFilter() { //Para el filtro offline
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemsMoviesFiltered = itemsMovies;
                } else {
                    List<Movie> filteredList = new ArrayList<>();
                    for (Movie row : itemsMovies) {

                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getOriginalTitle().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    itemsMoviesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsMoviesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemsMoviesFiltered = (ArrayList<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class AdapterMovieViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageMovie;
        private LinearLayout layoutMovie;
        private TextView txtTitle;
        private TextView txtScore;
        private TextView txtLanguage;


        public AdapterMovieViewHolder(View itemView) {
            super(itemView);

            imageMovie = itemView.findViewById(R.id.img_movie);
            layoutMovie = itemView.findViewById(R.id.lyt_item);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtScore =  itemView.findViewById(R.id.txt_score);
            txtLanguage = itemView.findViewById(R.id.txt_language);
        }
    }

    public void getMovieDetail(Integer id){

        ApiServiceClientInterface apiService = ApiClient.getClient().create(ApiServiceClientInterface.class);

        Call<MovieDetailResponse> call = apiService.getMovieDetails(id, Util.API_KEY, language);
        call.enqueue(new Callback<MovieDetailResponse>() {

            @Override
            public void onResponse(Call<MovieDetailResponse> call, final Response<MovieDetailResponse> response) {

                if(response.isSuccessful()){

                    if(response.body() != null){
                        Util.movieDetailResponse = new MovieDetailResponse();
                        Util.movieDetailResponse = response.body();
                        getVideo(response.body().getId());
                    }

                }else{
                    if(Util.pDialog != null)
                        Util.pDialog.dismiss();

                    String error = "Ha ocurrido un error al intentar conectar con el servidor! Intente nuevamente";
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("jObjError", jObjError +"");
                        error= jObjError.get("errors").toString();
                        Log.d("ERROR PELICULAS", error);
                    } catch (Exception e) {
                        Log.d("EXCEPCION ERROR", e +"");
                    }

                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
                if(Util.pDialog != null)
                    Util.pDialog.dismiss();
                Toast.makeText(activity, "Ha ocurrido un error al intentar conectar con el servidor! Revise su conexión e intente nuevamente", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getVideo(Long id){

        ApiServiceClientInterface apiService = ApiClient.getClient().create(ApiServiceClientInterface.class);

        Call<VideoResponse> call = apiService.getVideo(id, Util.API_KEY, language);
        call.enqueue(new Callback<VideoResponse>() {

            @Override
            public void onResponse(Call<VideoResponse> call, final Response<VideoResponse> response) {

                if(Util.pDialog != null)
                    Util.pDialog.dismiss();

                if(response.isSuccessful()){

                    if(response.body() != null){
                        if(!response.body().getResults().isEmpty()){
                            Util.VIDEO_KEY = response.body().getResults().get(0).getKey(); //Tomo unicamente el primer video
                        }
                    }

                }else{

                    String error = "Ha ocurrido un error al intentar conectar con el servidor! Intente nuevamente";
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("jObjError", jObjError +"");
                        error= jObjError.get("errors").toString();
                        Log.d("ERROR PELICULAS", error);
                    } catch (Exception e) {
                        Log.d("EXCEPCION ERROR", e +"");
                    }

                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show();

                }

                Intent myIntent = new Intent(activity, DetailMoviePagerActivity.class);
                activity.startActivity(myIntent);


            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                if(Util.pDialog != null)
                    Util.pDialog.dismiss();

                Intent myIntent = new Intent(activity, DetailMoviePagerActivity.class);
                activity.startActivity(myIntent);

                Toast.makeText(activity, "Ha ocurrido un error al intentar obtener el video del trailer! Revise su conexión e intente nuevamente", Toast.LENGTH_LONG).show();
            }
        });
    }

}
