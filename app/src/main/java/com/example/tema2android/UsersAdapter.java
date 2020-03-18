package com.example.tema2android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tema2android.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    private List<User> users = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView markTextView;
        TextView nameTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView= itemView.findViewById(R.id.name_text_view);
            markTextView = itemView.findViewById(R.id.mark_text_view);
        }
    }

    public UsersAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_details,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.nameTextView.setText(users.get(position).getName());
        holder.markTextView.setText(users.get(position).getMark() + "");
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


}

