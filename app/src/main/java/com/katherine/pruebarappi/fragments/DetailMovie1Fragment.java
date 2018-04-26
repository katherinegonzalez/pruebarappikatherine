package com.katherine.pruebarappi.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.katherine.pruebarappi.R;
import com.katherine.pruebarappi.util.Util;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailMovie1Fragment extends Fragment {

    private View v;
    private TextView txtTitleMovie, txtOverview;
    private ImageView imgMovie;


    public DetailMovie1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =inflater.inflate(R.layout.fragment_detail_movie1, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtTitleMovie = v.findViewById(R.id.txt_title);
        txtOverview = v.findViewById(R.id.txt_overview);
        imgMovie = v.findViewById(R.id.img_movie);

        txtTitleMovie.setText(Util.movieDetailResponse.getTitle());
        txtOverview.setText(Util.movieDetailResponse.getOverview());

        if(Util.movieDetailResponse.getBackdropPath() != null){
            if(!Util.movieDetailResponse.getBackdropPath().equals("")){
                Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w500"+ Util.movieDetailResponse.getBackdropPath()).into(imgMovie);
            }
        }


    }
}
