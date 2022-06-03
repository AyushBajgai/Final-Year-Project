package com.example.finalyearproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class fragment_profile extends Fragment {

    TextView changePassowrd;

    private FirebaseUser user;

    private DatabaseReference reference;

    private String userID;

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_profile, container, false);

        //Setting the title
        ((dashboard)getActivity()).changeTitle("Your Profile");

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        //Getting an ID from text field
        final TextView fName_txt = (TextView)  rootView.findViewById(R.id.first_name);
        final TextView lName_txt = (TextView)  rootView.findViewById(R.id.last_name);
        final TextView email_txt = (TextView)  rootView.findViewById(R.id.email);
        final TextView address_txt = (TextView)  rootView.findViewById(R.id.address);
        final TextView phone_txt = (TextView)  rootView.findViewById(R.id.contact);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if(userprofile != null){
                    String fName = userprofile.firstName;
                    String lName = userprofile.lastName;
                    String address = userprofile.address;
                    String email = userprofile.email;
                    String contactNo = userprofile.contactNo;

                    fName_txt.setText(fName);
                    lName_txt.setText(lName);
                    email_txt.setText(email);
                    address_txt.setText(address);
                    phone_txt.setText(contactNo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        changePassowrd = (TextView) rootView.findViewById(R.id.btn_change);
        changePassowrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_view,new changePassword()).addToBackStack(null).commit();
            }
        });


        return rootView;
    }
}