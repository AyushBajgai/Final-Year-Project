package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.finalyearproject.databinding.ActivityDashboardBinding;
import com.example.finalyearproject.databinding.ActivityRegisterBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class dashboard extends AppCompatActivity {

    //Initiating string dasboard title to be change
    private TextView dashboardTitleChange;

    fragment_profile Profile_fragment;

    private ActivityDashboardBinding dashboardBinding;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view =dashboardBinding.getRoot();
        setContentView(view);

        //Getting dashboard title by ID
        dashboardTitleChange = findViewById(R.id.title_dashboard);

        //While Clicking on Profile icon load profile fragment
        dashboardBinding.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view,new fragment_profile()).addToBackStack(null).commit();
            }
        });

        //Button Navigation
        dashboardBinding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.btn_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view, new dashboardFragment()).commit();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.btn_exit:
                        moveTaskToBack(true);
                        System.exit(0);
                        return true;
                }
                return false;
            }
        });



        if(savedInstanceState == null){
            //Loading dashboard items fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view,new dashboardFragment()).commit();
        }
      //  return dashboardBinding.getRoot();

    }

    public void changeTitle(String getTitle){
        dashboardTitleChange.setText(getTitle);
    }
}