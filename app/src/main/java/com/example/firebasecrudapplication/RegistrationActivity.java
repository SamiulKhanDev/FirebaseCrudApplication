package com.example.firebasecrudapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private TextInputEditText username,password,confirmPassword;
    private Button registerButton;
    private TextView alreadyUser;
    private FirebaseAuth firebaseauth;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username = findViewById(R.id.logingUser);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        registerButton = findViewById(R.id.registerButton);
        alreadyUser = (TextView)findViewById(R.id.alreadyUser);
        firebaseauth = FirebaseAuth.getInstance();

        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               intent = new Intent(RegistrationActivity.this,LoginActivity.class);

               startActivity(intent);
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameText = username.getText().toString();
                if(userNameText.contains("@")==false){
                    Toast.makeText(RegistrationActivity.this, "Please Provide Valid Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                String passwordText = password.getText().toString();
                if(passwordText.length()<6){
                    Toast.makeText(RegistrationActivity.this, "Please Provide Password With Length More Then 6", Toast.LENGTH_SHORT).show();
                    return;
                }
                String confirmPasswordText = confirmPassword.getText().toString();
                if(passwordText.equals(confirmPasswordText)==false){
                    Toast.makeText(RegistrationActivity.this, "Password Are Not Matching,Please Re-Enter Them", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(userNameText) || TextUtils.isEmpty(passwordText) || TextUtils.isEmpty(confirmPasswordText)){
                    Toast.makeText(RegistrationActivity.this, "Please Fill All The Fields", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseauth.createUserWithEmailAndPassword(userNameText,passwordText).addOnCompleteListener(RegistrationActivity.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isComplete()){
                             Toast.makeText(RegistrationActivity.this, "User Has Successfully Registered", Toast.LENGTH_SHORT).show();
                             intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                             startActivity(intent);
                         }else{
                             Toast.makeText(RegistrationActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                         }
                        }
                    });
                }
            }
        });

    }
}