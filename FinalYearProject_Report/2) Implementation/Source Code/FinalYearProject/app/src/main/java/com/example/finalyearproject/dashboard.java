package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.finalyearproject.databinding.ActivityDashboardBinding;
import com.example.finalyearproject.databinding.ActivityRegisterBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class dashboard extends AppCompatActivity {

    //Initiating string dasboard title to be change
    private TextView dashboardTitleChange;

    fragment_profile Profile_fragment;

    private ActivityDashboardBinding dashboardBinding;

    BottomNavigationView bottomNavigationView;

    @RequiresApi(api = Build.VERSION_CODES.R)
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

        //While clicking on exit icon shows message
        dashboardBinding.imageExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(dashboard.this);

                builder.setMessage("Are you sure want to logout").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(dashboard.this, MainActivity.class));
                        FirebaseAuth.getInstance().signOut();
                       // finish();
                    }
                }).setNegativeButton("No", null);

                AlertDialog alert = builder.create();
                alert.show();
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

/*                    case R.id.btn_exit:
                        moveTaskToBack(true);
                        System.exit(0);
                        return true;*/
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);

        return true;
    }
}