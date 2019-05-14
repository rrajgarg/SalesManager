//This page shows the profile of a particular salesman
package com.example.android.salesmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyProfile extends AppCompatActivity {
    List<CommodityClass> MyCommodities;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    TextView Uusername,emailid,myid,name;
    List<String> TimeList;
    Context mctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        mctx = getApplicationContext();
        MyCommodities = new ArrayList<>();
        TimeList = new ArrayList<>();
        Uusername = (TextView) findViewById(R.id.slaesmanUserName);
        name = (TextView) findViewById(R.id.slaesmanName);
        myid = (TextView) findViewById(R.id.salesmanID);
        emailid = (TextView) findViewById(R.id.Salesmanemail);
        recyclerView = (RecyclerView) findViewById(R.id.SoldByMe);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Bundle bundle = getIntent().getExtras();
        String MyEmail = bundle.getString("email");
        final String myEmail = MyEmail;
        final String username = MyEmail.substring(0,MyEmail.length()-10);
        final Profile[] profile = new Profile[1];
        // getting profile information from database
        final GraphView graphView = (GraphView)findViewById(R.id.histogram_view);
        databaseReference.child("root").child("profile").child(username).child("profileDetails").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        profile[0] = dataSnapshot.getValue(Profile.class);
                        Uusername.setText(username);
                        emailid.setText(myEmail);
                        name.setText(profile[0].getName().toString());
                        myid.setText(profile[0].getId().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
        databaseReference.child("root").child("profile").child(username).child("SoldCommodities").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        MyCommodities.clear();
                        for(DataSnapshot xyz: dataSnapshot.getChildren())
                        {
                            CommodityClass commodityClass = xyz.getValue(CommodityClass.class);
                            TimeList.addAll(commodityClass.getTime());
                            MyCommodities.add(commodityClass);
                        }
                        int graphArray[] = new int[100];
                        for(int i = 0; i < graphArray.length; ++i) {
                            graphArray[i] = 0;
                        }
                        Log.i("Size is",Integer.toString(TimeList.size()));
                        for(String time: TimeList)
                        {
                            int year=Integer.parseInt(time.substring(0,4));
                            int month=Integer.parseInt(time.substring(4,6));
                            int tot=12*(year-2019)+month;
                            graphArray[tot]++;
                        }
                        graphView.setGraphArray(graphArray);
                        //   Toast.makeText(getApplicationContext(),Integer.toString(MyNotifications.size()),Toast.LENGTH_LONG).show();
                        CommodityAdapter commodityAdapter= new CommodityAdapter(MyCommodities,getApplicationContext(),"admin",myEmail);
                        recyclerView.setAdapter(commodityAdapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );

    }
}
