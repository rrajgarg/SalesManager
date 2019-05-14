package com.example.android.salesmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Notifications extends AppCompatActivity {
    EditText editText;
    Button button;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    List<NotificationClass> MyNotifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editText = (EditText) findViewById(R.id.writeNotifi);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        button = (Button) findViewById(R.id.sends);
        MyNotifications = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        String types = bundle.getString("type");
        String is = bundle.getString("is");
        // Because Salesman is not allowed to send notifications
        databaseReference.child("root").child("notifications").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        MyNotifications.clear();
                        for(DataSnapshot xyz: dataSnapshot.getChildren())
                        {
                            NotificationClass notificationClass = xyz.getValue(NotificationClass.class);
                            MyNotifications.add(notificationClass);
                        }
                     //   Toast.makeText(getApplicationContext(),Integer.toString(MyNotifications.size()),Toast.LENGTH_LONG).show();
                        NotificationsAdapter notificationsAdapter= new NotificationsAdapter(MyNotifications,getApplicationContext());
                        recyclerView.setAdapter(notificationsAdapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
        if(types.equals("salesman"))
        {
            editText.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        }
        else
        {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Extracting time in yyyyMMdd_HHmmss form
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String currentDateandTime = sdf.format(new Date());
                    final String time = currentDateandTime.substring(9,currentDateandTime.length());
                    final String message = editText.getText().toString();
                    if(message!=null)
                    {
                        databaseReference.child("root").child("count").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Count count = dataSnapshot.getValue(Count.class);
                                // This is done so that latest notifications remain on the top
                                int xx = count.getNotifications();
                                String id = Integer.toString(xx);
                                NotificationClass obj = new NotificationClass(message,time);

                                // value of count of notifications being set here
                                Count cuntu = new Count(count.getNotifications()+1,count.getGlobalMessages());
                                databaseReference.child("root").child("count").setValue(cuntu);
                                databaseReference.child("root").child("notifications").child(id).setValue(obj).addOnSuccessListener(
                                        new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(),"Notification Sent Successfully",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                );
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(),"Unnable to send the notification",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });
        }
    }
}
