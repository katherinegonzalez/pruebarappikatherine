package com.katherine.pruebarappi.util;

import com.google.gson.Gson;
import com.katherine.pruebarappi.model.GeneralResponse;

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
}
