package com.example.finalyearproject;

import static com.example.finalyearproject.register.sha256;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalyearproject.databinding.ActivityDashboardBinding;
import com.example.finalyearproject.databinding.FragmentChangePasswordBinding;
import com.example.finalyearproject.databinding.FragmentDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changePassword extends Fragment {

    private FragmentChangePasswordBinding binding;

    private View rootView;

    EditText password;
    EditText cpassword;

    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_profile, container, false);

        //Initiating Dashboard title permanent
        ((dashboard)getActivity()).changeTitle("Change Password");

        password = (EditText) rootView.findViewById(R.id.password);
        cpassword = (EditText) rootView.findViewById(R.id.cpassword);

        auth = FirebaseAuth.getInstance();
/*        binding.chBtnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });*/

        return rootView;
    }

    private boolean change(){

        String password = binding.password.getText().toString().trim();
        String newPassword =  sha256(password).toString();
        String cpassword = binding.cpassword.getText().toString().trim();

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getActivity(), "Password Changed", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(getActivity(), "Couldn't change password", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}