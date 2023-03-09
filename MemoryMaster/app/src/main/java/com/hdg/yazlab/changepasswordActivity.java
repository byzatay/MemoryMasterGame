package com.hdg.yazlab;

import android.content.Intent;
import android.os.Bundle;
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

public class changepasswordActivity extends AppCompatActivity {
    private EditText editEmail, editSifre, editSifre2;
    private String txtEmail,  txtSifre, txtsifre2;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Button button1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void sifreDegisimi(View view){
        editEmail=(EditText) findViewById(R.id.email1);
        editSifre=(EditText) findViewById(R.id.password1);
        editSifre2=(EditText) findViewById(R.id.newpassword);


        mAuth= FirebaseAuth.getInstance();
        mUser=FirebaseAuth.getInstance().getCurrentUser();
        button1=(Button)findViewById(R.id.changeP);

        txtEmail=editEmail.getText().toString();
        txtSifre=editSifre.getText().toString();
        txtsifre2=editSifre2.getText().toString();

        if(mUser.getEmail()==(txtEmail)){
            mUser.updatePassword(txtsifre2).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(changepasswordActivity.this,"Şifre başarıyla değiştirildi.", Toast.LENGTH_SHORT).show();
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i=new Intent(changepasswordActivity.this,girisActivity.class);
                                startActivity(i);
                            }
                        });
                    }
                    else{
                        Toast.makeText(changepasswordActivity.this,"Şifre değiştiremediniz.1 ", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else{
            Toast.makeText(changepasswordActivity.this,"Şifre değiştiremediniz.", Toast.LENGTH_SHORT).show();
        }


    }
}