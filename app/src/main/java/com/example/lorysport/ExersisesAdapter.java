package com.example.lorysport;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExersisesAdapter extends RecyclerView.Adapter<ExersisesAdapter.ViewHolder> {
    ArrayList<Exercise> exe;
    Context context;
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }


    public interface Listener{
        void onClick(LinearLayout frima, int position);
    }



    public ExersisesAdapter(ArrayList<Exercise> exe, Context context){
        this.exe = exe;
        this.context = context;
    }

    @NonNull
    @Override
    public ExersisesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ExersisesAdapter.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewUser = inflater.inflate(R.layout.linearforchose,parent,false);
        viewHolder = new ExersisesAdapter.ViewHolder(viewUser);
        viewHolder.frima.setVisibility(View.GONE);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View linearLayout;
        ImageView image;
        ImageView imageview;
        LinearLayout lina;
        TextView name;
        LinearLayout frima;
        ImageButton detail, favorite, delete, diffic;

        public ViewHolder(View ll) {
            super(ll);
            linearLayout = ll;
            name = ll.findViewById(R.id.name3);
            lina = ll.findViewById(R.id.lina);
            image = ll.findViewById(R.id.image3);
            imageview = ll.findViewById(R.id.imageView3);
            frima = ll.findViewById(R.id.frima);
            favorite = ll.findViewById(R.id.fav);
            delete = ll.findViewById(R.id.delete);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final ExersisesAdapter.ViewHolder holder, final int position) {
        holder.name.setText(exe.get(position).getName());
        Log.d("tag", exe.get(position).image);
        Picasso.get().load(exe.get(position).image).into(holder.image);
        holder.lina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(holder.frima, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return exe.size();
    }
}
