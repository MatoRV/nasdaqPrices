package com.politecnicomalaga.NasdaqOilPrices.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PriceList {

    private List<Price> priceList;

    public PriceList() {
        priceList = new ArrayList<>();
    }

    public List<Price> getPriceList() {
        return this.priceList;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    public List<String> getPriceListDays() {
        List<String> priceListDays = new ArrayList<>();

        return priceListDays;
    }

    public double getPrecioMayor() {
        double precioMayor = 0;
        for (int i = 0; i < this.priceList.size(); i++) {
            if (Double.parseDouble(this.priceList.get(i).getPrice()) > precioMayor) {
                precioMayor = Double.parseDouble(this.priceList.get(i).getPrice());
            }
        }
        return precioMayor;
    }

    public boolean validarDatos() {
        if ((this.priceList.size() == 0) || (this.priceList.get(0).getDay().equalsIgnoreCase(String.valueOf(LocalDate.now().minusDays(1))))) {
            return false;
        }
        return true;
    }
}
