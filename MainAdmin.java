//Main page for the admin
package com.example.android.salesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainAdmin extends AppCompatActivity {
    Button notifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        notifications = (Button) findViewById(R.id.notification);
        // When notification button is clicked
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAdmin.this,Notifications.class);
                // Tell notifications activity that i am an admin to give me rights of sending message
                intent.putExtra("type","admin");
                startActivity(intent);
            }
        });
    }
}
