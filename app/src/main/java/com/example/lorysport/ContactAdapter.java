package com.example.lorysport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    ArrayList<User> users;


    public ContactAdapter(ArrayList<User> users){
        this.users = users;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contact,parent,false);
        holder = new ViewHolder(view);
        return holder;
    }

    public void changedata(ArrayList<User> users){
        this.users = users;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        LinearLayout cont;
        ImageView image;
        TextView name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            cont = view.findViewById(R.id.lins);
            image = view.findViewById(R.id.photo);
            name = view.findViewById(R.id.name);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        holder.name.setText(users.get(position).number);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


}
