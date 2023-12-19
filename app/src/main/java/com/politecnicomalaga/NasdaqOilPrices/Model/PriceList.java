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
}
