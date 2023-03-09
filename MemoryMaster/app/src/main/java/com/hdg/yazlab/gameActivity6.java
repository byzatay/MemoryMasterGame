package com.hdg.yazlab;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class gameActivity6 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFiresore;
    private DocumentReference docReference;

    private TextView sayac,skor;
    private ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15,i16,i17,i18,i19,i20,i21,i22
            ,i23,i24,i25,i26,i27,i28,i29,i30,i31,i32,i33,i34,i35,i36;
    private CountDownTimer countDownTimer;

    private Switch switchMuzik;
    private MediaPlayer mediaPlayer,mediaPlayer2,mediaPlayer3,mediaPlayer4;

    private int[] line =new int[36];
    private int[] matched = new int [36];
    private int[] cards = new int [44];

    private int firstCard,secondCard;
    private int firstCardId,secondCardId;
    private int clickedFirst,clickedSecond;
    private int cardNumber=1;
    private long puan=0;
    private int cardPoint;
    private int count=0;
    private long time;
    private int count2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game6);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFiresore = FirebaseFirestore.getInstance();

        sayac = (TextView)findViewById(R.id.textView3);
        skor = (TextView)findViewById(R.id.textView4);
        skor.setText("Skor: "+puan);

        switchMuzik=(Switch) findViewById(R.id.switch1);
        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.prologue);
        mediaPlayer2=MediaPlayer.create(getApplicationContext(),R.raw.victory);
        mediaPlayer3=MediaPlayer.create(getApplicationContext(),R.raw.congratulations);
        mediaPlayer4=MediaPlayer.create(getApplicationContext(),R.raw.shocked);

        switchMuzik.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mediaPlayer.start();
                }
                else{
                    mediaPlayer.pause();
                }

            }
        });

        i1=(ImageView)findViewById(R.id.iv1);
        i2=(ImageView)findViewById(R.id.iv2);
        i3=(ImageView)findViewById(R.id.iv3);
        i4=(ImageView)findViewById(R.id.iv4);
        i5=(ImageView)findViewById(R.id.iv5);
        i6=(ImageView)findViewById(R.id.iv6);
        i7=(ImageView)findViewById(R.id.iv7);
        i8=(ImageView)findViewById(R.id.iv8);
        i9=(ImageView)findViewById(R.id.iv9);
        i10=(ImageView)findViewById(R.id.iv10);
        i11=(ImageView)findViewById(R.id.iv11);
        i12=(ImageView)findViewById(R.id.iv12);
        i13=(ImageView)findViewById(R.id.iv13);
        i14=(ImageView)findViewById(R.id.iv14);
        i15=(ImageView)findViewById(R.id.iv15);
        i16=(ImageView)findViewById(R.id.iv16);
        i17=(ImageView)findViewById(R.id.iv17);
        i18=(ImageView)findViewById(R.id.iv18);
        i19=(ImageView)findViewById(R.id.iv19);
        i20=(ImageView)findViewById(R.id.iv20);
        i21=(ImageView)findViewById(R.id.iv21);
        i22=(ImageView)findViewById(R.id.iv22);
        i23=(ImageView)findViewById(R.id.iv23);
        i24=(ImageView)findViewById(R.id.iv24);
        i25=(ImageView)findViewById(R.id.iv25);
        i26=(ImageView)findViewById(R.id.iv26);
        i27=(ImageView)findViewById(R.id.iv27);
        i28=(ImageView)findViewById(R.id.iv28);
        i29=(ImageView)findViewById(R.id.iv29);
        i30=(ImageView)findViewById(R.id.iv30);
        i31=(ImageView)findViewById(R.id.iv31);
        i32=(ImageView)findViewById(R.id.iv32);
        i33=(ImageView)findViewById(R.id.iv33);
        i34=(ImageView)findViewById(R.id.iv34);
        i35=(ImageView)findViewById(R.id.iv35);
        i36=(ImageView)findViewById(R.id.iv36);

        cards[0]=20;cards[1]=12;cards[2]=13;cards[3]=10;cards[4]=18;cards[5]=12;cards[6]=10;
        cards[7]=5;cards[8]=10;cards[9]=8;cards[10]=10;cards[11]=20;cards[12]=12;cards[13]=13;
        cards[14]=10;cards[15]=16;cards[16]=12;cards[17]=10;cards[18]=5;cards[19]=10;cards[20]=18;
        cards[21]=10;cards[22]=20;cards[23]=9;cards[24]=13;cards[25]=10;cards[26]=11;cards[27]=14;
        cards[28]=10;cards[29]=5;cards[30]=10;cards[31]=15;cards[32]=15;cards[33]=20;cards[34]=18;
        cards[35]=14;cards[36]=10;cards[37]=18;cards[38]=12;cards[39]=10;cards[40]=5;cards[41]=10;
        cards[42]=12;cards[43]=10;

        for(int i=0;i<matched.length;i++)
            matched[i]=-1;

        i1.setTag("0");
        i2.setTag("1");
        i3.setTag("2");
        i4.setTag("3");
        i5.setTag("4");
        i6.setTag("5");
        i7.setTag("6");
        i8.setTag("7");
        i9.setTag("8");
        i10.setTag("9");
        i11.setTag("10");
        i12.setTag("11");
        i13.setTag("12");
        i14.setTag("13");
        i15.setTag("14");
        i16.setTag("15");
        i17.setTag("16");
        i18.setTag("17");
        i19.setTag("18");
        i20.setTag("19");
        i21.setTag("20");
        i22.setTag("21");
        i23.setTag("22");
        i24.setTag("23");
        i25.setTag("24");
        i26.setTag("25");
        i27.setTag("26");
        i28.setTag("27");
        i29.setTag("28");
        i30.setTag("29");
        i31.setTag("30");
        i32.setTag("31");
        i33.setTag("32");
        i34.setTag("33");
        i35.setTag("34");
        i36.setTag("35");

        Random r = new Random();

        int num1 = r.nextInt(4) + 1;
        int num2 = r.nextInt(4) + 1;
        while (num1 == num2) {
            num2 = r.nextInt(4) + 1;
        }
        int num3 = r.nextInt(11) + 1;
        while (num3 == num1 || num3 == num2) {
            num3 = r.nextInt(11) + 1;
        }
        int num4 = r.nextInt(11) + 1;
        while (num4 == num1 || num4 == num2 || num4 == num3) {
            num4 = r.nextInt(11) + 1;
        }

        line[0]=num1;line[1]=num1;
        line[2]=num2;line[3]=num2;
        line[4]=num3;line[5]=num3;
        line[6]=num4;line[7]=num4;

        num1 = r.nextInt(11) + 12;
        num2 = r.nextInt(11) + 12;
        while (num1 == num2) {
            num2 = r.nextInt(11) + 12;
        }
        num3 = r.nextInt(11) + 12;
        while (num3 == num1 || num3 == num2) {
            num3 = r.nextInt(11) + 12;
        }
        num4 = r.nextInt(11) + 12;
        while (num4 == num1 || num4 == num2 || num4 == num3) {
            num4 = r.nextInt(11) + 12;
        }

        line[8]=num1;line[9]=num1;
        line[10]=num2;line[11]=num2;
        line[12]=num3;line[13]=num3;
        line[14]=num4;line[15]=num4;

        num1 = r.nextInt(11) + 23;
        num2 = r.nextInt(11) + 23;
        while (num1 == num2) {
            num2 = r.nextInt(11) + 23;
        }num3 = r.nextInt(11) + 23;
        while (num3 == num1 || num3 == num2) {
            num3 = r.nextInt(11) + 23;
        }
        num4 = r.nextInt(11) + 23;
        while (num4 == num1 || num4 == num2 || num4 == num3) {
            num4 = r.nextInt(11) + 23;
        }
        int num5 = r.nextInt(11) + 23;
        while (num5 == num1 || num5 == num2 || num5 == num3 || num5 == num4) {
            num5 = r.nextInt(11) + 23;
        }

        line[16]=num1;line[17]=num1;
        line[18]=num2;line[19]=num2;
        line[20]=num3;line[21]=num3;
        line[22]=num4;line[23]=num4;
        line[24]=num5;line[25]=num5;

        num1 = r.nextInt(11) + 34;
        num2 = r.nextInt(11) + 34;
        while (num1 == num2) {
            num2 = r.nextInt(11) + 34;
        }
        num3 = r.nextInt(11) + 34;
        while (num3 == num1 || num3 == num2) {
            num3 = r.nextInt(11) + 34;
        }
        num4 = r.nextInt(11) + 34;
        while (num4 == num1 || num4 == num2 || num4 == num3) {
            num4 = r.nextInt(11) + 34;
        }
        num5 = r.nextInt(11) + 34;
        while (num5 == num1 || num5 == num2 || num5 == num3 || num5 == num4) {
            num5 = r.nextInt(11) + 34;
        }

        line[26]=num1;line[27]=num1;
        line[28]=num2;line[29]=num2;
        line[30]=num3;line[31]=num3;
        line[32]=num4;line[33]=num4;
        line[34]=num5;line[35]=num5;

        for (int i = line.length - 1; i > 0; i--)
        {
            int index = r.nextInt(i + 1);
            int a = line[index];
            line[index] = line[i];
            line[i] = a;
        }
        setBackground();
        cevapAnahtari();

        // 1 saniye 1000
        countDownTimer= new CountDownTimer(46000,1000){
            @Override
            public void onTick(long l){
                time=l/1000;
                sayac.setText("Süre: "+(l/1000));

                if(count==36){
                    mediaPlayer3.start();
                    countDownTimer.cancel();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(gameActivity6.this);
                    alertDialogBuilder
                            .setMessage("Oyun Bitti..\nSkor: "+puan)
                            .setCancelable(false)
                            .setNegativeButton("Onayla", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(getApplicationContext(),choiceActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
            @Override
            public void onFinish(){
                mediaPlayer4.start();
                sayac.setText("Süre: 0");
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(gameActivity6.this);
                alertDialogBuilder
                        .setMessage("Süre Bitti..\nSkor: "+puan)
                        .setCancelable(false)
                        .setNegativeButton("Onayla", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(),choiceActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }.start();

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i1,theCard);
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i2,theCard);
            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i3,theCard);
            }
        });
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i4,theCard);
            }
        });
        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i5,theCard);
            }
        });
        i6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i6,theCard);
            }
        });
        i7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i7,theCard);
            }
        });
        i8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i8,theCard);
            }
        });
        i9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i9,theCard);
            }
        });
        i10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i10,theCard);
            }
        });
        i11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i11,theCard);
            }
        });
        i12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i12,theCard);
            }
        });
        i13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i13,theCard);
            }
        });
        i14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i14,theCard);
            }
        });
        i15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i15,theCard);
            }
        });
        i16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i16,theCard);
            }
        });
        i17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i17,theCard);
            }
        });
        i18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i18,theCard);
            }
        });
        i19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i19,theCard);
            }
        });
        i20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i20,theCard);
            }
        });
        i21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i21,theCard);
            }
        });
        i22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i22,theCard);
            }
        });
        i23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i23,theCard);
            }
        });
        i24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i24,theCard);
            }
        });
        i25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i25,theCard);
            }
        });
        i26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i26,theCard);
            }
        });
        i27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i27,theCard);
            }
        });
        i28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i28,theCard);
            }
        });
        i29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i29,theCard);
            }
        });
        i30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i30,theCard);
            }
        });
        i31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i31,theCard);
            }
        });
        i32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i32,theCard);
            }
        });
        i33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i33,theCard);
            }
        });
        i34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i34,theCard);
            }
        });
        i35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i35,theCard);
            }
        });
        i36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                control(i36,theCard);
            }
        });
    }

    private void control(ImageView iv,int card){
        if(cardNumber==1)
        {
            firstCard=line[card];
            firstCardId=card;
            setFront(line[card],card);
            cardNumber=2;
            iv.setEnabled(false);
        }else if(cardNumber==2){
            secondCard=line[card];
            secondCardId=card;
            setFront(line[card],card);
            cardNumber=1;
            clickedSecond = card;

            i1.setEnabled(false);
            i2.setEnabled(false);
            i3.setEnabled(false);
            i4.setEnabled(false);
            i5.setEnabled(false);
            i6.setEnabled(false);
            i7.setEnabled(false);
            i8.setEnabled(false);
            i9.setEnabled(false);
            i10.setEnabled(false);
            i11.setEnabled(false);
            i12.setEnabled(false);
            i13.setEnabled(false);
            i14.setEnabled(false);
            i15.setEnabled(false);
            i16.setEnabled(false);
            i17.setEnabled(false);
            i18.setEnabled(false);
            i19.setEnabled(false);
            i20.setEnabled(false);
            i21.setEnabled(false);
            i22.setEnabled(false);
            i23.setEnabled(false);
            i24.setEnabled(false);
            i25.setEnabled(false);
            i26.setEnabled(false);
            i27.setEnabled(false);
            i28.setEnabled(false);
            i29.setEnabled(false);
            i30.setEnabled(false);
            i31.setEnabled(false);
            i32.setEnabled(false);
            i33.setEnabled(false);
            i34.setEnabled(false);
            i35.setEnabled(false);
            i36.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    compare(card);
                }
            },1000);
        }
    }

    private boolean contains(int aranan){
        for (int deger : matched) {
            if (deger == aranan) {
                return true;
            }
        }
        return false;
    }

    private void compare (int card){
        if(firstCard==secondCard){
            mediaPlayer2.start();
            matched[count]=firstCardId;matched[count+1]=secondCardId;
            count+=2;

            int pn=cards[firstCard-1];
            int ev=1;
            if(firstCard<=11)
                ev=2;
            else if(firstCard<=22)
                ev=2;
            int temp=2*pn;
            puan+=((long) temp *ev)*(time/10);
            skor.setText("Skor: "+puan);
        }else{
            int durum;
            int ev=1;
            int ev2=1;

            if(firstCard<=11 && firstCard>=1 && secondCard<=11 && secondCard>=1){
                durum=1;
                ev=2;
            }
            else if(firstCard<=22 && firstCard>11 && secondCard<=22 && secondCard>11){
                durum=1;
                ev=2;
            }
            else if(firstCard<=33 && firstCard>22 && secondCard<=33 && secondCard>22)
                durum=1;
            else if(firstCard<=44 && firstCard>33 && secondCard<=44 && secondCard>33)
                durum=1;
            else
                durum=0;

            // Evleri aynı
            if(durum==1){
                int kartPuani,kartPuani2;
                kartPuani=cards[firstCard-1];
                kartPuani2=cards[secondCard-1];
                puan-=((kartPuani+kartPuani2)/ev)*((45-time)/10);
            }else{
                if(firstCard<=11)
                    ev=2;
                else if(firstCard<=22)
                    ev=2;

                if(secondCard<=11)
                    ev2=2;
                else if(secondCard<=22)
                    ev2=2;

                int kartPuani,kartPuani2;
                kartPuani=cards[firstCard-1];
                kartPuani2=cards[secondCard-1];
                puan-=(((kartPuani+kartPuani2)/2)*(ev*ev2))*((45-time)/10);
            }
            skor.setText("Skor: "+puan);

            if(!contains(0))
                setFront(0,0);
            if(!contains(1))
                setFront(0,1);
            if(!contains(2))
                setFront(0,2);
            if(!contains(3))
                setFront(0,3);
            if(!contains(4))
                setFront(0,4);
            if(!contains(5))
                setFront(0,5);
            if(!contains(6))
                setFront(0,6);
            if(!contains(7))
                setFront(0,7);
            if(!contains(8))
                setFront(0,8);
            if(!contains(9))
                setFront(0,9);
            if(!contains(10))
                setFront(0,10);
            if(!contains(11))
                setFront(0,11);
            if(!contains(12))
                setFront(0,12);
            if(!contains(13))
                setFront(0,13);
            if(!contains(14))
                setFront(0,14);
            if(!contains(15))
                setFront(0,15);
            if(!contains(16))
                setFront(0,16);
            if(!contains(17))
                setFront(0,17);
            if(!contains(18))
                setFront(0,18);
            if(!contains(19))
                setFront(0,19);
            if(!contains(20))
                setFront(0,20);
            if(!contains(21))
                setFront(0,21);
            if(!contains(22))
                setFront(0,22);
            if(!contains(23))
                setFront(0,23);
            if(!contains(24))
                setFront(0,24);
            if(!contains(25))
                setFront(0,25);
            if(!contains(26))
                setFront(0,26);
            if(!contains(27))
                setFront(0,27);
            if(!contains(28))
                setFront(0,28);
            if(!contains(29))
                setFront(0,29);
            if(!contains(30))
                setFront(0,30);
            if(!contains(31))
                setFront(0,31);
            if(!contains(32))
                setFront(0,32);
            if(!contains(33))
                setFront(0,33);
            if(!contains(34))
                setFront(0,34);
            if(!contains(35))
                setFront(0,35);
        }

        i1.setEnabled(true);
        i2.setEnabled(true);
        i3.setEnabled(true);
        i4.setEnabled(true);
        i5.setEnabled(true);
        i6.setEnabled(true);
        i7.setEnabled(true);
        i8.setEnabled(true);
        i9.setEnabled(true);
        i10.setEnabled(true);
        i11.setEnabled(true);
        i12.setEnabled(true);
        i13.setEnabled(true);
        i14.setEnabled(true);
        i15.setEnabled(true);
        i16.setEnabled(true);
        i17.setEnabled(true);
        i18.setEnabled(true);
        i19.setEnabled(true);
        i20.setEnabled(true);
        i21.setEnabled(true);
        i22.setEnabled(true);
        i23.setEnabled(true);
        i24.setEnabled(true);
        i25.setEnabled(true);
        i26.setEnabled(true);
        i27.setEnabled(true);
        i28.setEnabled(true);
        i29.setEnabled(true);
        i30.setEnabled(true);
        i31.setEnabled(true);
        i32.setEnabled(true);
        i33.setEnabled(true);
        i34.setEnabled(true);
        i35.setEnabled(true);
        i36.setEnabled(true);
    }

    private void setFront(int line,int i){

        String s = String.valueOf(line);
        docReference = mFiresore.collection("Cards").document(s);
        docReference.get()
                .addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String sImage = (String)documentSnapshot.getData().get("Resmi");
                            byte[] bytes= Base64.decode(sImage,Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                            if(i==0)
                                i1.setImageBitmap(bitmap);
                            if(i ==1)
                                i2.setImageBitmap(bitmap);
                            if(i==2)
                                i3.setImageBitmap(bitmap);
                            if(i==3)
                                i4.setImageBitmap(bitmap);
                            if(i==4)
                                i5.setImageBitmap(bitmap);
                            if(i ==5)
                                i6.setImageBitmap(bitmap);
                            if(i==6)
                                i7.setImageBitmap(bitmap);
                            if(i==7)
                                i8.setImageBitmap(bitmap);
                            if(i==8)
                                i9.setImageBitmap(bitmap);
                            if(i ==9)
                                i10.setImageBitmap(bitmap);
                            if(i==10)
                                i11.setImageBitmap(bitmap);
                            if(i==11)
                                i12.setImageBitmap(bitmap);
                            if(i==12)
                                i13.setImageBitmap(bitmap);
                            if(i ==13)
                                i14.setImageBitmap(bitmap);
                            if(i==14)
                                i15.setImageBitmap(bitmap);
                            if(i==15)
                                i16.setImageBitmap(bitmap);
                            if(i==16)
                                i17.setImageBitmap(bitmap);
                            if(i ==17)
                                i18.setImageBitmap(bitmap);
                            if(i==18)
                                i19.setImageBitmap(bitmap);
                            if(i==19)
                                i20.setImageBitmap(bitmap);
                            if(i==20)
                                i21.setImageBitmap(bitmap);
                            if(i ==21)
                                i22.setImageBitmap(bitmap);
                            if(i==22)
                                i23.setImageBitmap(bitmap);
                            if(i==23)
                                i24.setImageBitmap(bitmap);
                            if(i==24)
                                i25.setImageBitmap(bitmap);
                            if(i ==25)
                                i26.setImageBitmap(bitmap);
                            if(i==26)
                                i27.setImageBitmap(bitmap);
                            if(i==27)
                                i28.setImageBitmap(bitmap);
                            if(i==28)
                                i29.setImageBitmap(bitmap);
                            if(i ==29)
                                i30.setImageBitmap(bitmap);
                            if(i==30)
                                i31.setImageBitmap(bitmap);
                            if(i==31)
                                i32.setImageBitmap(bitmap);
                            if(i==32)
                                i33.setImageBitmap(bitmap);
                            if(i ==33)
                                i34.setImageBitmap(bitmap);
                            if(i==34)
                                i35.setImageBitmap(bitmap);
                            if(i==35)
                                i36.setImageBitmap(bitmap);
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(gameActivity6.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setBackground(){
        docReference = mFiresore.collection("Cards").document("0");
        docReference.get()
                .addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String sImage = (String)documentSnapshot.getData().get("Resmi");
                            byte[] bytes= Base64.decode(sImage,Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                            i1.setImageBitmap(bitmap);
                            i2.setImageBitmap(bitmap);
                            i3.setImageBitmap(bitmap);
                            i4.setImageBitmap(bitmap);
                            i5.setImageBitmap(bitmap);
                            i6.setImageBitmap(bitmap);
                            i7.setImageBitmap(bitmap);
                            i8.setImageBitmap(bitmap);
                            i9.setImageBitmap(bitmap);
                            i10.setImageBitmap(bitmap);
                            i11.setImageBitmap(bitmap);
                            i12.setImageBitmap(bitmap);
                            i13.setImageBitmap(bitmap);
                            i14.setImageBitmap(bitmap);
                            i15.setImageBitmap(bitmap);
                            i16.setImageBitmap(bitmap);
                            i17.setImageBitmap(bitmap);
                            i18.setImageBitmap(bitmap);
                            i19.setImageBitmap(bitmap);
                            i20.setImageBitmap(bitmap);
                            i21.setImageBitmap(bitmap);
                            i22.setImageBitmap(bitmap);
                            i23.setImageBitmap(bitmap);
                            i24.setImageBitmap(bitmap);
                            i25.setImageBitmap(bitmap);
                            i26.setImageBitmap(bitmap);
                            i27.setImageBitmap(bitmap);
                            i28.setImageBitmap(bitmap);
                            i29.setImageBitmap(bitmap);
                            i30.setImageBitmap(bitmap);
                            i31.setImageBitmap(bitmap);
                            i32.setImageBitmap(bitmap);
                            i33.setImageBitmap(bitmap);
                            i34.setImageBitmap(bitmap);
                            i35.setImageBitmap(bitmap);
                            i36.setImageBitmap(bitmap);
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(gameActivity6.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void cevapAnahtari(){
        for (int i=0;i<line.length;i++) {
            if (line[i] == 1)
                System.out.println((i+1)+". Fotoğraf: Albus Dumbledore");
            else if (line[i] == 2)
                System.out.println((i+1)+". Fotoğraf: Rubeus Hagrid");
            else if (line[i] == 3)
                System.out.println((i+1)+". Fotoğraf: Minerva McGonagall");
            else if (line[i] == 4)
                System.out.println((i+1)+". Fotoğraf: Arthur Weasley");
            else if (line[i] == 5)
                System.out.println((i+1)+". Fotoğraf: Sirius Black");
            else if (line[i] == 6)
                System.out.println((i+1)+". Fotoğraf: Lily Potter");
            else if (line[i] == 7)
                System.out.println((i+1)+". Fotoğraf: Remus Lupin");
            else if (line[i] == 8)
                System.out.println((i+1)+". Fotoğraf: Peter Pettigrew");
            else if (line[i] == 9)
                System.out.println((i+1)+". Fotoğraf: Harry Potter");
            else if (line[i] == 10)
                System.out.println((i+1)+". Fotoğraf: Ron Weasley");
            else if (line[i] == 11)
                System.out.println((i+1)+". Fotoğraf: Hermione Granger");
            else if (line[i] == 12)
                System.out.println((i+1)+". Fotoğraf: Tom Riddle");
            else if (line[i] == 13)
                System.out.println((i+1)+". Fotoğraf: Horace Slughorn");
            else if (line[i] == 14)
                System.out.println((i+1)+". Fotoğraf: Bellatrix Lestrange");
            else if (line[i] == 15)
                System.out.println((i+1)+". Fotoğraf: Narcissa Malfoy");
            else if (line[i] == 16)
                System.out.println((i+1)+". Fotoğraf: Andromeda Tonks");
            else if (line[i] == 17)
                System.out.println((i+1)+". Fotoğraf: Lucius Malfoy");
            else if (line[i] == 18)
                System.out.println((i+1)+". Fotoğraf: Evan Rosier");
            else if (line[i] == 19)
                System.out.println((i+1)+". Fotoğraf: Draco Malfoy");
            else if (line[i] == 20)
                System.out.println((i+1)+". Fotoğraf: Dolores Umbridge");
            else if (line[i] == 21)
                System.out.println((i+1)+". Fotoğraf: Severus Snape");
            else if (line[i] == 22)
                System.out.println((i+1)+". Fotoğraf: Leta Lestrange");
            else if (line[i] == 23)
                System.out.println((i+1)+". Fotoğraf: Rowena Ravenclaw");
            else if (line[i] == 24)
                System.out.println((i+1)+". Fotoğraf: Luna Lovegood");
            else if (line[i] == 25)
                System.out.println((i+1)+". Fotoğraf: Gilderoy Lockhart");
            else if (line[i] == 26)
                System.out.println((i+1)+". Fotoğraf: Filius Flitwick");
            else if (line[i] == 27)
                System.out.println((i+1)+". Fotoğraf: Cho Chang");
            else if (line[i] == 28)
                System.out.println((i+1)+". Fotoğraf: Sybill Trelawney");
            else if (line[i] == 29)
                System.out.println((i+1)+". Fotoğraf: Marcus Belby");
            else if (line[i] == 30)
                System.out.println((i+1)+". Fotoğraf: Myrtle Warren");
            else if (line[i] == 31)
                System.out.println((i+1)+". Fotoğraf: Padma Patil");
            else if (line[i] == 32)
                System.out.println((i+1)+". Fotoğraf: Quirinus Quirrell");
            else if (line[i] == 33)
                System.out.println((i+1)+". Fotoğraf: Garrick Ollivander");
            else if (line[i] == 34)
                System.out.println((i+1)+". Fotoğraf: Helga Hufflepuff");
            else if (line[i] == 35)
                System.out.println((i+1)+". Fotoğraf: Cedric Diggory");
            else if (line[i] == 36)
                System.out.println((i+1)+". Fotoğraf: Nymphadora Tonks");
            else if (line[i] == 37)
                System.out.println((i+1)+". Fotoğraf: Pomona Sprout");
            else if (line[i] == 38)
                System.out.println((i+1)+". Fotoğraf: Newt Scamander");
            else if (line[i] == 39)
                System.out.println((i+1)+". Fotoğraf: Fat Friar");
            else if (line[i] == 40)
                System.out.println((i+1)+". Fotoğraf: Hannah Abbott");
            else if (line[i] == 41)
                System.out.println((i+1)+". Fotoğraf: Ernest Macmillan");
            else if (line[i] == 42)
                System.out.println((i+1)+". Fotoğraf: Leanne");
            else if (line[i] == 43)
                System.out.println((i+1)+". Fotoğraf: Silvanus Kettleburn");
            else if (line[i] == 44)
                System.out.println((i+1)+". Fotoğraf: Ted Lupin");

        }
    }
}