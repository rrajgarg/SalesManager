package com.example.android.salesmanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CommodityDetails extends AppCompatActivity {
    DatabaseReference databaseReference;
    EditText commodityName,commodityQuantity,commodityProfit;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_details);
        commodityName = (EditText) findViewById(R.id.name);
        commodityProfit = (EditText) findViewById(R.id.profit);
        commodityQuantity = (EditText) findViewById(R.id.quantity);
        button = (Button) findViewById(R.id.add);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = commodityName.getText().toString();
                final String Squantity = commodityQuantity.getText().toString();
                final String Sprofit = commodityProfit.getText().toString();
                databaseReference.child("root").child("commodities").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(name))
                        {
                            int Quantity = dataSnapshot.child(name).getValue(CommodityClass.class).getQuantity()+Integer.parseInt(Squantity);
                            int Profit = dataSnapshot.child(name).getValue(CommodityClass.class).getProfit();
                            CommodityClass cc = new CommodityClass(name,Quantity,Profit,null);
                            databaseReference.child("root").child("commodities").child(name).setValue(cc).addOnSuccessListener(
                                    new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(),"Commodity has bee added",Toast.LENGTH_LONG).show();
                                        }
                                    }
                            );
                        }
                        else
                        {
                            CommodityClass cc = new CommodityClass(name,Integer.parseInt(Squantity),Integer.parseInt(Sprofit));
                            databaseReference.child("root").child("commodities").child(name).setValue(cc);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
