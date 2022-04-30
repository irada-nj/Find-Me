package com.example.findme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MyRecyclerPositionAdapter
        extends RecyclerView.Adapter<MyRecyclerPositionAdapter.MyViewHolder> {

    Context con;
    ArrayList<Position> data;

    public MyRecyclerPositionAdapter(Context con, ArrayList<Position> data) {
        this.con = con;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create view
        LayoutInflater inf=LayoutInflater.from(con);
        CardView element= (CardView) inf.inflate(R.layout.view_position,null);
        return new MyViewHolder(element);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //affectation des valeurs
        Position p=data.get(position);
        holder.tv_id.setText(""+p.id);
        holder.tv_num.setText(""+p.num);
        holder.tv_lat.setText(""+p.latitude);
        holder.tv_lon.setText(""+p.longitude);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id,tv_num,tv_lat,tv_lon;
        Button btncall,btnmap,btnsms;
        public MyViewHolder(@NonNull View element) {
            super(element);
            //recuperation des cpts
             tv_id=element.findViewById(R.id.tv_id_view);
             tv_num=element.findViewById(R.id.tv_num_view);
             tv_lat=element.findViewById(R.id.tv_lat_view);
             tv_lon=element.findViewById(R.id.tv_lon_view);
             btncall=element.findViewById(R.id.btn_call_view);
             btnmap=element.findViewById(R.id.btn_map_view);
             btnsms=element.findViewById(R.id.btn_sms_view);

             btncall.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     int indice=MyViewHolder.this.getAdapterPosition();//renvoie  indice de l'elt selectionné
                     Toast.makeText(con, "appel de : "+data.get(indice).num, Toast.LENGTH_SHORT).show();
                   // a faire: lancer  appel telephonique
                     //met1: y7ot num tel fi interfce w enta tenzl 3el botn akhr call bch tkelm
                     //met2 ghirma tenzl yotlblek el num
                     Intent callIntent = new Intent(Intent.ACTION_CALL);
                     callIntent.setData(Uri.parse("tel:"+data.get(indice).num));
                     con.startActivity(callIntent);
                 }
             });
             btnmap.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     int indice=MyViewHolder.this.getAdapterPosition();
                     Position p=data.get(indice);
                     Intent i=new Intent(con,MapsActivity.class);
                     i.putExtra("NUMERO",p.num);
                     i.putExtra("LONGITUDE",p.longitude);
                     i.putExtra("LATITUDE",p.latitude);
                     con.startActivity(i);
                 }
             });
             btnsms.setOnClickListener(new View.OnClickListener() {
                 @SuppressLint("MissingPermission")
                 @Override
                 public void onClick(View v) {
                     int indice=MyViewHolder.this.getAdapterPosition();
                   
                     Position p=data.get(indice);
                     FusedLocationProviderClient mClient=
                             LocationServices.getFusedLocationProviderClient(con);
                     mClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                         @Override
                         public void onSuccess(Location location) {
                             if(location!=null) {
                                 SmsManager manager=SmsManager.getDefault(); //sim1

                                 manager.sendTextMessage(p.num,//puce num destinataire elli bch teb3ethla msg
                                         null,"findme#"+ location.getLatitude() +"#"+  location.getLongitude(),//position num local findme#log#lat
                                         null,null);
                             }
                         }
                     });

                 }// afaire
                 // n7ot pos local *
                 //fi ajout lazem njem nrecuperi num te3 puce anahi el puce eli yekhdem 3leha
                 
             });
        }
    }
}


