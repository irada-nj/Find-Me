package com.example.findme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;

public class Test extends AppCompatActivity {

    private ListView lv;
    ArrayAdapter ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        lv=findViewById(R.id.lv_test);
        ad=new ArrayAdapter(Test.this,android.R.layout.simple_list_item_1,Ajout.p);
        lv.setAdapter(ad);
    }
}