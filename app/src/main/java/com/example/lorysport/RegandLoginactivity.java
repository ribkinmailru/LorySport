package com.example.lorysport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;

public class RegandLoginactivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private LinearLayout login, regs, codelay;
    private FirebaseFirestore fire;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String numbers,names;
    private CountryCodePicker rpicker, rpicker2;
    private String varify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_regand_loginactivity);
        final EditText phone = findViewById(R.id.email);
        final EditText name = findViewById(R.id.name);
        final EditText rphone = findViewById(R.id.numberphone);
        regs = findViewById(R.id.regestration);
        regs.setVisibility(View.GONE);
        codelay = findViewById(R.id.indentific);
        codelay.setVisibility(View.GONE);
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
        rpicker2 = findViewById(R.id.ccp2);
        rpicker2.registerCarrierNumberEditText(phone);
        final EditText editcode = findViewById(R.id.code);

        final Button reg, log, accept;
        login = findViewById(R.id.login);
        reg = findViewById(R.id.reg);
        fire = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        log = findViewById(R.id.log);
        accept = findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varifidecode(editcode.getText().toString());
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numbers = rpicker2.getFullNumberWithPlus();
                boolean validphone = rpicker.isValidFullNumber();
                if(validphone) {
                    verifyuser(numbers);
                    changeelement(3);
                }else{
                    Toast.makeText(getApplicationContext(), "Ты ебалн?", Toast.LENGTH_SHORT).show();

                }

            }
        });



        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names = name.getText().toString();
                numbers = rpicker.getFullNumberWithPlus();
                boolean validphone = rpicker.isValidFullNumber();
                if(validphone) {
                    verifyuser(numbers);
                    changeelement(3);
                }else{
                    Toast.makeText(getApplicationContext(), "Ты ебалн?", Toast.LENGTH_SHORT).show();

                }

            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if(code!=null){
                    varifidecode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getApplicationContext(), "Слишком много попыток попробуйте позже", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                varify = s;
            }
        };

    }

    public void changeelement(int e) {
        if (e == 1) {
            login.setVisibility(View.GONE);
            codelay.setVisibility(View.GONE);
            regs.setVisibility(View.VISIBLE);
        } else if (e == 2) {
            regs.setVisibility(View.GONE);
            codelay.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        }else{
            login.setVisibility(View.GONE);
            regs.setVisibility(View.GONE);
            codelay.setVisibility(View.VISIBLE);
        }
    }

    public void change(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void Writenewuser(String name, String number, String uid) {
        final User user = new User();
        user.name = AppManager.firstUpperCase(name);
        user.number = number;
        user.status = getString(R.string.status);
        HashMap<String, Object> us = new HashMap<>();
        us.put("status", user.status);
        us.put("name", user.name);
        us.put("number", user.number);
        fire.collection("users").document(uid).set(us)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        AppManager.user = user;
                        change();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void varifidecode(String code){
        PhoneAuthCredential credental = PhoneAuthProvider.getCredential(varify, code);
        signInWithPhoneAuthCredential(credental);
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
                            DocumentReference query = fire.collection("users").document(user.getUid());
                            query.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            AppManager.user = document.toObject(User.class);
                                            change();
                                        } else {
                                            Writenewuser(names, user.getPhoneNumber(), user.getUid());
                                        }
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(), R.string.notrightcode, Toast.LENGTH_SHORT).show();
                        }
                    }});}



}