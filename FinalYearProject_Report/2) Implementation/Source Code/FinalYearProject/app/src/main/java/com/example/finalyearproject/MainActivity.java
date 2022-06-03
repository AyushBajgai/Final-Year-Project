package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalyearproject.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class MainActivity extends AppCompatActivity {

private ActivityMainBinding binding;

private FirebaseAuth mAuth;

EditText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        binding.btnRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, register.class);
                startActivity(i);
            }
        });

        binding.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, forgetPassword.class);
                startActivity(i);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginValidation();
            }
        });
    }

    //Login Validation
    private boolean loginValidation(){
        String username = binding.username.getText().toString().trim();
        String password = binding.password.getText().toString().trim();
        //Validation for Email address
        if(username.isEmpty()){
            binding.layoutUsername.setError("Email is required");
            binding.username.requestFocus();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            binding.layoutUsername.setError("Invalid email address");
            binding.username.requestFocus();
            return false;
        }

        //Password Validation
        if(password.isEmpty()){
            binding.layoutPassword.setError("Password is required");
            binding.password.requestFocus();
            return false;
        }

        if(password.length()<6){
            binding.layoutPassword.setError("Password can't be less than 6");
            binding.password.requestFocus();
            return false;
        }

        if(!password.matches(".*[!@#$%^&*+=/?].*")) {
            binding.layoutPassword.setError("Required at least 1 special character");
            binding.password.requestFocus();
            return false;
        }

        if(!password.matches(".*[A-Z].*")) {
            binding.layoutPassword.setError("Required at least 1 capital letter");
            binding.password.requestFocus();
            return false;
        }

        if(!password.matches(".*[0-9].*")) {
            binding.layoutPassword.setError("Required at least 1 numeric number");
            binding.password.requestFocus();
            return false;
        }

        if(!password.matches(".*[a-z].*")) {
            binding.layoutPassword.setError("Required at least 1 small letter");
            binding.password.requestFocus();
            return false;
        }

        else{
            binding.layoutUsername.setErrorEnabled(false);
            binding.layoutUsername.setError(null);

            binding.layoutPassword.setErrorEnabled(false);
            binding.layoutPassword.setError(null);

            mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent intent = new Intent(MainActivity.this,dashboard.class);
                        //clearing the previous tasks if present!
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    }else {
                        // Registration failed
                        Toast.makeText(getApplicationContext(),"Login Failed" + " \nPlease try again",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        return true;
    }

}