package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.finalyearproject.databinding.ActivityDashboardBinding;
import com.example.finalyearproject.databinding.ActivityRegisterBinding;

public class dashboard extends AppCompatActivity {

    //Initiating string dasboard title to be change
    private TextView dashboardTitleChange;

    private ActivityDashboardBinding dashboardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view =dashboardBinding.getRoot();
        setContentView(view);

        //Getting dashboard title by ID
        dashboardTitleChange = findViewById(R.id.title_dashboard);

        if(savedInstanceState == null){
            //Loading dashboard items fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view,new dashboardFragment()).commit();
        }

    }

    public void changeTitle(String getTitle){
        dashboardTitleChange.setText(getTitle);
    }
}