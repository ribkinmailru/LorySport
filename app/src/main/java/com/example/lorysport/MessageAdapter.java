package com.example.lorysport;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<Message> messages;
    public static final int RIGHT = 1;
    public static final int LEFT = 0;
    private boolean exe;
    private FirebaseFirestore fire;

    public MessageAdapter(ArrayList<Message> message) {
        this.messages = message;
        fire = FirebaseFirestore.getInstance();
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MessageAdapter.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == RIGHT && !exe) {
                View view = inflater.inflate(R.layout.message_right, parent, false);
                viewHolder = new ViewHolder(view);
                return viewHolder;
            } else if(viewType == LEFT && !exe) {
                View view = inflater.inflate(R.layout.message_left, parent, false);
                viewHolder = new ViewHolder(view);
                return viewHolder;
            }else if(viewType == RIGHT && exe){
                View view = inflater.inflate(R.layout.linearforchose ,parent,false);
                LinearLayout line = view.findViewById(R.id.linas);
                LinearLayout frima = view.findViewById(R.id.frima);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                line.removeView(frima);
                params.setMargins(100,0,10,0);
                line.setLayoutParams(params);
                MessageAdapter.ViewHolderExe viewHolderExe =  new ViewHolderExe(view);
                return viewHolderExe;
            }else {
                View view = inflater.inflate(R.layout.linearforchose ,parent,false);
                LinearLayout line = view.findViewById(R.id.linas);
                line.setGravity(Gravity.START);
                viewHolder =  new ViewHolder(view);
                return viewHolder;
            }
    }

    public void updatemessage(Message m){
        messages.add(m);
        notifyItemInserted(messages.size()-1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView message,time;
        public TextView name;
        public ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            image = itemView.findViewById(R.id.image);
        }
    }

    public static class ViewHolderExe extends RecyclerView.ViewHolder {
        public CircleImageView imageView;
        public TextView name;


        public ViewHolderExe(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image3);
            name = itemView.findViewById(R.id.name3);
        }
    }

        @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(!messages.get(position).photo && !messages.get(position).exercize) {
            ViewHolder holders = (ViewHolder)holder;
            holders.image.setVisibility(View.GONE);
            holders.message.setText(messages.get(position).text);
            holders.time.setText(AppManager.formessage(new Date(messages.get(position).time)));
        }else if(messages.get(position).photo){
            ViewHolder holders = (ViewHolder)holder;
            holders.message.setVisibility(View.GONE);
            holders.time.setVisibility(View.GONE);
            holders.image.setVisibility(View.VISIBLE);
            Picasso.get().load(messages.get(position).text).into(holders.image);
        }
        else{
            final ViewHolderExe hold =(ViewHolderExe)holder;
            fire.document(messages.get(position).text).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Exercise exe = documentSnapshot.toObject(Exercise.class);
                    hold.name.setText(exe.name);
                    Picasso.get().load(exe.image).into(hold.imageView);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        exe = messages.get(position).exercize;
        if (messages.get(position).author.equals(AppManager.user.getNumber())) {
            return RIGHT;
        }
        else {
            return LEFT;
        }

    }
}
