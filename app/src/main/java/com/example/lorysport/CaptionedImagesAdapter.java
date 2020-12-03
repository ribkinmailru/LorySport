package com.example.lorysport;


import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {
    private String[] captions;
    private TypedArray imageIds;
    private int[] images;
    private Listener1 listener1;




    public void changeDatas(String[] captions){
        this.captions = captions;
        notifyDataSetChanged();
    }


    public interface Listener1 {
        void onClick1(int position, View cardView);
    }

    public void setListener1(Listener1 listener1) {
        this.listener1 = listener1;
    }

    public CaptionedImagesAdapter(String[] captions, TypedArray imageIds, int[] images) {
        this.captions = captions;
        this.imageIds = imageIds;
        this.images = images;
    }
    public CaptionedImagesAdapter(String[] captions){
        this.captions = captions;
    }

    public int getItemCount() {
        return captions.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View cardView;
        ImageView image;
        TextView name;


        public ViewHolder(View ll) {
            super(ll);
           cardView = ll;
           image = ll.findViewById(R.id.splash_imageview);
           name = ll.findViewById(R.id.type);
        }

    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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


    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if(images==null) {
            holder.image.setImageDrawable(imageIds.getDrawable(position));
        }else{
            holder.image.setImageResource(images[position]);
        }
            holder.name.setText(captions[position]);
    }

}






