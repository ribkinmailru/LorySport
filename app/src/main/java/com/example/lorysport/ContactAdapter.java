package com.example.lorysport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    ArrayList<User> users;
    StorageReference mStorage;
    Context ctx;
    public Listener listener;
    public Layout layout;
    public boolean exist;
    public boolean messages;
    public ArrayList<Message> mess;
    private String ref;

    public interface  Listener{
        void onClick(User user,int position);
    }
    public interface Layout{
        void onClick(User user);
    }
    public void setLayout(Layout layout){
        this.layout = layout;
    }
    public void setListener(Listener listener){
        this.listener = listener;
    }
    public ContactAdapter(ArrayList<User> users,Context ctx, String ref){
        this.ref = ref;
        this.users = users;
        this.ctx = ctx;
    }


    public ContactAdapter(ArrayList<User> users,boolean messages, ArrayList<Message> mess, Context ctx){
        this.messages = messages;
        this.mess = mess;
        this.users = users;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ViewHolder holder;
        mStorage = FirebaseStorage.getInstance().getReference();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contact,parent,false);
        holder = new ViewHolder(view);
        if (exist) {
            holder.add.setVisibility(View.VISIBLE);
            holder.message.setVisibility(View.GONE);
        }else{
            holder.add.setVisibility(View.GONE);
            holder.message.setVisibility(View.VISIBLE);
        }
        if (messages || ref!=null){
            if(ref!=null) {
                holder.message.setText(R.string.send);
            }else{
                holder.message.setVisibility(View.GONE);
            }
            holder.cont.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();
                    AppManager.contact = users.get(adapterPosition);
                    MainActivity2 activity = (MainActivity2) ctx;
                    activity.changefragment(7, ref);
                }
            });
        }
        else {
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();
                    listener.onClick(users.get(adapterPosition), adapterPosition);
                }
            });
            holder.message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();
                    layout.onClick(users.get(adapterPosition));
                }
            });
        }
        return holder;
    }

    public void changedata(ArrayList<User> users){
        this.users = users;
        exist = true;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        LinearLayout cont;
        ImageView image, add;
        TextView name, number, message;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            cont = view.findViewById(R.id.lins);
            image = view.findViewById(R.id.photo);
            name = view.findViewById(R.id.name);
            message = view.findViewById(R.id.messa);
            add = view.findViewById(R.id.addtocontact);
            number = view.findViewById(R.id.numberormessage);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactAdapter.ViewHolder holder, int position) {
        holder.name.setText(AppManager.firstUpperCase(users.get(position).name));
            if(users.get(position).avatar!=null) {
            Picasso.get().load(users.get(position).avatar).into(holder.image);
            }else{
            holder.image.setImageDrawable(ctx.getResources().getDrawable(R.drawable.user));
        }

        if(messages && mess.size()!=0){
            holder.number.setText(mess.get(position).text);
        }else if(!messages){
            holder.number.setText(users.get(position).number);
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


}
