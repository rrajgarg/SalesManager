//Main page for the salesman
package com.example.android.salesmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainSalesperson extends AppCompatActivity {
    Button myProfile,myChat,myGroup,myNotifications;
    List<CommodityClass> MyCommodities;
    List<CommodityClass> AllCommodities;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_salesperson);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        MyCommodities = new ArrayList<>();
        AllCommodities = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.salesman_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myNotifications = (Button) findViewById(R.id.notifications);
        myNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainSalesperson.this,Notifications.class);
                intent.putExtra("type","salesman");
                startActivity(intent);
            }
        });
        // Recycler View for Commodities has been set up here
        databaseReference.child("root").child("commodities").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        MyCommodities.clear();
                        AllCommodities.clear();
                        for(DataSnapshot xyz: dataSnapshot.getChildren())
                        {
                            CommodityClass commodityClass = xyz.getValue(CommodityClass.class);
                            AllCommodities.add(commodityClass);
                            if(commodityClass.getQuantity()!=0)
                            {
                                MyCommodities.add(commodityClass);
                            }
                        }
                        //   Toast.makeText(getApplicationContext(),Integer.toString(MyNotifications.size()),Toast.LENGTH_LONG).show();
                        CommodityAdapter commodityAdapter= new CommodityAdapter(MyCommodities,getApplicationContext());
                        recyclerView.setAdapter(commodityAdapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }
}
