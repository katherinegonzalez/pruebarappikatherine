package com.katherine.pruebarappi.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.katherine.pruebarappi.R;
import com.katherine.pruebarappi.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailMovie2Fragment extends Fragment {

    private View v;
    private TextView txtTitleMovie, txtOriginalTitle, txtGenre, txtVoteCount, txtVoteAverage, txtPopularity, txtReleaseDate, txtLanguage, txtHomepage;
    private String titleMovie = "", originalTitle = "", genre ="", voteCount = "", voteAverage = "", popularity = "", releaseDate = "", language = "", homepage ="";
    public DetailMovie2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_detail_movie2, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtTitleMovie = v.findViewById(R.id.txt_title);
        txtOriginalTitle = v.findViewById(R.id.txt_original_title);
        txtGenre = v.findViewById(R.id.txt_genre);
        txtVoteCount = v.findViewById(R.id.txt_vote_count);
        txtVoteAverage = v.findViewById(R.id.txt_vote_average);
        txtPopularity = v.findViewById(R.id.txt_popularity);
        txtReleaseDate = v.findViewById(R.id.txt_release_date);
        txtLanguage = v.findViewById(R.id.txt_language);
        txtHomepage = v.findViewById(R.id.txt_homepage);

        setData();

        txtTitleMovie.setText(titleMovie);
        txtOriginalTitle.setText(originalTitle);
        txtGenre.setText(genre);

        txtVoteCount.setText(voteCount);
        txtVoteAverage.setText(voteAverage);
        txtPopularity.setText(popularity);
        txtReleaseDate.setText(releaseDate);
        txtLanguage.setText(language);
        txtHomepage.setText(homepage);

    }

    public void setData(){

        if(Util.movieDetailResponse != null){ //Si hay internet obtengo los datos del objeto que me retorna el endpoint

            titleMovie = Util.movieDetailResponse.getTitle();
            originalTitle = Util.movieDetailResponse.getOriginalTitle();

            String genders = "";
            if(!Util.movieDetailResponse.getGenres().isEmpty()){
                for(int i=0; i<Util.movieDetailResponse.getGenres().size(); i++){
                    if(i == 0){
                        genders = genders + Util.movieDetailResponse.getGenres().get(i).getName();
                    }else{
                        genders = genders +"-"+ Util.movieDetailResponse.getGenres().get(i).getName();
                    }

                }
            }
            genre =genders;
            voteCount = Util.movieDetailResponse.getVoteCount().toString();
            voteAverage = Util.movieDetailResponse.getVoteAverage().toString();
            popularity = Util.movieDetailResponse.getPopularity().toString();
            releaseDate = Util.movieDetailResponse.getReleaseDate();

            String languages = "";

            if(!Util.movieDetailResponse.getSpokenLanguages().isEmpty()){
                for(int i=0; i<Util.movieDetailResponse.getSpokenLanguages().size(); i++){
                    if(i == 0){
                        languages = languages + Util.movieDetailResponse.getSpokenLanguages().get(i).getName();
                    }else{
                        languages = languages +"-"+ Util.movieDetailResponse.getSpokenLanguages().get(i).getName();
                    }

                }
            }

            language = languages;
            homepage =Util.movieDetailResponse.getHomepage();

        }

        if(Util.movieDetail != null){ //Si no hay internet obtengo los datos del objeto de la lista->Algunos datos no existen en ese objeto por lo cual quedan vacíos
            titleMovie = Util.movieDetail.getTitle();
            originalTitle = Util.movieDetail.getOriginalTitle();
            genre ="";
            voteCount = Util.movieDetail.getVoteCount().toString();
            voteAverage = Util.movieDetail.getVoteAverage().toString();
            popularity = Util.movieDetail.getPopularity().toString();
            releaseDate = Util.movieDetail.getReleaseDate();

            language = Util.movieDetail.getOriginalLanguage();
            homepage ="";

        }
    }
}
