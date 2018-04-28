package com.katherine.pruebarappi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.katherine.pruebarappi.R;
import com.katherine.pruebarappi.util.Util;
import com.squareup.picasso.Picasso;

public class VideoActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private YouTubePlayerView youTubeView;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        txtTitle = findViewById(R.id.txt_title);
        if(Util.movieDetailResponse != null) { //Si hay internet obtengo los datos del objeto que me retorna el endpoint
            txtTitle.setText(Util.movieDetailResponse.getTitle());
        }
        if(Util.movieDetail != null){ //Si no hay internet obtengo los datos del objeto de la lista
            txtTitle.setText(Util.movieDetail.getTitle());
        }
        youTubeView = findViewById(R.id.youtube_view);
        youTubeView.initialize(Util.API_KEY_GOOGLE, this);
    }



    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.loadVideo(Util.VIDEO_KEY); //cargar video automaticamente
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(VideoActivity.this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), youTubeInitializationResult.toString());
            Toast.makeText(VideoActivity.this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Volver a inicializar el video
            getYouTubePlayerProvider().initialize(Util.VIDEO_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Util.pDialog != null){
            Util.pDialog.dismiss();
        }
    }
}
