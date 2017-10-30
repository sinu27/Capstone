package com.example.sinu.capstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sinu on 2017-09-20.
 */
public class ListviewAdapter extends BaseAdapter {
    private static ArrayList<String[]> orderedList;
    private LayoutInflater inflater;
    private ArrayList<Listviewitem> data;

    private int layout;
    Listviewitem listviewitem;


    public ListviewAdapter(Context context, int layout, ArrayList<Listviewitem> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
    }
    @Override
    public int getCount(){return data.size();}
    @Override
    public String getItem(int position){return data.get(position).getName();}
    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Context context = parent.getContext();
        final int p = position;
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        listviewitem=data.get(position);

        TextView name=(TextView)convertView.findViewById(R.id.textView2);
        name.setText(listviewitem.getName());
        //여기서 price를 DB에서 가져와야됨
        TextView price=(TextView)convertView.findViewById(R.id.textView3);
        price.setText(listviewitem.getPrice());

        Button button =(Button)convertView.findViewById(R.id.button);
        orderedList = new ArrayList<>();
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderedList.add(new String[]{(data.get(p).getName()),(data.get(p).getPrice())});
                Toast.makeText(context,data.get(p).getName()+"  이 추가 되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
