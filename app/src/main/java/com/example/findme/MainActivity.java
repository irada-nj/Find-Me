package com.example.findme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
boolean permession_sms=false;
RecyclerView rv;
    private boolean permession_gps=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /****traitement des permissions ***/
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
            permession_sms=true;
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_SMS,Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE},1);
        }
        /****traitement des permissions ***/
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            permession_gps=true;
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
                    },2);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(MainActivity.this,Ajout.class));
            }
        });
        rv=findViewById(R.id.rv);
       /* PositionManager pm=new PositionManager(this,"findme.db",1);
        ArrayList<Position> data=pm.selectionnertout();
        //MyPositionAdapter ad=new MyPositionAdapter(this,data);
        MyRecyclerPositionAdapter ad=new MyRecyclerPositionAdapter(this,data);//lazem ne3tih layoutManager
        GridLayoutManager manager=new GridLayoutManager(this,1); // layout naplioha 3el recclerView
        rv.setLayoutManager(manager);
        rv.setAdapter(ad);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        PositionManager pm=new PositionManager(this,"findme.db",1);
        ArrayList<Position> data=pm.selectionnertout();
        //MyPositionAdapter ad=new MyPositionAdapter(this,data);
        MyRecyclerPositionAdapter ad=new MyRecyclerPositionAdapter(this,data);//lazem ne3tih layoutManager
        GridLayoutManager manager=new GridLayoutManager(this,1);
        rv.setLayoutManager(manager);
        rv.setAdapter(ad);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1 || requestCode==2){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                permession_sms=true;
                permession_gps=true;
            }
            else{
                this.finish();
            }
        }
    }
}