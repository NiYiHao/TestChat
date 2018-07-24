package com.example.administrator.testchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class startActivity extends AppCompatActivity {
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態
        setContentView( R.layout.activity_start );
        logo = findViewById( R.id.Logo );
        Animation fadein = AnimationUtils.loadAnimation(this,R.anim.fadein);
        logo.setAnimation( fadein );
        Thread thread = new Thread(  ){
            public void run(){
            super.run();
            try{
                sleep( 4000 );
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                startActivity( new Intent( startActivity.this,MainActivity.class ));
                finish();
            }
            }
        };
        thread.start();
    }
}
