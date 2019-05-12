package com.example.android.salesmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText email,password,ids;
    Button login;
    TextView textView;
    FirebaseAuth auth;
    ProgressBar progressBar;
    DatabaseReference firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        email = (EditText) findViewById(R.id.email);
        ids = (EditText) findViewById(R.id.usersid);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        textView = (TextView) findViewById(R.id.sendtoregister);
        auth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBa);
        // If user has not registered yet
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String useremail = email.getText().toString();
                String userpassword = password.getText().toString();
                String userid = ids.getText().toString();
                if(TextUtils.isEmpty(useremail))
                {
                    // email not filled
                    Toast.makeText(getApplicationContext(),"Enter the email adress",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(userpassword))
                {
                    // password not filled
                    Toast.makeText(getApplicationContext(),"Enter the password",Toast.LENGTH_LONG).show();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if(!task.isSuccessful())
                            {
                                // if there is some problem in login
                                Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                final String chil = useremail.substring(0,useremail.length()-10);
                                firebaseDatabase.child("root").child("profile").child(chil).child("profileDetails").addListenerForSingleValueEvent(
                                        new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Profile pp = dataSnapshot.getValue(Profile.class);
                                                Boolean adm = pp.isAdmin();
                                                if(adm)
                                                {
                                                    // if admin logs in
                                                    Intent intent = new Intent(Login.this,MainAdmin.class);
                                                    intent.putExtra("myemail",pp.getEmail().toString());
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else
                                                {
                                                    // if salesman logs in
                                                    Intent intent = new Intent(Login.this,MainSalesperson.class);
                                                    intent.putExtra("myemail",pp.getEmail().toString());
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Toast.makeText(getApplicationContext(),"try again",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                );
                            }
                        }
                    });
                }
            }
        });
    }
}
