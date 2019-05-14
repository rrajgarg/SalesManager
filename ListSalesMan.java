package com.example.android.salesmanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListSalesMan extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    List<Profile> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sales_man);
        recyclerView = (RecyclerView) findViewById(R.id.salesmanRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference= FirebaseDatabase.getInstance().getReference();
        Bundle bundle = getIntent().getExtras();
        final String YeahMe = bundle.getString("YeahMe");
        list=new ArrayList<>();
        databaseReference.child("root").child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> contactChildren = dataSnapshot.getChildren();
                for(DataSnapshot val: contactChildren)
                {
                    Profile pp = val.child("profileDetails").getValue(Profile.class);
                    Log.i("fsdlk",pp.getEmail());
                    list.add(pp);
                }
                SalesManAdapter salesManAdapter= new SalesManAdapter(list,getApplicationContext(),YeahMe);
                recyclerView.setAdapter(salesManAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
