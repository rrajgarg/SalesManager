//Main page for the admin
package com.example.android.salesmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainAdmin extends AppCompatActivity {
    Button notifications,profiles,groupChat;
    List<CommodityClass> MyCommodities;
    List<CommodityClass> AllCommodities;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MaterialSearchView materialSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        profiles = (Button) findViewById(R.id.profiles);
        groupChat = (Button) findViewById(R.id.groupchats);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        materialSearchView = (MaterialSearchView) findViewById(R.id.mySearch);
        MyCommodities = new ArrayList<>();
        AllCommodities = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        final String MyEmail = bundle.getString("myemail");
        recyclerView = (RecyclerView) findViewById(R.id.admin_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notifications = (Button) findViewById(R.id.notification);

        profiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAdmin.this,ListSalesMan.class);
                intent.putExtra("YeahMe","admin");
                startActivity(intent);
            }
        });
        // When notification button is clicked
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAdmin.this,Notifications.class);
                // Tell notifications activity that i am an admin to give me rights of sending message
                intent.putExtra("type","admin");
                intent.putExtra("is","notification");
                startActivity(intent);
            }
        });

        // Commodities are filled in recycler view here
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
                        materialSearchView.closeSearch();
                        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                return false;
                            }
                        });
                        //   Toast.makeText(getApplicationContext(),Integer.toString(MyNotifications.size()),Toast.LENGTH_LONG).show();
                        CommodityAdapter commodityAdapter= new CommodityAdapter(MyCommodities,getApplicationContext(),"admin",MyEmail);
                        recyclerView.setAdapter(commodityAdapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }
    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        MenuItem menuItem = menu.findItem(R.id.Search);
        materialSearchView.setMenuItem(menuItem);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_commodities) {
            // sending the admin to fill the details of commodities
            Intent intent = new Intent(MainAdmin.this,CommodityDetails.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
