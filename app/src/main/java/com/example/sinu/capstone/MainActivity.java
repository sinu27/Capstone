package com.example.sinu.capstone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CustomExpandableListViewAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        // preparing list data
        prepareListData();

        listAdapter = new CustomExpandableListViewAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

    }
    /*
    * Preparing the list data
    */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("추천메뉴");
        listDataHeader.add("치킨");
        listDataHeader.add("한식");
        listDataHeader.add("중식");
        listDataHeader.add("일식");

        // Adding child data
        List<String> recom = new ArrayList<String>();
        recom.add("김치찌개");
        recom.add("양념치킨");
        recom.add("스페셜1");
        recom.add("닭도리탕");


        List<String> chicken = new ArrayList<String>();
        chicken.add("The Conjuring");
        chicken.add("Despicable Me 2");
        chicken.add("Turbo");
        chicken.add("Grown Ups 2");
        chicken.add("Red 2");

        List<String> hansik = new ArrayList<String>();
        hansik.add("2 Guns");
        hansik.add("The Smurfs 2");
        hansik.add("The Spectacular Now");
        hansik.add("The Canyons");

        List<String> jungsik = new ArrayList<String>();
        jungsik.add("2 Guns");
        jungsik.add("The Smurfs 2");
        jungsik.add("The Spectacular Now");


        List<String> ilsik = new ArrayList<String>();
        ilsik.add("2 Guns");
        ilsik.add("The Smurfs 2");
        ilsik.add("The Spectacular Now");
        ilsik.add("The Canyons");

        listDataChild.put(listDataHeader.get(0), recom); // Header, Child data
        listDataChild.put(listDataHeader.get(1), chicken);
        listDataChild.put(listDataHeader.get(2), hansik);
        listDataChild.put(listDataHeader.get(3), jungsik);
        listDataChild.put(listDataHeader.get(4), ilsik);
    }

    @Override public void onBackPressed() {
        //super.onBackPressed(); }
    }
}

