package com.example.finalyearproject;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.finalyearproject.Models.CustomAdapter;
import com.example.finalyearproject.Models.NewsHeadlines;
import com.example.finalyearproject.Models.Report;
import com.example.finalyearproject.Models.ReportAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class view_report extends Fragment {

    private View rootView;
    private RelativeLayout relativeLayout;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_report, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Setting the title
        ((dashboard)getActivity()).changeTitle("Your Report Status");
        showReports();
        return rootView;
    }

    private void showReports(){
        ArrayList<Report> reportList = new ArrayList<Report>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    DataSnapshot reports = ds.child("Reports");
                    for (DataSnapshot report : reports.getChildren()) {
                        reportList.add(report.getValue(Report.class));
                    }
                }

                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_report);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                ReportAdapter adapter = new ReportAdapter(getActivity(), reportList);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.orderByKey().equalTo(user.getUid()).addListenerForSingleValueEvent(eventListener);
    }
}