package com.politecnicomalaga.NasdaqOilPrices.Controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.politecnicomalaga.NasdaqOilPrices.Model.Price;
import com.politecnicomalaga.NasdaqOilPrices.Model.PriceList;
import com.politecnicomalaga.NasdaqOilPrices.View.MainActivity;
import com.politecnicomalaga.NasdaqOilPrices.View.SecondActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class GraphController {

    private static GraphController myController;
    private static final int num = 80;

    private GraphController() {

    }

    public static GraphController getSingleton() {
        if (GraphController.myController == null) {
            myController = new GraphController();
        }
        return myController;
    }

    public void getGraphView(MainActivity myActivity) {
        myActivity.startActivity(new Intent(myActivity, SecondActivity.class));
    }

    public LineGraphSeries<DataPoint> setLineGraphSeries() {
        PriceList priceList = new PriceList();
        ArrayList<DataPoint> dataPoints = new ArrayList<>();

        priceList.setPriceList(MainController.getSingleton().getDataFromNasdaq());

        if (priceList.getPriceList().size() == 0) {
            return new LineGraphSeries<>();
        } else {
            for (int i = 0; i < this.num; i++) {
                DataPoint dataPoint = new DataPoint(Double.parseDouble(String.valueOf(i)), Double.parseDouble(priceList.getPriceList().get(i).getPrice()));
                dataPoints.add(dataPoint);
            }
        }
        return new LineGraphSeries<DataPoint>(dataPoints.toArray(new DataPoint[0]));
    }
}
