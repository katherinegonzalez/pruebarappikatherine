package com.katherine.pruebarappi.list;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.katherine.pruebarappi.R;
import com.katherine.pruebarappi.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by katherinegonzalez on 25/04/18.
 */

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.AdapterMovieViewHolder> {

    protected Activity activity;
    protected List<Movie> itemsMovies;

    public AdapterMovie(Activity activity, List<Movie> itemsMovies) {
        this.activity = activity;
        this.itemsMovies = itemsMovies;
    }

    @Override
    public AdapterMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_movie, parent, false);
        return new AdapterMovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterMovieViewHolder holder, int position) {
        Movie movie = itemsMovies.get(position);


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
                //TODO: Show detail movie
                System.out.println("CLICK EN DETALLE");
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsMovies.size();
    }

    public class AdapterMovieViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageMovie;
        private LinearLayout layoutMovie;
        private TextView txtTitle;
        private TextView txtScore;
        private TextView txtLanguage;


        public AdapterMovieViewHolder(View itemView) {
            super(itemView);

            imageMovie = (ImageView)itemView.findViewById(R.id.img_movie);
            layoutMovie = (LinearLayout)itemView.findViewById(R.id.lyt_item);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            txtScore = (TextView) itemView.findViewById(R.id.txt_score);
            txtLanguage = (TextView) itemView.findViewById(R.id.txt_language);
        }
    }
}
