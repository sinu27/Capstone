package com.example.sinu.capstone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.view.View.OnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    CustomExpandableListViewAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, String> listData;
    //DB
    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_TYPE = "type";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";

    JSONArray menu = null;

    ArrayList al_type = new ArrayList();
    ArrayList al_name = new ArrayList();
    ArrayList al_price = new ArrayList();
    //DB
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //DB
        getData("http://ec2-13-124-225-240.ap-northeast-2.compute.amazonaws.com/qble/menu.php");

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        // preparing list data
        //prepareListData();

        //listAdapter = new CustomExpandableListViewAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        //expListView.setAdapter(listAdapter);
        //////

    }

    /*
    * Preparing the list data
    */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        //listData = new HashMap<String,String>();
        listDataChild = new HashMap<String, List<String>>();


        //listDataHeader.addAll(al_type);
        // Adding child data
        listDataHeader.add("추천메뉴");
        listDataHeader.add("한식");
        listDataHeader.add("일식");
        listDataHeader.add("튀김");
        listDataHeader.add("국물");
        List<String> recom = new ArrayList<String>(),hansik = new ArrayList<String>(),ilsik = new ArrayList<String>(),fries = new ArrayList<String>(),
                stew = new ArrayList<String>();

        for(int i=0;i<al_type.size();i++) {
            if (al_type.get(i).toString().equals("추천메뉴")) {
                recom.add(al_name.get(i).toString()+"|"+al_price.get(i).toString());
            }else if (al_type.get(i).toString().equals("한식")) {
                hansik.add(al_name.get(i).toString()+"|"+al_price.get(i).toString());
            }else if (al_type.get(i).toString().equals("일식")) {
                ilsik.add(al_name.get(i).toString()+"|"+al_price.get(i).toString());
            }else if (al_type.get(i).toString().equals("튀김")) {
                fries.add(al_name.get(i).toString()+"|"+al_price.get(i).toString());
            }else if (al_type.get(i).toString().equals("국물")) {
                stew.add(al_name.get(i).toString()+"|"+al_price.get(i).toString());
            }
        }
        // Adding child data

//        List<String> chicken = new ArrayList<String>();
//        chicken.add("The Conjuring");
//        chicken.add("Despicable Me 2");
//        chicken.add("Turbo");
//        chicken.add("Grown Ups 2");
//        chicken.add("Red 2");
//
//        List<String> hansik = new ArrayList<String>();
//        hansik.add("2 Guns");
//        hansik.add("The Smurfs 2");
//        hansik.add("The Spectacular Now");
//        hansik.add("The Canyons");
//
//        List<String> jungsik = new ArrayList<String>();
//        jungsik.add("2 Guns");
//        jungsik.add("The Smurfs 2");
//        jungsik.add("The Spectacular Now");
//
//
//        List<String> ilsik = new ArrayList<String>();
//        ilsik.add("2 Guns");
//        ilsik.add("The Smurfs 2");
//        ilsik.add("The Spectacular Now");
//        ilsik.add("The Canyons");

        listDataChild.put(listDataHeader.get(0), recom);// Header, Child data
        listDataChild.put(listDataHeader.get(1), hansik);
        listDataChild.put(listDataHeader.get(2), ilsik);
        listDataChild.put(listDataHeader.get(3), fries);
        listDataChild.put(listDataHeader.get(4), stew);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed(); }
    }

    class CustomExpandableListViewAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;

        public CustomExpandableListViewAdapter(Context context, List<String> listDataHeader,
                                               HashMap<String, List<String>> listChildData) {

            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {


            final String Text = (String) getChild(groupPosition, childPosition);
            String[] result =Text.split("\\|");
            final String childText = result[0];
            final String priceText = result[1];

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.childitem, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.childTV);

            txtListChild.setText(childText);

            TextView txtPriceChild = (TextView) convertView
                    .findViewById(R.id.priceTV);

            txtPriceChild.setText(priceText);

            Button button1 = (Button) convertView.findViewById(R.id.button);
            button1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(_context, childText + "  "+priceText, Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.parentitem, null);

            }
            TextView lblListHeader = (TextView) convertView.findViewById(R.id.parentTV);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);





            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    ////////////////////////////////DB//////////////////////////////////
    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            menu = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < menu.length(); i++) {
                JSONObject c = menu.getJSONObject(i);
                String type = c.getString(TAG_TYPE);
                String name = c.getString(TAG_NAME);
                String price = c.getString(TAG_PRICE);
                al_type.add(type);
                al_name.add(name);
                al_price.add(price);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //onclick
        prepareListData();
        listAdapter = new CustomExpandableListViewAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}