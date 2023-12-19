package com.politecnicomalaga.NasdaqOilPrices.Controller;

import com.politecnicomalaga.NasdaqOilPrices.Model.Price;
import com.politecnicomalaga.NasdaqOilPrices.View.MainActivity;

import java.util.LinkedList;
import java.util.List;

public class MainController {

    //SINGLETON Controller
    private static final String DATA_URL = "https://data.nasdaq.com/api/v3/datatables/QDL/OPEC.json?";
    private static final String GOLD_DATA_URL = "https://data.nasdaq.com/api/v3/datasets/LBMA/GOLD.json?";
    private static MainController mySingleController;

    private List<Price> dataRequested;
    private static MainActivity activeActivity;
    //Comportamiento
    //Constructor
    private MainController() {
         dataRequested = new LinkedList<Price>();
    }

    //Get instance
    public static MainController getSingleton() {
        if (MainController.mySingleController == null) {
            mySingleController = new MainController();
        }
        return mySingleController;
    }

    //To send data to view
    public List<Price> getDataFromNasdaq() {
        return this.dataRequested;
    }

    //Called from the view
    public void requestOilDataFromNasdaq() {
        Peticion p = new Peticion();
        p.requestData(DATA_URL,"oil");
    }

    public void requestGoldDataFromNasdaq() {
        Peticion p = new Peticion();
        p.requestData(GOLD_DATA_URL,"gold");
    }

    public void setGoldDataFromNasdaq(String json) {
        Respuesta answer = new Respuesta(json);
        dataRequested = answer.getGoldData();
        MainController.activeActivity.accessData();
    }

    //Called when onResponse is OK
    public void setDataFromNasdaq(String json) {

        Respuesta answer = new Respuesta(json);
        dataRequested = answer.getOilData();
        //Load data on the list
        MainController.activeActivity.accessData();
    }

    public void setErrorFromNasdaq(String error, String tipo) {

        //Load data on the list
        MainController.activeActivity.errorData(error, tipo);
    }


    public static void setActivity(MainActivity myAct) {
        activeActivity = myAct;
    }

}
