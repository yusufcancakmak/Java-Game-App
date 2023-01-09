package com.info.oyuncalismasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class OyunEkraniActivity extends AppCompatActivity {
    private ConstraintLayout cl;
    private TextView textViewoyunabasla;
    private TextView textViewSkor;
    private ImageView saridaire;
    private ImageView kirmiziucgen;
    private ImageView anakarakter;
    private ImageView siyahkare;
    //Pozisyonlar
    private int anakarakterX;
    private int anakarakterY;
    private int saridaireX;
    private int saridaireY;
    private int kirmiziucgenX;
    private int kirmiziucgenY;
    private int siyahkareX;
    private int siyahkareY;
    //boyutlar
    private int ekranGenisligi;
    private int ekranYuksekligi;
    private int anakarakterGenisligi;
    private int anakarakterYuksekligi;

    //Hızlar
    private int anakarakterHiz;
    private int saridaireHiz;
    private int kirmiziucgenhiz;
    private int siyahkareHiz;


    //Kontroller
    private boolean dokunmaKontrol =false;
    private boolean baslangıckontrol=false;

    private int skor=0;
    private Timer timer = new Timer();
    private Handler handler= new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun_ekrani);
        cl=findViewById(R.id.cl);

        textViewoyunabasla=findViewById(R.id.textViewoyunabasla);
        textViewSkor=findViewById(R.id.textViewSkor);
        saridaire=findViewById(R.id.saridaire);
        kirmiziucgen=findViewById(R.id.kirmiziucgen);
        anakarakter=findViewById(R.id.anakarakter);
        siyahkare=findViewById(R.id.siyahkare);

        //Cisimleri Ekranın Dısına ÇıKARMA
        siyahkare.setX(-80);
        siyahkare.setY(-80);
        kirmiziucgen.setX(-80);
        kirmiziucgen.setY(-80);
        saridaire.setX(-80);
        saridaire.setY(-80);


        textViewoyunabasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OyunEkraniActivity.this,SonucEkraniActivity.class));
                finish();
            }
        });

        cl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (baslangıckontrol){
                    if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                        Log.e("MotionEvent","Ekrana dokunuldu");
                        dokunmaKontrol =true;
                    }
                    if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                        Log.e("MotionEvent","Ekrana bırakıldı");
                        dokunmaKontrol=false;

                    }


                }else {
                    baslangıckontrol =true;
                    textViewoyunabasla.setVisibility(View.INVISIBLE);

                    anakarakterX =(int) anakarakter.getX();
                    anakarakterY=(int) anakarakter.getY();
                    anakarakterGenisligi = anakarakter.getWidth();
                    anakarakterYuksekligi=anakarakter.getHeight();
                    ekranGenisligi=cl.getWidth();
                    ekranYuksekligi=cl.getHeight();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                  anakarakterHareketEttirme();
                                  cisimleriHareketEttir();
                                  CarpısmaKOntrol();
                                }
                            });

                        }
                    },0,20);

                }




                return true;
            }
        });
    }
    public void anakarakterHareketEttirme(){
        if (dokunmaKontrol){
            anakarakterHiz=Math.round(ekranYuksekligi/60);//1280//60=20
            anakarakterY-=anakarakterHiz;

        }else {
            anakarakterY+=anakarakterHiz;
        }
        if (anakarakterY<=0){
            anakarakterY=0;
        }
        if (anakarakterY>=ekranYuksekligi - anakarakterYuksekligi){
            anakarakterY=ekranYuksekligi - anakarakterYuksekligi;
        }
        anakarakter.setY(anakarakterY);
    }
    public void cisimleriHareketEttir(){
        kirmiziucgenhiz=Math.round(ekranGenisligi/30);//760//60=13
        saridaireHiz=Math.round(ekranGenisligi/60);//1280//60=20
        siyahkareHiz=Math.round(ekranGenisligi/60);//1280//60=20
        siyahkareX-=siyahkareHiz;
        if (siyahkareX<0){
            siyahkareX =ekranGenisligi +20;
            siyahkareY =(int) Math.floor(Math.random() * ekranYuksekligi);

        }
        siyahkare.setX(siyahkareX);
        siyahkare.setY(siyahkareY);

        saridaireX-=saridaireHiz;
        if (saridaireX<0){
            saridaireX =ekranGenisligi +20;
            saridaireY =(int) Math.floor(Math.random() * ekranYuksekligi);

        }
        saridaire.setX(saridaireX);
        saridaire.setY(saridaireY);

        kirmiziucgenX-=kirmiziucgenhiz;
        if (kirmiziucgenX<0){
            kirmiziucgenX =ekranGenisligi +20;
            kirmiziucgenY =(int) Math.floor(Math.random() * ekranYuksekligi);

        }
        kirmiziucgen.setX(kirmiziucgenX);
        kirmiziucgen.setY(kirmiziucgenY);

    }
    public void CarpısmaKOntrol(){
        int saridaireMerkezX=saridaireX+saridaire.getWidth()/2;
        int saridaireMerkezY =saridaireY+saridaire.getHeight()/2;

        if (0<=saridaireMerkezX && saridaireMerkezX<=anakarakterGenisligi && anakarakterY <=saridaireMerkezY && saridaireMerkezY <=anakarakterY+anakarakterYuksekligi){
        skor+=50;
        saridaireX = -10;
        }
        int kirmiziucgenMerkezX=kirmiziucgenX+kirmiziucgen.getWidth()/2;
        int kirmiziucgenMerkezY =kirmiziucgenY+kirmiziucgen.getHeight()/2;

        if (0<=kirmiziucgenMerkezX && kirmiziucgenMerkezX<=anakarakterGenisligi && anakarakterY <=kirmiziucgenMerkezY && kirmiziucgenMerkezY <=anakarakterY+anakarakterYuksekligi){
            skor+=20;
            kirmiziucgenX = -10;
        }
        int siyahkareMerkezX=siyahkareX+siyahkare.getWidth()/2;
        int siyahKareMerkezY =siyahkareY+siyahkare.getHeight()/2;

        if (0<=siyahkareMerkezX && siyahkareMerkezX<=anakarakterGenisligi && anakarakterY <=siyahKareMerkezY && siyahKareMerkezY <=anakarakterY+anakarakterYuksekligi){

            siyahkareX = -10;
            //Timer durdur.
            timer.cancel();
            timer=null;
            Intent intent = new Intent(OyunEkraniActivity.this,SonucEkraniActivity.class);
            intent.putExtra("skor",skor);
            startActivity(intent);
        }
        textViewSkor.setText(String.valueOf(skor));
    }
}