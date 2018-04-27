package com.katherine.pruebarappi.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.katherine.pruebarappi.R;
import com.katherine.pruebarappi.util.NetValidation;
import com.katherine.pruebarappi.util.Util;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailMovie1Fragment extends Fragment {

    private View v;
    private TextView txtTitleMovie, txtOverview;
    private ImageView imgMovie;
    private String title = "", overview = "", imagepath = "";
    private NetValidation netValidation = new NetValidation();

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

        setData();

        txtTitleMovie.setText(title);
        txtOverview.setText(overview);

        if(!netValidation.isNet(getActivity())){
            Toast.makeText(getContext(), "Para ver la imagen se requiere conexión a internet!", Toast.LENGTH_LONG).show();
        }

    }

    public void setData(){

        if(Util.movieDetailResponse != null){ //Si hay internet obtengo los datos del objeto que me retorna el endpoint
            title = Util.movieDetailResponse.getTitle();
            overview = Util.movieDetailResponse.getOverview();
            imagepath = Util.movieDetailResponse.getBackdropPath();
            if(imagepath != null){
                if(!imagepath.equals("")){
                    Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w500"+ imagepath).into(imgMovie);
                }
            }

        }

        if(Util.movieDetail != null){ //Si no hay internet obtengo los datos del objeto de la lista
            title = Util.movieDetail.getTitle();
            overview = Util.movieDetail.getOverview();
            imgMovie.setImageResource(R.drawable.ic_warning_black_48dp);
        }
    }

}
