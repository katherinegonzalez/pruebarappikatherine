package com.katherine.pruebarappi.util;

import com.google.gson.Gson;
import com.katherine.pruebarappi.model.GeneralResponse;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConvertGsonTest {

    @Test
    public void serializingGson() {

        GeneralResponse generalResponse = new GeneralResponse();
        Gson gson = new Gson();
        String json = gson.toJson(generalResponse);
        assertNotNull(json);

    }

    @Test
    public void deserializingGson() {

        String json = " {\"page\":1,\"results\":[{\"adult\":false,\"backdrop_path\":\"/9ywA15OAiwjSTvg3cBs9B7kOCBF.jpg\",\"genre_ids\":[18,10749],\"id\":337167,\"original_language\":\"en\",\"original_title\":\"Fifty Shades Freed\",\"overview\":\"Creyendo que han dejado atrás las sombras del pasado, los recién casados Christian y Anastasia disfrutan de su relación y de su vida llena de lujos. Pero justo cuando Ana empieza a relajarse, aparecen nuevas amenazas que ponen en riesgo su felicidad. Adaptación de la última novela de la saga \\\"50 sombras de Grey\\\".\",\"popularity\":732.998105,\"poster_path\":\"/eLa1TaO78vbqANvfcRHZBnI1F3Q.jpg\",\"release_date\":\"2018-02-07\",\"title\":\"Cincuenta sombras liberadas\",\"video\":false,\"vote_average\":6.0,\"vote_count\":1522}]}";
        GeneralResponse generalResponse = new Gson().fromJson(json, GeneralResponse.class);
        assertTrue(!generalResponse.getResults().isEmpty());

    }

}