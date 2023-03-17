package com.example.championship;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterFeelings extends RecyclerView.Adapter<AdapterFeelings.ViewHolder> {

    private LayoutInflater inflater;
    private List<Feelings> feelingsList;

    public AdapterFeelings(Context context, List<Feelings> feelingsList) {

        inflater = LayoutInflater.from(context);
        this.feelingsList = feelingsList;
    }

    @NonNull
    @Override
    public AdapterFeelings.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_feelings, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFeelings.ViewHolder holder, int position) {

        Feelings feeling = feelingsList.get(position);
        holder.tvFeeling.setText(feeling.getTitle());
        new DownloadImages(holder.imageFeeling).execute(feeling.getImage());
    }

    @Override
    public int getItemCount() {
        return feelingsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageFeeling;
        final TextView tvFeeling;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFeeling = itemView.findViewById(R.id.imageFeeling);
            tvFeeling = itemView.findViewById(R.id.tvFeeling);
        }
    }
}