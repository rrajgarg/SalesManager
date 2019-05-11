package com.example.android.salesmanager;

import android.app.Notification;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by raj garg on 11-05-2019.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {
    // A list to hod what to display
    List<NotificationClass> MyNotifications;
    Context mctx;

    public NotificationsAdapter(List<NotificationClass> myNotifications, Context mctx) {
        MyNotifications = myNotifications;
        this.mctx = mctx;
    }

    @Override
    public NotificationsAdapter.NotificationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mctx);
        View view = layoutInflater.inflate(R.layout.notification_layout,null);
        return new NotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationsAdapter.NotificationsViewHolder holder, int position) {
        NotificationClass notificationClass = MyNotifications.get(position);
        holder.message.setText(notificationClass.getText().toString());
        String temp=notificationClass.getTime();
        String perm=temp.substring(0,2)+":"+temp.substring(2,4)+":"+temp.substring(4,6);
        holder.time.setText(perm);
    }

    @Override
    public int getItemCount() {
        return MyNotifications.size();
    }

    class NotificationsViewHolder extends RecyclerView.ViewHolder{
        TextView message,time;
        public NotificationsViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.whatsNotification);
            time = (TextView) itemView.findViewById(R.id.whatsTime);
        }
    }
}
