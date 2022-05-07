package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finalyearproject.databinding.ActivityDashboardBinding;
import com.example.finalyearproject.databinding.ActivityRegisterBinding;
import com.example.finalyearproject.databinding.ActivityViewReportBinding;

public class dashboard extends AppCompatActivity {

    private ActivityDashboardBinding dashboardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view =dashboardBinding.getRoot();
        setContentView(view);

        //For Viewing Profile
        dashboardBinding.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(dashboard.this,view_profile.class);
                startActivity(i);
            }
        });

        //For Top Stories
        dashboardBinding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(dashboard.this,top_stories.class);
                startActivity(i);
            }
        });

        //For Viewing Reports
        dashboardBinding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(dashboard.this,view_report.class);
                startActivity(i);
            }
        });

        //For Useful Tips
        dashboardBinding.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(dashboard.this,useful_tips.class);
                startActivity(i);
            }
        });

        //For Analyzing Speech
        dashboardBinding.cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(dashboard.this,analyze_speech.class);
                startActivity(i);
            }
        });


    }
}