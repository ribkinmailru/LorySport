package com.example.lorysport;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;

import io.realm.RealmList;

public class BodyAdapter extends RecyclerView.Adapter<BodyAdapter.ViewHolder> {
    private RealmList<Double> firstparams;
    private double[] lastparams;
    private String[] params;
    private String[] names;
    private Listener1 listener1;
    private String s;
    private Context context;
    DecimalFormat format;

    public interface Listener1{
        void onClick1(int position, EditText edit, TextView text);
    }

    public void setListener1(BodyAdapter.Listener1 listener1) {
        this.listener1 = listener1;
    }

    public BodyAdapter(RealmList<Double> firstparams, double[] lastparams, String[] names, String[] params, Context context) {
        this.lastparams = lastparams;
        this.names = names;
        this.params = params;
        this.firstparams = firstparams;
        this.context = context;
        format = new DecimalFormat("#0.00");
    }


    @NonNull
    @Override
    public BodyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.bodyrecycler,parent,false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, changes, pararm;
        EditText editpara;


        public ViewHolder(@NonNull View ll) {
            super(ll);
            name = ll.findViewById(R.id.textView10);
            pararm = ll.findViewById(R.id.textView11);
            changes = ll.findViewById(R.id.textView4);
            editpara = ll.findViewById(R.id.editpara);
            changes.setVisibility(View.GONE);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull final BodyAdapter.ViewHolder holder, final int position) {
        holder.name.setText(names[position]);
        holder.pararm.setText(params[position]);
            if(firstparams.get(position) - lastparams[position]>0) {
                holder.changes.setTextColor(context.getResources().getColor(R.color.green));
                s ="+ "+ format.format(firstparams.get(position) - lastparams[position])
                        + " " + context.getString(R.string.last1) + " " + AppManager.form.format(firstparams.get(position) - lastparams[position]);
            }
            else{
                holder.changes.setTextColor(context.getResources().getColor(R.color.red));
                s =format.format(firstparams.get(position) - lastparams[position])
                        + " " + context.getString(R.string.last1) + " " + AppManager.form.format(firstparams.get(position) - lastparams[position]);
            }
            if(firstparams.get(position)!=0) {
                holder.editpara.setText(Double.toString(firstparams.get(position)));
                holder.changes.setVisibility(View.VISIBLE);
                holder.changes.setText(s);
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
                listener1.onClick1(position, holder.editpara, holder.changes);
            }
        });
    }

    @Override
    public int getItemCount() {
        return params.length;
    }
}
