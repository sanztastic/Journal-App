package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.journalapp.dbUtils.DatabaseUtil;

public class SignUp extends AppCompatActivity {

    private EditText fullname;
    private EditText address;
    private EditText username;
    private EditText password;
    private Button register;
    private DatabaseUtil dbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullname = findViewById(R.id.fullname);
        address = findViewById(R.id.address);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        dbUtil = new DatabaseUtil(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String add = password.getText().toString();
                String name = fullname.getText().toString();
                Toast toast;
                if(dbUtil.checkUsernameExist(user)){
                    toast = Toast.makeText(getApplicationContext(), "Username already exist!!Please try another username", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    boolean insert = dbUtil.insertRegisterData(name,user,add,pass);
                    if(insert){
                        toast = Toast.makeText(getApplicationContext(), "Register Successdull", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(view.getContext(),LoginActivity.class);
                        startActivity(intent);
                    }else{
                        toast = Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
    }
}