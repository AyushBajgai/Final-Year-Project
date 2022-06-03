package com.example.finalyearproject.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.CustomViewHolder>{
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Reports").child("input");
    private Context context;
    private List<Report> reports;

    public ReportAdapter(Context context, List<Report> reports) {
        this.context = context;
        this.reports = reports;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.report_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.input_text.setText("Input: " + reports.get(position).getInput());
        holder.description_text.setText(reports.get(position).getDescription());
        holder.suggestion_text.setText(reports.get(position).getSuggestion().replace("\n", " "));
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView input_text, description_text, suggestion_text;
        CardView cardView;
        ImageView btnDelete;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            //Getting an id from layout
            input_text = itemView.findViewById(R.id.input_text);
            description_text = itemView.findViewById(R.id.input_description);
            suggestion_text = itemView.findViewById(R.id.input_suggestion);
            cardView = itemView.findViewById(R.id.report_container);
            btnDelete =(ImageView) itemView.findViewById(R.id.delete_speak);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ref.child(reports.get(getAdapterPosition()).getInput()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                         //   FirebaseDatabase.getInstance().getReference().child("Reports").child("input").setValue(null);
                            reports.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            Toast.makeText(context.getApplicationContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
    }

}
