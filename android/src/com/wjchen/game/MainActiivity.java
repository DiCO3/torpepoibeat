package com.wjchen.game;

/**
 * Created by WJChen on 2017/6/5.
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


public class MainActiivity extends AppCompatActivity {
    private ImageView playButton;
    private ImageView scoreButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up notitle
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SQLiteDatabase DB = null;
        try{
            DB = this.openOrCreateDatabase("leaderboard", MODE_PRIVATE ,null);
            DB.execSQL("CREATE TABLE IF NOT EXISTS scores (name TEXT ,score TEXT);");
            Cursor cursor = DB.rawQuery("SELECT * FROM scores",null);
            if(cursor !=null){
                if(cursor.getCount() == 0) DB.execSQL("INSERT INTO scores (name ,score) VALUES('Nobody','0');");
            }
        }catch (Exception e){
        }finally {}
        setContentView(R.layout.activity_main);

        playButton = (ImageView) findViewById(R.id.playBtn);
        scoreButton= (ImageView) findViewById(R.id.scoreBtn);

        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startGame();
            }
        });
        scoreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startScore();
            }
        });
    }
    private void startGame(){
        Intent intent = new Intent(this,AndroidLauncher.class);
        startActivity(intent);
    }
    public void startScore(){
        Intent intent = new Intent(this,ScoreBoardActivity.class);
        startActivity(intent);
    }
}
