package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finalyearproject.databinding.ActivityRegisterBinding;

public class register extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view =binding.getRoot();
        setContentView(view);

        binding.btnLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(register.this,MainActivity.class);
                startActivity(i);
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatefName();
                validatelName();
                validateUsername();
                validatePassword();
                validatecPassword();
                validateAddress();
                validateContactNo();
            }


            private boolean validatefName() {
                if(binding.fname.getText().toString().trim().isEmpty()){
                    binding.layoutFname.setError("First name is required");
                    binding.fname.requestFocus();
                    return false;
                }else{
                    binding.layoutFname.isErrorEnabled();
                }
                return true;
            }

            private boolean validatelName() {
                if(binding.lname.getText().toString().trim().isEmpty()){
                    binding.layoutLname.setError("Last name is required");
                    binding.lname.requestFocus();
                    return false;
                }else{
                    binding.layoutLname.isErrorEnabled();
                }
                return true;
            }

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

            private boolean validatePassword() {
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
                    binding.layoutPassword.isErrorEnabled();
                }

                return true;
            }

            private boolean validatecPassword() {
                if(binding.cpassword.getText().toString().trim().isEmpty()){
                    binding.layoutCpassword.setError("Confirm password is required");
                    binding.cpassword.requestFocus();
                    return false;
                }else if(binding.cpassword.getText().toString().length()<6){
                    binding.layoutCpassword.setError("Password can't be less than 6");
                    binding.cpassword.requestFocus();
                    return false;
                }else if(!binding.cpassword.getText().toString().matches(".*[!@#$%^&*+=/?].*")) {
                    binding.layoutCpassword.setError("Required at least 1 special character");
                    binding.cpassword.requestFocus();
                    return false;
                }
                else if(!binding.cpassword.getText().toString().matches(".*[A-Z].*")) {
                    binding.layoutCpassword.setError("Required at least 1 capital letter");
                    binding.cpassword.requestFocus();
                    return false;
                }
                else if(!binding.cpassword.getText().toString().matches(".*[a-z].*")) {
                    binding.layoutCpassword.setError("Required at least 1 small letter");
                    binding.cpassword.requestFocus();
                    return false;
                }
                else{
                    binding.layoutCpassword.isErrorEnabled();
                }

                return true;
            }

            private boolean validateAddress() {
                if(binding.address.getText().toString().trim().isEmpty()){
                    binding.layoutAddress.setError("Address is required");
                    binding.address.requestFocus();
                    return false;
                }else{
                    binding.layoutAddress.isErrorEnabled();
                }

                return true;
            }

            private boolean validateContactNo() {
                if(binding.contactNo.getText().toString().trim().isEmpty()){
                    binding.layoutContactNumber.setError("Address is required");
                    binding.contactNo.requestFocus();
                    return false;
                }else{
                    binding.layoutContactNumber.isErrorEnabled();
                }

                return true;
            }

        });
    }
}