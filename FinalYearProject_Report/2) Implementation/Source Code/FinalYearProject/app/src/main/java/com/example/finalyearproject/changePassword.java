package com.example.finalyearproject;

import static android.content.ContentValues.TAG;
import static com.example.finalyearproject.register.sha256;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalyearproject.databinding.ActivityDashboardBinding;
import com.example.finalyearproject.databinding.FragmentChangePasswordBinding;
import com.example.finalyearproject.databinding.FragmentDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class changePassword extends Fragment {

    private FragmentChangePasswordBinding binding;

    private View rootView;

    EditText password_edit_text;
    EditText cpassword_edit_text;
    EditText old_edit_text;
    Button change_password;

    TextInputLayout layoutOldPassword;
    TextInputLayout layoutPassword;
    TextInputLayout layoutCpassword;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_change_password, container, false);

        //Initiating Dashboard title permanent
        ((dashboard)getActivity()).changeTitle("Change Password");

        //Getting an ID from the fragments
        password_edit_text = (EditText) rootView.findViewById(R.id.password);
        cpassword_edit_text = (EditText) rootView.findViewById(R.id.cpassword);
        old_edit_text = (EditText) rootView.findViewById(R.id.oldPassword);

        change_password = (Button) rootView.findViewById(R.id.ch_btn_change);

        layoutPassword = (TextInputLayout) rootView.findViewById(R.id.layout_password);
        layoutCpassword = (TextInputLayout) rootView.findViewById(R.id.layout_cpassword);
        layoutOldPassword = (TextInputLayout) rootView.findViewById(R.id.layout_oldPassword);

        auth = FirebaseAuth.getInstance();
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });

        return rootView;
    }

    private boolean change(){

        String oldPassword = old_edit_text.getText().toString().trim();
        String password = password_edit_text.getText().toString().trim();
        String newPassword =  sha256(password).toString();
        String cpassword = cpassword_edit_text.getText().toString().trim();

        // OLD Password Validation
        if(oldPassword.isEmpty()){
            layoutOldPassword.setError("Old password required");
           /* binding.layoutPassword.setError("Password is required");
            binding.password.requestFocus();*/
            return false;
        }

        //Password Validation
        if(password.isEmpty()){
            layoutPassword.setError("Password required");
           /* binding.layoutPassword.setError("Password is required");
            binding.password.requestFocus();*/
            return false;
        }

        if(password.length()<6){
            layoutPassword.setError("Password can't be less than 6");
//            binding.password.requestFocus();
            return false;
        }

        if(!password.matches(".*[!@#$%^&*+=/?].*")) {
            layoutPassword.setError("Required at least 1 special character");
        //    binding.password.requestFocus();
            return false;
        }

        if(!password.matches(".*[A-Z].*")) {
            layoutPassword.setError("Required at least 1 capital letter");
         //   binding.password.requestFocus();
            return false;
        }

        if(!password.matches(".*[0-9].*")) {
            layoutPassword.setError("Required at least 1 numeric number");
         //   binding.password.requestFocus();
            return false;
        }


        if(!password.matches(".*[a-z].*")) {
            layoutPassword.setError("Required at least 1 small letter");
        //    binding.password.requestFocus();
            return false;
        }

        //Confrim Password Validation
        if(cpassword.isEmpty()){
            layoutCpassword.setError("Confirm password is required");
       //     binding.cpassword.requestFocus();
            return false;
        }

        if(cpassword.length()<6){
            layoutCpassword.setError("Confirm password can't be less than 6");
      //      binding.cpassword.requestFocus();
            return false;
        }

        if(!cpassword.matches(".*[!@#$%^&*+=/?].*")) {
            layoutCpassword.setError("Required at least 1 special character");
      //      binding.cpassword.requestFocus();
            return false;
        }

        if(!cpassword.matches(".*[A-Z].*")) {
            layoutCpassword.setError("Required at least 1 capital letter");
       //     binding.cpassword.requestFocus();
            return false;
        }

        if(!cpassword.matches(".*[0-9].*")) {
            layoutCpassword.setError("Required at least 1 numeric number");
         //   binding.cpassword.requestFocus();
            return false;
        }

        if(!cpassword.matches(".*[a-z].*")) {
            layoutCpassword.setError("Required at least 1 small letter");
       //     binding.cpassword.requestFocus();
            return false;
        }

        if(!password.matches(cpassword)){
            layoutCpassword.setError("Confirm password not matched");
         //   binding.cpassword.requestFocus();
            return false;
        }

        //getting the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Getting auth from re authenticate user
      AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),oldPassword);
      user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
              if(user!=null){
//            reference.child("password").setValue(newPassword);
                  Log.d("password", "entered password"+newPassword);
                  user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
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
          }
      });




        return true;
    }
}