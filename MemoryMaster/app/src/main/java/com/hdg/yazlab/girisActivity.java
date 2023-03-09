package com.hdg.yazlab;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;



public class girisActivity extends AppCompatActivity {
    private EditText editEmail, editSifre;
    private String txtEmail,  txtSifre;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Button button1;
    private FirebaseFirestore db;
    public CollectionReference collection;
    private Query query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editEmail=(EditText) findViewById(R.id.giris_yap_editEmail);
        editSifre=(EditText) findViewById(R.id.giris_yap_editSifre);

        mAuth= FirebaseAuth.getInstance();
        mUser=FirebaseAuth.getInstance().getCurrentUser();
        button1=findViewById(R.id.choiceEkran);
        db= FirebaseFirestore.getInstance();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Kullanıcı Giriş Ekranı");
        }
    }

    public void girisYap(View v){

        txtEmail=editEmail.getText().toString();
        txtSifre=editSifre.getText().toString();
        collection = db.collection("users");

        if(!TextUtils.isEmpty(txtEmail)&& !TextUtils.isEmpty(txtSifre)){
            query= collection.whereEqualTo("email",txtEmail);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        QuerySnapshot querySnapshot= task.getResult();
                        if(querySnapshot.size()>0){
                            button1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i=new Intent(girisActivity.this,choiceActivity.class);
                                    startActivity(i);
                                }
                            });
                        }
                        else{
                            Toast.makeText(girisActivity.this,"Giriş yapamadınız lütfen kayıt yapınız.", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(this,"Email ve Şifre boş olamaz.", Toast.LENGTH_SHORT).show();
        }



    }
}