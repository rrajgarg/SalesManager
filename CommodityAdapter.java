package com.example.android.salesmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by raj garg on 11-05-2019.
 */

public class CommodityAdapter extends RecyclerView.Adapter<CommodityAdapter.CommodityViewHolder> {
    // A list to hod what to display
    List<CommodityClass> MyCommodities;
    Context mctx;
    String type;
    DatabaseReference databaseReference;
    String email;
    DatabaseReference databaseReference1;
    public CommodityAdapter(List<CommodityClass> myCommodities, Context mctx,String type,String email) {
        MyCommodities = myCommodities;
        this.mctx = mctx;
        this.type = type;
        this.email = email;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference1 = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public CommodityAdapter.CommodityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mctx);
        View view = layoutInflater.inflate(R.layout.commodity_layout,null);
        return new CommodityAdapter.CommodityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommodityViewHolder holder, int position) {
        final CommodityClass commodityClass = MyCommodities.get(position);
        holder.name.setText(commodityClass.getCommodityName().toString());
        holder.quantity.setText(Integer.toString(commodityClass.getQuantity()));
        holder.profit.setText(Integer.toString(commodityClass.getProfit()));
        if(type.equals("admin"))
        {
            holder.button.setVisibility(View.GONE);
        }
        else
        {
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference.child("root").child("commodities").child(commodityClass.getCommodityName().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final CommodityClass commodityClass1 = dataSnapshot.getValue(CommodityClass.class);
                            final String commodityName = commodityClass1.getCommodityName().toString();
                            commodityClass1.setQuantity(commodityClass1.getQuantity()-1);
                            final String username = email.substring(0,email.length()-10);
                            databaseReference1.child("root").child("commodities").child(commodityClass1.getCommodityName().toString()).setValue(commodityClass1);
                            databaseReference1.child("root").child("profile").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild("SoldCommodities"))
                                    {
                                        if(dataSnapshot.child("SoldCommodities").hasChild(commodityName))
                                        {
                                            CommodityClass commodityClass2 = dataSnapshot.child("SoldCommodities").child(commodityName).getValue(CommodityClass.class);
                                            commodityClass2.setQuantity(commodityClass2.getQuantity()+1);
                                            databaseReference1.child("root").child("profile").child(username).child("SoldCommodities").child(commodityName).setValue(commodityClass2);
                                        }
                                        else
                                        {
                                            commodityClass1.setQuantity(1);
                                            databaseReference1.child("root").child("profile").child(username).child("SoldCommodities").child(commodityName).setValue(commodityClass1);
                                        }
                                    }
                                    else
                                    {
                                        commodityClass1.setQuantity(1);
                                        databaseReference1.child("root").child("profile").child(username).child("SoldCommodities").child(commodityName).setValue(commodityClass1);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return MyCommodities.size();
    }

    class CommodityViewHolder extends RecyclerView.ViewHolder{
        TextView name,quantity,profit;
        Button button;
        public CommodityViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nameof);
            quantity = (TextView) itemView.findViewById(R.id.quantityof);
            profit = (TextView) itemView.findViewById(R.id.profitof);
            button = (Button) itemView.findViewById(R.id.sell);
        }
    }
}

