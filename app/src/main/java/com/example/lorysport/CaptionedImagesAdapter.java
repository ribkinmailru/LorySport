package com.example.lorysport;


import android.content.Context;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.io.File;


class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {
    private String[] captions;
    private String[] param;
    private String[] edittext;
    private int[] imageIds;
    private boolean[] isFavorite;
    private Listener1 listener1;
    private Listener2 listener2;
    private Listener3 listener3;
    private Listener4 listener4;
    private Listener5 listener5;
    int inputtype;
    int custom;
    private double[] change;
    int[] count;
    String[] stringss;
    String[] download;
    Context ctx;


    public void changeDatas(String[] captions, int[] imageIds, boolean[] isFavorite){
        this.captions = captions;
        this.imageIds = imageIds;
        this.isFavorite = isFavorite;
        notifyDataSetChanged();
    }

    public void changeDatas(String[] captions){
        this.captions = captions;
        notifyDataSetChanged();
    }

    public void changeDatar(String[] captions, int position){
        this.captions = captions;
        notifyItemRemoved(position);
    }

    public void changeDatass(String[] captions, int[] imageIds,boolean[] isFavorite, String[] download, int position){
        this.download = download;
        this.captions = captions;
        this.imageIds = imageIds;
        this.isFavorite = isFavorite;
        notifyItemRemoved(position);
    }


    public void changeShadule(int custom, String[] captions, int[] imageIds, boolean[] isFavorite, String[] download,
                              Context ctx, int position){
        this.custom = custom;
        this.ctx = ctx;
        this.download = download;
        this.captions = captions;
        this.imageIds = imageIds;
        this.isFavorite = isFavorite;
        notifyItemRemoved(position);
    }


    public interface Listener1 {
        void onClick1(int position, View cardView);
    }
    public interface Listener2 {
        void onClick2(View view, int position, LinearLayout line);
    }
    public interface Listener3 {
        void onClick3(int position, EditText edittext, TextView text);
    }

    public interface Listener4 {
        void onClick4(int position, ImageButton cardview, String bs);
    }

    public interface Listener5 {
        void onClick5(int position,String bs, View linee);
    }

    public void setListener1(Listener1 listener1) {
        this.listener1 = listener1;
    }
    public void setListener2(Listener2 listener2) {
        this.listener2 = listener2;
    }
    public void setListener3(Listener3 listener3) {
        this.listener3 = listener3;
    }
    public void setListener4(Listener4 listener4) {
        this.listener4 = listener4;
    }
    public void setListener5(Listener5 listener5) {
        this.listener5 = listener5;
    }



    public CaptionedImagesAdapter(int custom, String[] captions, int[] imageIds, boolean[] isFavorite, String[] download, Context ctx) {
        this.captions = captions;
        this.imageIds = imageIds;
        this.custom = custom;
        this.isFavorite = isFavorite;
        this.download = download;
        this.ctx = ctx;
    }

    public CaptionedImagesAdapter(String[] captions, String[] param, String[] edittexte, int custom, double[] change, int[] count,
                                  String[] steingss) {
        this.captions = captions;
        this.param = param;
        this.edittext = edittexte;
        this.custom = custom;
        this.change = change;
        this.count = count;
        this.stringss = steingss;
    }

    public CaptionedImagesAdapter(String[] captions, int[] imageIds) {
        this.captions = captions;
        this.imageIds = imageIds;
    }
    public CaptionedImagesAdapter(String[] captions){
        this.captions = captions;
    }

    public int getItemCount() {
        return captions.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View linearLayout;
        private View cardView;
        ImageView image;
        ImageView imageview;
        LinearLayout lina;
        TextView name, changes, pararm;
        LinearLayout frima;
        EditText editpara;
        ImageButton detail, favorite, delete, diffic;


        public ViewHolder(View ll) {
            super(ll);
           cardView = ll;
           image = ll.findViewById(R.id.splash_imageview);
           name = ll.findViewById(R.id.type);

        }

        public ViewHolder(View ll, int i) {
            super(ll);
            if(i==3) {
                linearLayout = ll;
                name = ll.findViewById(R.id.name3);
                lina = ll.findViewById(R.id.lina);
                image = ll.findViewById(R.id.image3);
                imageview = ll.findViewById(R.id.imageView3);
                frima = ll.findViewById(R.id.frima);
                favorite = ll.findViewById(R.id.fav);
                delete = ll.findViewById(R.id.delete);
            }
            if(i==4) {
                name = ll.findViewById(R.id.textView10);
                pararm = ll.findViewById(R.id.textView11);
                changes = ll.findViewById(R.id.textView4);
                editpara = ll.findViewById(R.id.editpara);
            }
            if(i==5){
                linearLayout = ll;
                name = ll.findViewById(R.id.namer);
            }
        }
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inputtype == 3){
            final ViewHolder viewHolder;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View viewUser = inflater.inflate(R.layout.linearforchose,parent,false);
            viewHolder = new ViewHolder(viewUser, 3);
            viewHolder.frima.setVisibility(View.GONE);
            viewHolder.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    listener4.onClick4(adapterPosition, viewHolder.favorite, captions[adapterPosition]);
                }
            });
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    listener2.onClick2(v, adapterPosition, viewHolder.frima);

                }
            });

            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    listener5.onClick5(adapterPosition, captions[adapterPosition], viewHolder.linearLayout);

                }
            });
            return viewHolder;
        }
        if(inputtype == 4){
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewUser = inflater.inflate(R.layout.bodyrecycler,parent,false);
        viewHolder = new ViewHolder(viewUser, 4);
        return viewHolder;
        }
        if(inputtype == 5){
            final ViewHolder viewHolder;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View viewUser = inflater.inflate(R.layout.notifitem,parent,false);
            viewHolder = new ViewHolder(viewUser,5);
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewHolder.getAdapterPosition();
                    listener5.onClick5(pos, captions[pos], viewHolder.linearLayout);
                }
            });
            return viewHolder;
        }
        else {
            final ViewHolder viewHolder;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View viewUser = inflater.inflate(R.layout.cardfortype,parent,false);
            viewHolder = new ViewHolder(viewUser);
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewHolder.getAdapterPosition();
                    listener1.onClick1(pos, viewHolder.cardView);
                }
            });


            return viewHolder;
        }
    }

    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if (inputtype == 2) {
            holder.image.setImageResource(imageIds[position]);
            holder.name.setText(captions[position]);
        }
        if(inputtype == 5){
            holder.name.setText(captions[position]);
        }
        if (inputtype == 3 && imageIds!=null) {
            View linearLayout = holder.linearLayout;
            if(custom == 2){
                holder.lina.setBackgroundResource(R.drawable.textes_gradient);
            }
            if(custom == 3){
                holder.lina = linearLayout.findViewById(R.id.lina);
                holder.lina.setBackgroundResource(R.drawable.textes_gradient_pink);
            }
            holder.image.setImageResource(imageIds[position]);
            holder.imageview.setImageResource(imageIds[position]);
            holder.name.setText(captions[position]);
            if(isFavorite[position]){
                holder.favorite.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_baseline_star_24));
            }
            if(download[position]!=null){
                File file = new File(Environment.getExternalStorageDirectory(), download[position]+".jpg");
                Picasso.with(ctx).
                        load(file).into(holder.image);
            }


        }
        StringBuilder tex = new StringBuilder();
        if (inputtype == 4) {
            holder.name.setText(captions[position]);
            holder.pararm.setText(param[position]);
            if (custom == 2) {
                holder.pararm.setBackgroundResource(R.drawable.bodyparam);
            }
            if (custom == 3) {
                holder.pararm.setBackgroundResource(R.drawable.bodyparam_pink);
            }
            holder.editpara.setText(edittext[position]);
            holder.changes.setVisibility(View.GONE);
            if(edittext[position]!=null && change[position]!=0) {
                if (change[position] < 0) {
                    holder.changes.setTextColor(ContextCompat.getColor(holder.changes.getContext(), R.color.red));
                } else {
                    holder.changes.setTextColor(ContextCompat.getColor(holder.changes.getContext(), R.color.green));
                    tex.append("+");
                }
                if (count[position] == 1) {
                    tex.append(change[position]).append(" ").append(stringss[3]).append(" ").append(count[position]).append(" ").append(stringss[0]);
                }
                if (count[position] > 1 && count[position] < 5) {
                    tex.append(change[position]).append(" ").append(stringss[4]).append(" ").append(count[position]).append(" ").append(stringss[1]);
                }
                if (count[position] >= 5) {
                    tex.append(change[position]).append(" ").append(stringss[4]).append(" ").append(count[position]).append(" ").append(stringss[2]);
                }
                if (count[position] > 21 && count[position] < 25) {
                    tex.append(change[position]).append(" ").append(stringss[4]).append(" ").append(count[position]).append(" ").append(stringss[1]);
                }
                if(count[position]!=0) {
                    holder.changes.setVisibility(View.VISIBLE);
                    holder.changes.setText(tex);
                }
            }
            holder.editpara.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    listener3.onClick3(position, holder.editpara, holder.changes);

                }
            });

        }
        if(inputtype==3 && imageIds == null){
            holder.name.setText(captions[position]);
        }
    }
}






