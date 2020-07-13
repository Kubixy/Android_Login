package com.example.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> tasks;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick (int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener) {
        this.listener = listener;
    }

    public RecyclerViewAdapter(ArrayList<String> data) {
        this.tasks = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent , false);
        ViewHolder holder = new ViewHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.task.setText(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView btn;
        TextView task;
        RelativeLayout layout;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            layout = itemView.findViewById(R.id.Row);
            task = itemView.findViewById(R.id.task);
            btn = itemView.findViewById(R.id.opt);

            btn.setOnClickListener(view -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}