package com.example.lorysport;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.concurrent.CountDownLatch;

import io.realm.Realm;
import io.realm.RealmResults;

public class CustomDialogFragment extends DialogFragment implements View.OnClickListener {
    MainActivity2 activity;
    final int ids;
    String bs;
    AlertDialog dialogs;
    methods met1;
    int pose;
    Realm realm;


    public interface methods{
        void onOk(int pose);
    }

    public void setlist(methods met1){
        this.met1 = met1;
    }

    public CustomDialogFragment(int ids, String bs, int pose) {
        this.ids = ids;
        this.bs = bs;
        this.pose = pose;
    }

    public CustomDialogFragment(int ids) {
        this.ids = ids;
    }




    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final View v1 = View.inflate(getActivity(), R.layout.dialog, null);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                .setView(v1);
        dialogs = dialog.create();
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnReopenId = v1.findViewById(R.id.button2);
        Button btnCancelId = v1.findViewById(R.id.button4);
        ImageView image = v1.findViewById(R.id.im);
        image.setImageResource(R.drawable.ic_baseline_warning_24);
        btnReopenId.setOnClickListener(this);
        btnCancelId.setOnClickListener(this);
        activity = ((MainActivity2) getActivity());
        realm = Realm.getDefaultInstance();
        return dialogs;
    }


    @Override
    public void onClick(View v) {
        if (ids == 1) {
            switch (v.getId()) {
                case R.id.button4:
                    long millis = AppManager.getmillis();
                    realm.beginTransaction();
                    final RealmResults<Train> query = realm.where(Train.class).equalTo("time", millis).findAll();
                    query.deleteAllFromRealm();
                    activity.changefragment(4);
                    dialogs.dismiss();
                    break;
                case R.id.button2:
                    dialogs.dismiss();
                    break;
            }
        }
        if (ids == 2) {
            switch (v.getId()) {
                case R.id.button4:
                    SQLiteOpenHelper exe = new ExeDatabase(getContext());
                    SQLiteDatabase db = exe.getWritableDatabase();
                    db.delete("EXE", "NAME=?", new String[]{bs});
                    db.close();
                    dialogs.dismiss();
                    met1.onOk(pose);
                    break;
                case R.id.button2:
                    dialogs.dismiss();
                    break;
            }

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dialogs = null;
        activity = null;
    }
}
