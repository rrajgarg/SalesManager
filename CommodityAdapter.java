package com.example.android.salesmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by raj garg on 11-05-2019.
 */

public class CommodityAdapter extends RecyclerView.Adapter<CommodityAdapter.CommodityViewHolder> {
    // A list to hod what to display
    List<CommodityClass> MyCommodities;
    Context mctx;

    public CommodityAdapter(List<CommodityClass> myCommodities, Context mctx) {
        MyCommodities = myCommodities;
        this.mctx = mctx;
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
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("I am here","Heloooooooooooooooooooo");
            }
        });
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

