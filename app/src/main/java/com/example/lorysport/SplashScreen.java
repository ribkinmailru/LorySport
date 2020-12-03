package com.example.lorysport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import io.realm.Realm;

public class SplashScreen extends AppCompatActivity {
    FirebaseFirestore fire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        fire = FirebaseFirestore.getInstance();
        Realm.init(getBaseContext());
        try {
            fire.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot s = task.getResult();
                        AppManager.user = s.toObject(User.class);
                        change(true);
                } else {
                    change(false);
                    }
                }
            });
        } catch (NullPointerException ex) {
            change(false);
        }
    }

    public void change(boolean what){
        Intent intent;
        if(what){
            intent = new Intent(this, MainActivity2.class);
        }else{
            intent = new Intent(this, RegandLoginactivity.class);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fire = null;
    }
}