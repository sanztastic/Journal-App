package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionLabel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.journalapp.dbUtils.DatabaseUtil;

public class LoginActivity extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private Button loginButton;
    private MotionLabel register;
    private DatabaseUtil dbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        register = findViewById(R.id.registerLabel);
        dbUtil = new DatabaseUtil(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userName.getText().toString();
                String pass = password.getText().toString();
                if(dbUtil.loginUser(user,pass)){
                    Intent intent = new Intent(view.getContext(), JournalDash.class);
                    intent.putExtra("user_name",user);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Wrong password or username try again", Toast.LENGTH_SHORT).show();
                    userName.setText("");
                    password.setText("");
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SignUp.class);
                startActivity(intent);
            }
        });
    }
}