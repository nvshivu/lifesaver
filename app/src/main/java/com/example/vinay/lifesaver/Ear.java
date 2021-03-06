package com.example.vinay.lifesaver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import com.example.vinay.lifesaver.Adapter.CustomAdapter;
import com.example.vinay.lifesaver.Model.DataModel;
import com.example.vinay.lifesaver.Model.MyData1;

public class Ear extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private static ArrayList<DataModel> dataModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        //initialize
        recyclerView=findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        dataModels=new ArrayList<DataModel>();
        for(int i = 0; i< MyData1.titleArray.length; i++)
        {
            dataModels.add(new DataModel(
                    MyData1.titleArray[i],
                    MyData1.Ear[i],
                    MyData1.id[i]
            ));
        }
        //attach to the adapter
        adapter=new CustomAdapter(dataModels);
        recyclerView.setAdapter(adapter);
    }
}

