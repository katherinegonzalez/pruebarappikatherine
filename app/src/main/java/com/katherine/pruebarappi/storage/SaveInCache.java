package com.katherine.pruebarappi.storage;

import android.content.Context;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class SaveInCache {
    private File file;
    private FileOutputStream outputStream;
    private FileInputStream inputStream;

    public SaveInCache() {
    }


    public void saveInCache(Context context, String filename, String jsonMovies){
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

    public String getDataInCache(Context context, String filename){
        String data = "";
        try {
            file = File.createTempFile(filename, null, context.getCacheDir());
            inputStream = new FileInputStream(file);
            inputStream = context.openFileInput(filename);
            data = IOUtils.toString(inputStream, "UTF-8");

            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
