package com.katherine.pruebarappi.storage;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveInCache {
    private File file;
    private FileOutputStream outputStream;
    private String filename = "moviesfile";

    public SaveInCache() {
    }

    public void saveInCache(Context context, String jsonMovies){
        try {
            file = File.createTempFile(filename, null, context.getCacheDir());
            outputStream = new FileOutputStream(file);
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonMovies.getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getDataInCache(){

    }

}
