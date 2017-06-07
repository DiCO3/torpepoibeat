package com.wjchen.game;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.wjchen.game.Screens.PlayScreen.attemptScore;

/**
 * Created by WJChen on 2017/6/7.
 */

public class ScoreActivity extends AppCompatActivity {
    SQLiteDatabase DB = null;
    Cursor cursor;
    TextView gameScore;
    EditText nameEditText;
    Button submitButton;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up notitle
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        try{
            DB = this.openOrCreateDatabase("leaderboard", MODE_PRIVATE ,null);
            DB.execSQL("CREATE TABLE IF NOT EXISTS scores (name TEXT ,score TEXT);");
            cursor = DB.rawQuery("SELECT * FROM scores",null);
            if(cursor !=null){
                if(cursor.getCount() == 0) DB.execSQL("INSERT INTO scores (name ,score) VALUES('Nobody','0');");
            }
        }catch (Exception e){
        }finally {}
        setContentView(R.layout.activity_score);

        gameScore = (TextView)findViewById(R.id.gameScore);
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        submitButton =(Button)findViewById(R.id.submitButton);

        gameScore.setText(String.valueOf(attemptScore));
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nameEditText.getText().equals("")){
                    if(cursor !=null){
                        if(attemptScore>0)DB.execSQL("INSERT INTO scores (name ,score) VALUES('"+nameEditText.getText().toString()+"','"+gameScore.getText().toString()+"');");
                        returnToMenu();
                    }
                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        returnToMenu();
    }
    public void returnToMenu(){
        Intent intent = new Intent(this,MainActiivity.class);
        startActivity(intent);
    }
}
