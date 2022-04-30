package com.example.findme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.media.MediaRouter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.text.BreakIterator;
import java.util.ArrayList;

public class Ajout extends AppCompatActivity {

    private static  int PHONE_NUMBER_RC ;
    ItemTouchHelper.SimpleCallback phoneNumberCallback;
    private EditText ednum;
    private EditText edlongitude;
    private EditText edlatitude;
    private Button btnval,btnqte;
    PositionManager manager;
    public static ArrayList<Position> p;
    private GoogleApiClient googleApiClient;
    private BreakIterator phoneET;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);
        ednum=findViewById(R.id.ednum_ajout);
        edlongitude=findViewById(R.id.edlongitude_ajout);
        edlatitude=findViewById(R.id.edlatitude_ajout);
        btnval=findViewById(R.id.btnvalid_ajout);
        btnqte=findViewById(R.id.btnqte_ajout);

        Bundle b=this.getIntent().getExtras();
        if(b!=null){
            String body=b.getString("BODY");
            String numero=b.getString("PHONE");

            String t[]=body.split("#");
            edlongitude.setText(t[1]);
            edlatitude.setText(t[2]);
            ednum.setText(numero);

        }
        else{
            //MAINacitivity
            //ajout de la position locale


            FusedLocationProviderClient mClient=
                    LocationServices.getFusedLocationProviderClient(Ajout.this);
            mClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null){
                        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                        String getSimSerialNumber = manager.getSimSerialNumber();

                        String phoneNumber = manager.getLine1Number();
                        ednum.setText(phoneNumber);
                    edlatitude.setText(location.getLatitude()+"");
                    edlongitude.setText(location.getLongitude()+"");
                    ednum.setText("+216");
                        } // numero local
                }
            });
            LocationRequest request=LocationRequest.create()
                    .setInterval(10)
                    .setSmallestDisplacement(10);
            mClient.requestLocationUpdates(request,new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if(locationResult!=null){
                        edlatitude.setText(locationResult.getLastLocation().getLatitude()+"");
                        edlongitude.setText(locationResult.getLastLocation().getLongitude()+"");
                        //ednum.setText("+216");
                    }
                }
            },null);
        }

        manager=new PositionManager(Ajout.this,"findme.db",1);
        btnqte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ajout.this.finish();
            }
        });
        btnval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  //  manager.ouvrir("findme.db",1);
                    String num=ednum.getText().toString();
                    String longitude=edlongitude.getText().toString();
                    String latitude =edlatitude.getText().toString();
                    long a=manager.inserer(num,longitude,latitude);
                    if(a!=-1){
                        // p=manager.selectionnertout();
                        ednum.setText("");
                        edlatitude.setText("");
                        edlongitude.setText("");
                        //Intent i=new Intent(Ajout.this,Test.class);

                       // startActivity(i);
                        Toast.makeText(Ajout.this, "ajout reussi", Toast.LENGTH_SHORT).show();

                    }
                }

        });
    }

}