package com.hdg.yazlab;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

public class gameActivity44 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFiresore;
    private DocumentReference docReference;

    private TextView sayac,skor1,skor2;
    private ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15,i16;
    private CountDownTimer countDownTimer;

    private Switch switchMuzik;
    private MediaPlayer mediaPlayer,mediaPlayer2,mediaPlayer3,mediaPlayer4;

    private int[] line =new int[16];
    private int[] matched = new int [16];
    private int[] cards = new int [44];

    private int firstCard,secondCard;
    private int firstCardId,secondCardId;
    private int clickedFirst,clickedSecond;
    private int cardNumber=1;
    private long puan,puan1=0,puan2=0;
    private int cardPoint;
    private int count=0;
    private long time;
    private int count2=0;
    private int turn = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game44);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFiresore = FirebaseFirestore.getInstance();

        sayac = (TextView) findViewById(R.id.textView);
        skor1 = (TextView)findViewById(R.id.textView2);
        skor2 = (TextView)findViewById(R.id.textView3);
        skor1.setText("Skor1: "+puan1);
        skor2.setText("Skor2: "+puan2);

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

        Random r = new Random();

        int num1 = r.nextInt(4) + 1;
        int num2 = r.nextInt(4) + 1;
        while (num1 == num2) {
            num2 = r.nextInt(4) + 1;
        }

        line[0]=num1;line[1]=num1;
        line[2]=num2;line[3]=num2;

        num1 = r.nextInt(11) + 12;
        num2 = r.nextInt(11) + 12;
        while (num1 == num2) {
            num2 = r.nextInt(11) + 12;
        }

        line[4]=num1;line[5]=num1;
        line[6]=num2;line[7]=num2;

        num1 = r.nextInt(11) + 23;
        num2 = r.nextInt(11) + 23;
        while (num1 == num2) {
            num2 = r.nextInt(11) + 23;
        }

        line[8]=num1;line[9]=num1;
        line[10]=num2;line[11]=num2;

        num1 = r.nextInt(11) + 34;
        num2 = r.nextInt(11) + 34;
        while (num1 == num2) {
            num2 = r.nextInt(11) + 34;
        }

        line[12]=num1;line[13]=num1;
        line[14]=num2;line[15]=num2;

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
        countDownTimer= new CountDownTimer(61000,1000){
            @Override
            public void onTick(long l){
                time=l/1000;
                sayac.setText("Süre: "+(l/1000));

                if(count==16){
                    mediaPlayer3.start();
                    countDownTimer.cancel();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(gameActivity44.this);
                    alertDialogBuilder
                            .setMessage("Oyun Bitti..\nSkor: "+puan1+"\nSkor2: "+puan2)
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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(gameActivity44.this);
                alertDialogBuilder
                        .setMessage("Süre Bitti..\nSkor: "+puan1+"\nSkor2: "+puan2)
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
            puan=(long) temp *ev;
            if(turn==1){
                puan1+=puan;
                skor1.setText("Skor1: "+puan1);
            }else if(turn==2){
                puan2+=puan;
                skor2.setText("Skor2: "+puan2);
            }
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
                puan=((kartPuani+kartPuani2)/ev);
                if(turn==1){
                    puan1-=puan;
                    skor1.setText("Skor1: "+puan1);
                }else if(turn==2){
                    puan2-=puan;
                    skor2.setText("Skor2: "+puan2);
                }
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
                puan=(((kartPuani+kartPuani2)/2)*(ev*ev2));
                if(turn==1){
                    puan1-=puan;
                    skor1.setText("Skor1: "+puan1);
                }else if(turn==2){
                    puan2-=puan;
                    skor2.setText("Skor2: "+puan2);
                }
            }

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

            if(turn==1){
                turn=2;
                skor1.setTextColor(Color.GRAY);
                skor2.setTextColor(Color.BLACK);
            }

            else if(turn==2){
                turn=1;
                skor2.setTextColor(Color.GRAY);
                skor1.setTextColor(Color.BLACK);
            }
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
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(gameActivity44.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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

                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(gameActivity44.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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