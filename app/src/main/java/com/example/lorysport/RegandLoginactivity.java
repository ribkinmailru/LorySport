package com.example.lorysport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Any;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;

public class RegandLoginactivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private LinearLayout login;
    private DatabaseReference mData;
    private FirebaseFirestore fire;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private LinearLayout regs;
    private LinearLayout logs;
    private String numbers,names;
    private CountryCodePicker rpicker;
    private String mVerificationId;
    private String mCode;
    private SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_regand_loginactivity);
        final EditText phone = findViewById(R.id.email);
        final EditText name = findViewById(R.id.name);
        final EditText rphone = findViewById(R.id.numberphone);
        regs = findViewById(R.id.regestration);
        logs = findViewById(R.id.login);
        regs.setVisibility(View.GONE);
        TextView zareg = findViewById(R.id.zareg);
        TextView haveacc = findViewById(R.id.textView14);
        haveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeelement(2);
            }
        });
        zareg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeelement(1);
            }
        });
        rpicker = findViewById(R.id.ccp);
        rpicker.registerCarrierNumberEditText(rphone);

        final Button reg, log, accept;
        login = findViewById(R.id.login);
        log = findViewById(R.id.log);
        reg = findViewById(R.id.reg);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null) {
            change();
        }



        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names = name.getText().toString();
                numbers = rpicker.getFullNumberWithPlus();
                boolean validphone = rpicker.isValidFullNumber();
                if(validphone) {
                    Log.d("Работает", "Rabotaet");
                    verifyuser(numbers);
                }else{
                    Toast.makeText(getApplicationContext(), "Ты ебалн?", Toast.LENGTH_SHORT).show();

                }

            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                mCode = phoneAuthCredential.getSmsCode();
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getApplicationContext(), "Слишком много попыток попробуйте позже", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerificationId = s;
            }
        };

    }

    public void changeelement(int e){
        if(e==1){
            login.setVisibility(View.GONE);
            regs.setVisibility(View.VISIBLE);
        }else if(e==2){
            regs.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        fire = FirebaseFirestore.getInstance();
    }

    public void change(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void Writenewuser(String name, String number) {
        User user = new User(name, number);
        HashMap<String, Object> us = new HashMap();
        user.contacts.add("Лохпидр");
        us.put("name", user.name);
        us.put("number", user.number);
        us.put("contacts", user.contacts);
        fire.collection("users").document(mAuth.getCurrentUser().getUid()).set(us)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


        public void verifyuser(String phoneNumber){
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks

        }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser user = task.getResult().getUser();
                            DocumentReference query = fire.collection("users").document(mAuth.getCurrentUser().getUid());
                            query.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if(document.exists()){
                                            change();
                                        }else{
                                            Writenewuser(names, user.getPhoneNumber());
                                            change();
                                        }
                                        sPref = getPreferences(MODE_PRIVATE);
                                        SharedPreferences.Editor ed = sPref.edit();
                                        ed.putString("mVar", mVerificationId);
                                        ed.putString("kode", mCode);
                                        ed.apply();

                                    }
                                }
                            });

                        }
                        else {
                            Log.d("tag", "Ті пидор?");
                        }
                    }});}



}