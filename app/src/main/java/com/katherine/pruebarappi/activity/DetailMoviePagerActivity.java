package com.katherine.pruebarappi.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.katherine.pruebarappi.list.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.katherine.pruebarappi.R;
import com.katherine.pruebarappi.fragments.DetailMovie1Fragment;
import com.katherine.pruebarappi.fragments.DetailMovie2Fragment;
import com.katherine.pruebarappi.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DetailMoviePagerActivity extends AppCompatActivity {

    private ViewPager pager;
    PagerAdapter adapter;
    private List<Fragment> data;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    private int currentPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie_pager);

        pager = (ViewPager) findViewById(R.id.pager); // es el contenedor de los fragment del activity
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots); // barra de abajo para visualizar cuando se deslizan los fragment del activity
        data =  new ArrayList<>();

        DetailMovie1Fragment detailMovie1Fragment = new DetailMovie1Fragment();
        data.add(detailMovie1Fragment); // agrego a la lista el fragment de la tarjeta del conductor

        DetailMovie2Fragment detailMovie2Fragment =  new DetailMovie2Fragment();
        data.add(detailMovie2Fragment);

        adapter = new PagerAdapter(getSupportFragmentManager(), data); // creo un adaptador de fragment para que me maneje los fragment como un carrusel
        pager.setAdapter(adapter); // mando el adaptador de fragments a el contenedor de la vista
        pager.setCurrentItem(currentPosition);
        addBottomDots(pager.getCurrentItem()); //puntos para determinar slide del fragment

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[data.size()];

        int colorsActive = getResources().getColor(R.color.dot_light_screen);
        int colorsInactive = getResources().getColor(R.color.dot_dark_screen);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextColor(colorsInactive);
            dotsLayout.addView(dots[i]);

        }

        if (dots.length > 0){
            dots[currentPage].setTextColor(colorsActive);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Util.pDialog != null)
            Util.pDialog.dismiss();
    }
}
