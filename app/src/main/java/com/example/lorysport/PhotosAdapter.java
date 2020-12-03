package com.example.lorysport;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {
    public ArrayList<String> uris;
    private Context context;
    private static final int ADD = 1;
    private static final int WATCH = 0;
    private Listeneradd add;
    private Listenerwatch watch;

    public interface Listeneradd{
        void onClick();
    }
    public interface  Listenerwatch{
        void onClick();
    }
    public void setAdd(Listeneradd add){
        this.add = add;
    }
    public void setWatch(Listenerwatch watch){
        this.watch = watch;
    }

    public PhotosAdapter(ArrayList<String> uris,Context context){
        this.context = context;
        this.uris = uris;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView image;
        ImageView addphoto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.photo);
            addphoto = itemView.findViewById(R.id.addphoto);
            cardView = itemView.findViewById(R.id.card);

        }
    }

    @NonNull
    @Override

    public PhotosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("countadapte", "true");
        PhotosAdapter.ViewHolder viewholder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.photos_item,parent,false);
        viewholder = new ViewHolder(view);
        if(viewType == WATCH){
            viewholder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watch.onClick();
                }
            });
        }else{
            viewholder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add.onClick();
                }
            });
        }
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosAdapter.ViewHolder holder, int position) {
        try {
            Picasso.get().load(uris.get(position)).into(holder.image);
        }catch (IndexOutOfBoundsException ex){
            holder.image.setBackgroundColor(context.getResources().getColor(R.color.edittext));
            holder.addphoto.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public int getItemViewType(int position) {
        try {
            uris.get(position);
            return WATCH;
        }catch (IndexOutOfBoundsException ex){
            return ADD;
        }
    }

    @Override
    public int getItemCount() {
        if(uris.size()>=3){
            return uris.size();
        }else{
            return 3;
        }
    }
}
