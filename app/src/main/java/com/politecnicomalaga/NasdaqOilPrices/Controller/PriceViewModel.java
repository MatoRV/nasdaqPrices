package com.politecnicomalaga.NasdaqOilPrices.Controller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.politecnicomalaga.NasdaqOilPrices.Model.Price;

import java.util.List;

public class PriceViewModel extends ViewModel {

    private MutableLiveData<List<Price>> listaPrecios;

    public LiveData<List<Price>> getPrices() {
        if (listaPrecios == null) {
            listaPrecios = new MutableLiveData<>();
        }
        return listaPrecios;
    }

    public void loadOilPrecios() {
        MainController.getSingleton().requestOilDataFromNasdaq(this);
        listaPrecios.postValue(MainController.getSingleton().getList());
    }

    public void loadGoldPrecios() {
        MainController.getSingleton().requestGoldDataFromNasdaq(this);
        listaPrecios.postValue(MainController.getSingleton().getList());
    }

    public void setData(List<Price> list) {
        listaPrecios.postValue(list);
    }
}
