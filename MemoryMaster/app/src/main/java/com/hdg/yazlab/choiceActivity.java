package com.hdg.yazlab;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;


public class choiceActivity extends AppCompatActivity {
    private CheckBox cb1 , cb2,cb3, cb4,cb5;
    int a1,a2,a3,a4,a5;
    Button btn2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Seçim Ekranı");




        btn2=findViewById(R.id.oyunEkrani);
        a1=0;
        a2=0;
        a3=0;
        a4=0;
        a5=0;


        cb1=(CheckBox) findViewById(R.id.tekOyuncu);
        cb2=(CheckBox)findViewById(R.id.ikiOyuncu);
        cb3=(CheckBox)findViewById(R.id.ikilikMatris);
        cb4=(CheckBox)findViewById(R.id.dortlukMatris);
        cb5=(CheckBox)findViewById(R.id.altilikMatris);


        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    a1=1;
                }

            }
        });
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    a2=1;
                }

            }
        });

        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    a3=1;
                }

            }
        });

        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    a4=1;
                }

            }
        });

        cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    a5=1;
                }

            }
        });
    }


    public  void seciminiYap(View view){
        if(a1==1){
            if(a3==1){
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(choiceActivity.this, gameActivity2.class);
                        startActivity(i);
                    }
                });

            }
            else if(a4==1){
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(choiceActivity.this,gameActivity4.class);
                        startActivity(i);
                    }
                });
            }
            else{
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(choiceActivity.this,gameActivity6.class);
                        startActivity(i);
                    }
                });
            }
        }
        else if(a2==1){
            if(a3==1){
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(choiceActivity.this, gameActivity22.class);
                        startActivity(i);
                    }
                });

            }
            else if(a4==1){
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(choiceActivity.this,gameActivity44.class);
                        startActivity(i);
                    }
                });
            }
            else{
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(choiceActivity.this,gameActivity66.class);
                        startActivity(i);
                    }
                });
            }
        }
    }
}