package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.finalyearproject.databinding.ActivityForgetPasswordBinding;
import com.example.finalyearproject.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPassword extends AppCompatActivity {

    private ActivityForgetPasswordBinding binding;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth= FirebaseAuth.getInstance();

        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email();
            }
        });
    }

    private boolean email(){
        String username = binding.username.getText().toString().trim();

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

        auth.sendPasswordResetEmail(username).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                 if(task.isSuccessful()){
                     Toast.makeText(forgetPassword.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                 }
                 else{
                     Toast.makeText(forgetPassword.this, "Try again something went wrong", Toast.LENGTH_LONG).show();
                 }
            }
        });

        binding.layoutUsername.setErrorEnabled(false);
        binding.layoutUsername.setError(null);
        return true;
    }
}