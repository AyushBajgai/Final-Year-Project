package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.finalyearproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

private ActivityMainBinding binding;

EditText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, register.class);
                startActivity(i);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUsername();
                validatePassword();
            }
        });
    }

    //Password Validation
    private boolean validatePassword(){

        if(binding.password.getText().toString().trim().isEmpty()){
            binding.layoutPassword.setError("Password is required");
            binding.password.requestFocus();
            return false;
        }else if(binding.password.getText().toString().length()<6){
            binding.layoutPassword.setError("Password can't be less than 6");
            binding.password.requestFocus();
            return false;
        }else if(!binding.password.getText().toString().matches(".*[!@#$%^&*+=/?].*")) {
            binding.layoutPassword.setError("Required at least 1 special character");
            binding.password.requestFocus();
            return false;
        }
        else if(!binding.password.getText().toString().matches(".*[A-Z].*")) {
            binding.layoutPassword.setError("Required at least 1 capital letter");
            binding.password.requestFocus();
            return false;
        }
        else if(!binding.password.getText().toString().matches(".*[a-z].*")) {
            binding.layoutPassword.setError("Required at least 1 small letter");
            binding.password.requestFocus();
            return false;
        }
        else{
            binding.layoutPassword.setError(null);
            binding.layoutPassword.setErrorEnabled(false);
        }

        return true;
    }


    //Username Validation
    private boolean validateUsername() {

       if(binding.username.getText().toString().trim().isEmpty()){
        binding.layoutUsername.setError("Username is required");
        binding.username.requestFocus();
        return false;
       }else{
           binding.layoutUsername.isErrorEnabled();
       }

       return true;
    }



   /* public void loginPage(View view) {
        Intent i = new Intent(MainActivity.this,MainActivity.class);
        startActivity(new Intent(MainActivity.this,MainActivity.class));
    }

    public void registerPage(View view) {
        Intent i = new Intent(MainActivity.this,register.class);
        startActivity(new Intent(MainActivity.this,register.class));
    }*/
}