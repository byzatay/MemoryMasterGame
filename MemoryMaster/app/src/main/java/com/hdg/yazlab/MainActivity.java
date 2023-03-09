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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText editEmail, editSifre;
    private String txtEmail,  txtSifre;
    private FirebaseAuth mAuth;
    private Button button, btn;
    private FirebaseFirestore db;
    private FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editEmail=(EditText) findViewById(R.id.kayit_ol_editEmail);
        editSifre=(EditText) findViewById(R.id.kayit_ol_editSifre);
        mAuth= FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        button=findViewById(R.id.girissayfasi1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,girisActivity.class);
                startActivity(i);
            }
        });

        btn=findViewById(R.id.sifreDegisimi);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,changepasswordActivity.class);
                startActivity(i);
            }
        });
    }




    public void Kayitol(View v){
        txtEmail=editEmail.getText().toString();
        txtSifre=editSifre.getText().toString();

        if(!TextUtils.isEmpty(txtEmail) && !TextUtils.isEmpty(txtSifre)){
            mAuth.createUserWithEmailAndPassword(txtEmail,txtSifre)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Kayıt İşlemi Başarılı", Toast.LENGTH_SHORT).show();
                                user = task.getResult().getUser();
                                Map<String, Object> userData = new HashMap<>();
                                userData.put("email",user.getEmail());
                                userData.put("ID", user.getUid());
                                db.collection("users").document(user.getUid()).set(userData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(MainActivity.this, "Veriler başarıyla yüklendi.", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(MainActivity.this, "Veriler işlenirken hata oluştu.", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                            else{
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



        }else{
            Toast.makeText(this,"Email ve Şifre boş olamaz.", Toast.LENGTH_SHORT).show();
        }

    }
}