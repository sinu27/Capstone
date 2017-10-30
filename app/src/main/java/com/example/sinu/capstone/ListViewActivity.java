package com.example.sinu.capstone;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by sinu on 2017-09-20.
 */
public class ListViewActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_list);
        ListView listView = (ListView) findViewById(R.id.listview);
        ArrayList<Listviewitem> data = new ArrayList<>();
        Listviewitem normalChicken = new Listviewitem("순살치킨", "15000");
        Listviewitem hotChicken = new Listviewitem("양념치킨", "16000");
        Listviewitem soyChicken = new Listviewitem("간장치킨", "16000");
        Listviewitem paChicken = new Listviewitem("파닭치킨", "16000");
        data.add(normalChicken);
        data.add(hotChicken);
        data.add(soyChicken);
        data.add(paChicken);

        ListviewAdapter adapter = new ListviewAdapter(this, R.layout.childitem, data);
        listView.setAdapter(adapter);
    }
}
