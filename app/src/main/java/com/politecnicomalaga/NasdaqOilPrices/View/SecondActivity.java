package com.politecnicomalaga.NasdaqOilPrices.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.politecnicomalaga.NasdaqOilPrices.Controller.GraphController;
import com.politecnicomalaga.NasdaqOilPrices.Controller.MainController;
import com.politecnicomalaga.NasdaqOilPrices.R;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_view);

        GraphView graphView = findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = GraphController.getSingleton().setLineGraphSeries();

        series.setThickness(5);
        series.setTitle("Precios en los últimos 80 días");
        series.setDrawBackground(true);
        series.setColor(Color.WHITE);
        series.setBackgroundColor(Color.RED);
        series.setDrawDataPoints(true);

        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graphView.getLegendRenderer().setBackgroundColor(Color.GRAY);

        graphView.setTitle("Precio");
        graphView.setTitleColor(Color.BLACK);
        graphView.setTitleTextSize(40);

        graphView.getViewport().setBorderColor(Color.GRAY);

        graphView.removeAllSeries();
        graphView.addSeries(series);
    }
}