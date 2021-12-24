package com.example.firebasecrudapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText loginUser,logingPassword;
    private Button loginButton;
    private TextView signup;
    private Intent intent;
    private FirebaseAuth firebaseauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.logingButton);
        loginUser = findViewById(R.id.logingUser);

        logingPassword = findViewById(R.id.loginPassword);
        signup = findViewById(R.id.signup);
        firebaseauth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });

        loginUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    loginUser.setText("");
                }else if(!hasFocus && loginUser.getText().length()==0){
                    loginUser.setText("Email Address");
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginUserName = loginUser.getText().toString();
                if(isValid(loginUserName)==false){
                    Toast.makeText(LoginActivity.this, "Please Provide Valid Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                String logingUserPassword = logingPassword.getText().toString();

                if(TextUtils.isEmpty(loginUserName) || TextUtils.isEmpty(logingUserPassword)){
                    Toast.makeText(LoginActivity.this, "Please Fill All The Forms", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseauth.signInWithEmailAndPassword(loginUserName,logingUserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isComplete()){
                                if(firebaseauth.getCurrentUser()==null){
                                    Toast.makeText(LoginActivity.this, "Logging Failed,Provide Valid Credentials", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }

                        }
                    });
                }
            }
        });
    }

    @Override
    protected   void onStart() {
        super.onStart();
        FirebaseUser user =firebaseauth.getCurrentUser();
        if(user!=null){
            intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}