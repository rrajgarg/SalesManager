//Main page for the admin
package com.example.android.salesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
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
