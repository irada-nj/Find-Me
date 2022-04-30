package com.example.findme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class MyPositionAdapter extends BaseAdapter {
    Context con;
    ArrayList<Position> data;

    public MyPositionAdapter(Context con, ArrayList<Position> data) {
        this.con = con;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //create view
        LayoutInflater inf=LayoutInflater.from(con);
        CardView element= (CardView) inf.inflate(R.layout.view_position,null);
        //recuperation des cpts
        TextView tv_id=element.findViewById(R.id.tv_id_view);
        TextView tv_num=element.findViewById(R.id.tv_num_view);
        TextView tv_lat=element.findViewById(R.id.tv_lat_view);
        TextView tv_lon=element.findViewById(R.id.tv_lon_view);
        Button btncall=element.findViewById(R.id.btn_call_view);
        Button btnmap=element.findViewById(R.id.btn_map_view);
        Button btnsms=element.findViewById(R.id.btn_sms_view);
        //affectation des valeurs
        Position p=data.get(position);
        tv_id.setText(""+p.id);
        tv_num.setText(""+p.num);
        tv_lat.setText(""+p.latitude);
        tv_lon.setText(""+p.longitude);
        return element;
    }
}
