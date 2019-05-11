//Main page for the salesman
package com.example.android.salesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainSalesperson extends AppCompatActivity {
    Button myProfile,myChat,myGroup,myNotifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_salesperson);
        myNotifications = (Button) findViewById(R.id.notifications);
        myNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainSalesperson.this,Notifications.class);
                intent.putExtra("type","salesman");
                startActivity(intent);
            }
        });
    }
}
