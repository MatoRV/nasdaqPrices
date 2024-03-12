package com.politecnicomalaga.NasdaqOilPrices.Controller;

import com.politecnicomalaga.NasdaqOilPrices.Model.Price;
import com.politecnicomalaga.NasdaqOilPrices.View.MainActivity;

import java.nio.channels.Pipe;
import java.util.LinkedList;
import java.util.List;

public class MainController {

    //SINGLETON Controller
    private static final String DATA_URL = "https://data.nasdaq.com/api/v3/datatables/QDL/OPEC.json?";
    private static final String GOLD_DATA_URL = "https://data.nasdaq.com/api/v3/datasets/LBMA/GOLD.json?";
    private static MainController mySingleController;

    private PriceViewModel priceViewModel;

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
    public void requestOilDataFromNasdaq(PriceViewModel priceViewModel) {
        Peticion p = new Peticion();
        p.requestData(DATA_URL,"oil");
        this.priceViewModel = priceViewModel;
    }

    public void requestGoldDataFromNasdaq(PriceViewModel priceViewModel) {
        Peticion p = new Peticion();
        p.requestData(GOLD_DATA_URL,"gold");
        this.priceViewModel = priceViewModel;
    }

    public void setGoldDataFromNasdaq(String json) {
        Respuesta answer = new Respuesta(json);
        dataRequested = answer.getGoldData();
       // MainController.activeActivity.accessData();
        if (priceViewModel != null) priceViewModel.setData(dataRequested);
    }

    //Called when onResponse is OK
    public void setDataFromNasdaq(String json) {

        Respuesta answer = new Respuesta(json);
        dataRequested = answer.getOilData();
        //Load data on the list
        if (priceViewModel != null) priceViewModel.setData(dataRequested);
    }

    public void setErrorFromNasdaq(String error, String tipo) {

        //Load data on the list
        MainController.activeActivity.errorData(error, tipo);
    }


    public static void setActivity(MainActivity myAct) {
        activeActivity = myAct;
    }

    public List<Price> getList() {
        return this.dataRequested;
    }

}
