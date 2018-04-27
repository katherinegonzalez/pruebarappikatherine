package com.katherine.pruebarappi.util;

import com.google.gson.Gson;
import com.katherine.pruebarappi.model.GeneralResponse;
import com.katherine.pruebarappi.model.Movie;

public class ConvertGson {

    public ConvertGson() {
    }

    public String serializingGson(GeneralResponse generalResponse){
        Gson gson = new Gson();
        String json = gson.toJson(generalResponse);
        System.out.println(json);

        return json.toString();
    }

    public GeneralResponse deserializingGson(String json){

        GeneralResponse generalResponse = new Gson().fromJson(json, GeneralResponse.class);

        return generalResponse;
    }

    public Movie getDetailFromDesearilizingGson(Integer idMovie, String json){
        GeneralResponse generalResponse = deserializingGson(json);
        if(generalResponse != null){
            if(!generalResponse.getResults().isEmpty()){
                for(int i=0; i<generalResponse.getResults().size(); i++){
                  if(generalResponse.getResults().get(i).getId().equals(idMovie)){
                      return generalResponse.getResults().get(i);
                  }
                }
            }
        }
        return null;
    }
}
