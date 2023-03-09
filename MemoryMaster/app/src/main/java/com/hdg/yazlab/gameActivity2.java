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

public class gameActivity2 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFiresore;
    private DocumentReference docReference;

    private TextView sayac,skor;
    private ImageView i1,i2,i3,i4;
    private CountDownTimer countDownTimer;

    private Switch switchMuzik;
    private MediaPlayer mediaPlayer,mediaPlayer2,mediaPlayer3,mediaPlayer4;

    private int[] line =new int[4];
    private int[] matched = new int [4];
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
        setContentView(R.layout.activity_game2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFiresore = FirebaseFirestore.getInstance();

        sayac = (TextView) findViewById(R.id.sayac);
        skor = (TextView) findViewById(R.id.skor);
        skor.setText("Skor: "+puan);

        i1=(ImageView)findViewById(R.id.i1);
        i2=(ImageView)findViewById(R.id.i2);
        i3=(ImageView)findViewById(R.id.i3);
        i4=(ImageView)findViewById(R.id.i4);

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

        Random r = new Random();

        int num1 = r.nextInt(4) + 1;
        int num2 = r.nextInt(4) + 1;
        while (num1 == num2) {
            num2 = r.nextInt(4) + 1;
        }

        int low,high,result;
        if(num1==1){
            low = 1;
            high = 11;
            result = r.nextInt(high-low) + low;
            line[0]=result;line[1]=result;
        }else if(num1==2){
            low = 12;
            high = 22;
            result = r.nextInt(high-low) + low;
            line[0]=result;line[1]=result;
        }else if(num1==3){
            low = 23;
            high = 33;
            result = r.nextInt(high-low) + low;
            line[0]=result;line[1]=result;
        }else if(num1==4){
            low = 34;
            high = 44;
            result = r.nextInt(high-low) + low;
            line[0]=result;line[1]=result;
        }

        if(num2==1){
            low = 1;
            high = 11;
            result = r.nextInt(high-low) + low;
            line[2]=result;line[3]=result;
        }else if(num2==2){
            low = 12;
            high = 22;
            result = r.nextInt(high-low) + low;
            line[2]=result;line[3]=result;
        }else if(num2==3){
            low = 23;
            high = 33;
            result = r.nextInt(high-low) + low;
            line[2]=result;line[3]=result;
        }else if(num2==4){
            low = 34;
            high = 44;
            result = r.nextInt(high-low) + low;
            line[2]=result;line[3]=result;
        }

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

                if(count==4){
                    mediaPlayer3.start();
                    countDownTimer.cancel();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(gameActivity2.this);
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
                sayac.setText("Süre: 0");
                mediaPlayer4.start();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(gameActivity2.this);
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

            puan+=((long)2*pn*ev)*(time/10);
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

            if(!contains(0))
                setFront(0,0);
            if(!contains(1))
                setFront(0,1);
            if(!contains(2))
                setFront(0,2);
            if(!contains(3))
                setFront(0,3);
        }

        skor.setText("Skor: "+puan);

        i1.setEnabled(true);
        i2.setEnabled(true);
        i3.setEnabled(true);
        i4.setEnabled(true);

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
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(gameActivity2.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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

                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(gameActivity2.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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