package com.politecnicomalaga.NasdaqOilPrices.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.politecnicomalaga.NasdaqOilPrices.Controller.GraphController;
import com.politecnicomalaga.NasdaqOilPrices.Controller.JornadaAdapter;
import com.politecnicomalaga.NasdaqOilPrices.Controller.MainController;
import com.politecnicomalaga.NasdaqOilPrices.Controller.PriceViewModel;
import com.politecnicomalaga.NasdaqOilPrices.Model.Price;
import com.politecnicomalaga.NasdaqOilPrices.R;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private LinkedList<Price> mList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private JornadaAdapter mAdapter;

    private PriceViewModel priceViewModel;
    private static MainActivity myActiveActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.rv_prices);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new JornadaAdapter(this, mList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        priceViewModel = new ViewModelProvider(this).get(PriceViewModel.class);
        priceViewModel.getPrices().observe(this, prices -> {
            mList.clear();
            mList.addAll(prices);
            mAdapter.notifyDataSetChanged();
        });



        Button generar = (Button) findViewById(R.id.b_getData);
        Button generar2 = (Button) findViewById(R.id.button_other_price);
        Button generarGraph = (Button) findViewById(R.id.buttonOilGraph);
        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Getting data from Nasdaq Servers...", Toast.LENGTH_LONG).show();
                priceViewModel.loadOilPrecios();
            }
        });

        generar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Obteniendo datos de los servidores de Nasdaq...",Toast.LENGTH_LONG).show();
                priceViewModel.loadGoldPrecios();
            }
        });
        generarGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Cargando grafico del Pretróleo",Toast.LENGTH_LONG).show();
                GraphController.getSingleton().getGraphView(MainActivity.this);
            }
        });

        MainActivity.myActiveActivity = this;
        MainController.setActivity(this);
    }

//    public void accessData() {
//        //Get data from servers throgh controller-model classes
//        List<Price> nuevaLista = MainController.getSingleton().getDataFromNasdaq();
//
//        //Put data in adapter
//        mList.clear();
//        for (Price item:nuevaLista) {
//            mList.add(item);
//        }
//        mAdapter.notifyDataSetChanged();
//    }

    public void errorData(String error, String tipo) {
        TextView tv = (TextView) findViewById(R.id.tv_oilDesc);
        if (tipo.equalsIgnoreCase("oil")) {
            tv.setText(error);
        } else if (tipo.equalsIgnoreCase("iron")) {
            tv.setText(error);
        }
    }


}
