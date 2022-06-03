package com.example.finalyearproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalyearproject.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private FirebaseAuth mAuth;

    ProgressBar progressBar;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view =binding.getRoot();
        setContentView(view);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        EditText txt_email = (EditText) findViewById(R.id.username);
        ProgressBar progress = (ProgressBar) findViewById(R.id.register_progress);

        binding.btnLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(register.this,MainActivity.class);
                startActivity(i);
            }
        });

        //When clicked button is pressed
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }

        });
    }

    private boolean validation(){

        String fname = binding.fname.getText().toString().trim();
        String lname = binding.lname.getText().toString().trim();
        String username = binding.username.getText().toString().trim();
        String password = binding.password.getText().toString().trim();
        String newPassword =  sha256(password).toString();
        String cpassword = binding.cpassword.getText().toString().trim();
        String phone = binding.contactNo.getText().toString().trim();
        String address = binding.address.getText().toString().trim();

        //Validation for first name
        if(fname.isEmpty()){
            binding.layoutFname.setError("First name is required");
            binding.fname.requestFocus();
            return false;
        }
        if(fname.length()<3){
            binding.layoutFname.setError("Invalid first name");
            binding.fname.requestFocus();
            return false;
        }


        //Validation for last name
        if(lname.isEmpty()){
            binding.layoutLname.setError("Last name is required");
            binding.lname.requestFocus();
            return false;
        }

        if(lname.length()<3){
            binding.layoutLname.setError("Invalid last name");
            binding.lname.requestFocus();
            return false;
        }

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

        mAuth.fetchSignInMethodsForEmail(username)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                        if (!isNewUser) {
                            binding.layoutUsername.setError("Email is already taken");
                            binding.username.requestFocus();

                        }

                    }
                });


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

        //Confrim Password Validation
        if(cpassword.isEmpty()){
            binding.layoutCpassword.setError("Confirm password is required");
            binding.cpassword.requestFocus();
            return false;
        }

        if(cpassword.length()<6){
            binding.layoutCpassword.setError("Confirm password can't be less than 6");
            binding.cpassword.requestFocus();
            return false;
        }

        if(!cpassword.matches(".*[!@#$%^&*+=/?].*")) {
            binding.layoutCpassword.setError("Required at least 1 special character");
            binding.cpassword.requestFocus();
            return false;
        }

        if(!cpassword.matches(".*[A-Z].*")) {
            binding.layoutCpassword.setError("Required at least 1 capital letter");
            binding.cpassword.requestFocus();
            return false;
        }

        if(!cpassword.matches(".*[0-9].*")) {
            binding.layoutCpassword.setError("Required at least 1 numeric number");
            binding.cpassword.requestFocus();
            return false;
        }

        if(!cpassword.matches(".*[a-z].*")) {
            binding.layoutCpassword.setError("Required at least 1 small letter");
            binding.cpassword.requestFocus();
            return false;
        }

        if(!password.matches(cpassword)){
            binding.layoutCpassword.setError("Confirm password not matched");
            binding.cpassword.requestFocus();
            return false;
        }

        //Phone number validation
        if(phone.isEmpty()){
            binding.layoutContactNumber.setError("Phone number is required");
            binding.contactNo.requestFocus();
            return false;
        }

        if(!phone.matches(".*[0-9].*")){
            binding.layoutContactNumber.setError("Phone number must be numeric");
            binding.contactNo.requestFocus();
            return false;
        }

        if(phone.length() != 10){
            binding.layoutContactNumber.setError("Invalid phone number");
            binding.contactNo.requestFocus();
            return false;
        }

        //Address Validation
        if(address.isEmpty()){
            binding.layoutAddress.setError("Address is required");
            binding.address.requestFocus();
            return false;
        }

        else{
            binding.layoutFname.setErrorEnabled(false);
            binding.layoutFname.setError(null);

            binding.layoutLname.setErrorEnabled(false);
            binding.layoutLname.setError(null);

            binding.layoutPassword.setErrorEnabled(false);
            binding.layoutPassword.setError(null);

            binding.layoutCpassword.setErrorEnabled(false);
            binding.layoutCpassword.setError(null);

            binding.layoutUsername.setErrorEnabled(false);
            binding.layoutUsername.setError(null);

            binding.layoutAddress.setErrorEnabled(false);
            binding.layoutAddress.setError(null);

            binding.layoutContactNumber.setErrorEnabled(false);
            binding.layoutContactNumber.setError(null);

            //Adding into Firebase database
            mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user = new User(fname, lname, username, newPassword, phone, address);

                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(register.this,MainActivity.class);
                                    //clearing the previous tasks if present!
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    // Registration failed
                                    Toast.makeText(getApplicationContext(),"Registration Failed" + " \nPlease try again",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else {
                        // Registration failed
                        Toast.makeText(getApplicationContext(),"Registration Failed" + " \nPlease try again",Toast.LENGTH_LONG).show();
                    }
                }
            });
            Log.d("user", "username"+username);
        }

        return true;
    }

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}