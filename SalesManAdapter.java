package com.example.android.salesmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by raj garg on 12-05-2019.
 */

public class SalesManAdapter extends RecyclerView.Adapter<SalesManAdapter.SalesManViewHolder> {
        // A list to hod what to display
        List<Profile> MyNotifications;
        Context mctx;
        String type;
public SalesManAdapter(List<Profile> myNotifications, Context mctx,String WhoAmI) {
        MyNotifications = myNotifications;
        this.mctx = mctx;
        type=WhoAmI;
        }

@Override
public SalesManAdapter.SalesManViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mctx);
        View view = layoutInflater.inflate(R.layout.salesman_layout,null);
        return new SalesManViewHolder(view);
        }

    @Override
    public void onBindViewHolder(SalesManViewHolder holder, int position) {
        Profile profile = MyNotifications.get(position);
        final String string = profile.getEmail().toString();
        String username = string.substring(0,string.length()-10);
        holder.name.setText(username);
        if(type.equals("admin"))
        {
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mctx,MyProfile.class);
                    intent.putExtra("email",string);
                    mctx.startActivity(intent);
                }
            });
        }
    }

@Override
public int getItemCount() {
        return MyNotifications.size();
        }

class SalesManViewHolder extends RecyclerView.ViewHolder{
    TextView name;
    public SalesManViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.myName);
    }
}
}

