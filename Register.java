//This activity is used to register both a salesman and an admin
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText username,userid,useremail,userpassword,check;
    Button registerSalesman,registerAdmin;
    ProgressBar progressBar;
    FirebaseAuth auth;
    DatabaseReference mDataBase;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        registerAdmin = (Button) findViewById(R.id.RegisterAdmin);
        registerSalesman = (Button) findViewById(R.id.RegisterSalesman);
        useremail = (EditText) findViewById(R.id.useremail);
        userid = (EditText) findViewById(R.id.userid);
        username = (EditText) findViewById(R.id.username);
        userpassword = (EditText) findViewById(R.id.userpassword);
        check = (EditText) findViewById(R.id.Check);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.sendtologin);
        // If already a user
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
        // get firebase Auth Instance
        auth = FirebaseAuth.getInstance();
        // when register as salesman button is clicked
        registerSalesman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = username.getText().toString();
                final String id = userid.getText().toString();
                final String email = useremail.getText().toString();
                String password = userpassword.getText().toString();
                String ConfirmPassword = check.getText().toString();
                if(!(password.equals(ConfirmPassword)))
                {
                    // password is not retyped correctly
                    Toast.makeText(getApplicationContext(),"password has not been retyped properly",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(name))
                {
                    // Please enter the username
                    Toast.makeText(getApplicationContext(),"Enter the username",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(id))
                {
                    // Please enter the id number
                    Toast.makeText(getApplicationContext(),"Enter the id number",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(password))
                {
                    // Please enter the password
                    Toast.makeText(getApplicationContext(),"Enter the password",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(email))
                {
                    // Please enter the email
                    Toast.makeText(getApplicationContext(),"Enter the email",Toast.LENGTH_LONG).show();
                }
                else
                {
                    // every field is filled properly
                    progressBar.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Register.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if(!task.isSuccessful())
                            {
                                // if sign up fails
                                Toast.makeText(getApplicationContext(),"Failed to sign up",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                // if sign up is successful
                                // removing @gmail.com from email adress
                                final String chil = email.substring(0,email.length()-10);
                                // profile is stored in an object
                                Profile profile = new Profile(name,id,email,false);
                                mDataBase.child("root").child("profile").child(chil).child("profileDetails").setValue(profile).addOnSuccessListener(
                                        new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Signed in Successfully
                                                Toast.makeText(Register.this,"Signed in successfully",Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(Register.this,MainSalesperson.class);
                                                intent.putExtra("myemail",email);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                ).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"Failed to store data",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
        registerAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = username.getText().toString();
                final String id = userid.getText().toString();
                final String email = useremail.getText().toString();
                String password = userpassword.getText().toString();
                String ConfirmPassword = check.getText().toString();
                if(!(password.equals(ConfirmPassword)))
                {
                    // password is not retyped correctly
                    Toast.makeText(getApplicationContext(),"password has not been retyped properly",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(name))
                {
                    // Please enter the username
                    Toast.makeText(getApplicationContext(),"Enter the username",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(id))
                {
                    // Please enter the id number
                    Toast.makeText(getApplicationContext(),"Enter the id number",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(password))
                {
                    // Please enter the password
                    Toast.makeText(getApplicationContext(),"Enter the password",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(email))
                {
                    // Please enter the email
                    Toast.makeText(getApplicationContext(),"Enter the email",Toast.LENGTH_LONG).show();
                }
                else
                {
                    // every field is filled properly
                    progressBar.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Register.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if(!task.isSuccessful())
                            {
                                // if sign up fails
                                Toast.makeText(getApplicationContext(),"Failed to sign up",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                // if sign up is successful
                                // removing @gmail.com from the email adress
                                final String chil = email.substring(0,email.length()-10);
                                // profile is stored in an object
                                Profile profile = new Profile(name,id,email,true);
                                mDataBase.child("root").child("profile").child(chil).child("profileDetails").setValue(profile).addOnSuccessListener(
                                        new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Signed in Successfully
                                                Toast.makeText(Register.this,"Signed in successfully",Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(Register.this,MainAdmin.class);
                                                intent.putExtra("myemail",email);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                ).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"Failed to store data",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}
