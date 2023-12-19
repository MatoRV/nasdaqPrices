package com.politecnicomalaga.NasdaqOilPrices.Controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.politecnicomalaga.NasdaqOilPrices.Model.Price;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase respuesta. Encapsulará los datos que nos devuelve la API REST
 * opendata de Nasdaq.
 *
 * El controlador le dará el texto a "analizar" en JSON y proporcionará
 * una serialización de los datos "amigable" para la vista. Es en
 * realidad un procesador de textos (parser)
 */

public class Respuesta {
    //ESTADO
    protected String datos;


    //COMPORTAMIENTO
    public Respuesta(String entrada) {
        datos = entrada;
    }


    public List<Price> getOilData() {

        LinkedList<Price> dataList = new LinkedList<>();
        //Parsing the JSON

        //TODO: parse JSON and add data to the list.
        JsonElement jsonElement = JsonParser.parseString(this.datos);

        JsonObject jso = jsonElement.getAsJsonObject().get("datatable").getAsJsonObject();
        JsonArray jsonLista = jso.get("data").getAsJsonArray();

        for(int i = 0;i<jsonLista.size();i++) {
            dataList.add(new Price(jsonLista.get(i).getAsJsonArray().get(0).getAsJsonPrimitive().getAsString(),jsonLista.get(i).getAsJsonArray().get(1).getAsJsonPrimitive().getAsString()));
        }

        return dataList;
    }

    public List<Price> getGoldData() {
        List<Price> dataList = new LinkedList<>();

        JsonElement jsonElement = JsonParser.parseString(this.datos);
        JsonObject json = jsonElement.getAsJsonObject().get("dataset").getAsJsonObject();
        JsonArray jsonLista = json.get("data").getAsJsonArray();

        double[] precio_oro = new double[7];
        double precio_oro_media;
        int dividendo_precio_oro;
        DecimalFormat decimalFormat = new DecimalFormat("#.##"); //hace que el double sólo tenga dos decimales

        for (int i = 0; i < jsonLista.size(); i++) {
            dividendo_precio_oro = 7;
            for (int j = 0; j < jsonLista.get(i).getAsJsonArray().size(); j++) {
                if (jsonLista.get(i).getAsJsonArray().get(j) != null && jsonLista.get(i).getAsJsonArray().get(j).isJsonPrimitive() && jsonLista.get(i).getAsJsonArray().get(j).getAsJsonPrimitive().isNumber()) {
                    precio_oro[j] = Double.parseDouble(jsonLista.get(i).getAsJsonArray().get(j).getAsJsonPrimitive().getAsString());
                } else {
                    dividendo_precio_oro--;
                }
            }
            precio_oro_media = (precio_oro[0] + precio_oro[1] + precio_oro[2] + precio_oro[3] + precio_oro[4] + precio_oro[5] + precio_oro[6]) / dividendo_precio_oro;
            dataList.add(new Price(jsonLista.get(i).getAsJsonArray().get(0).getAsJsonPrimitive().getAsString(), decimalFormat.format(precio_oro_media)));
        }
        return dataList;
    }
}
