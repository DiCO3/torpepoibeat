package com.wjchen.game;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.wjchen.game.model.Items;
import com.wjchen.game.adapter.CustomListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.wjchen.game.Screens.PlayScreen.attemptScore;

/**
 * Created by WJChen on 2017/6/7.
 */

public class ScoreBoardActivity extends AppCompatActivity {
    SQLiteDatabase DB = null;
    private List<Items> itemsList = new ArrayList<Items>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up notitle
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scoreboard);

        try {

            //Create a Database if doesnt exist otherwise Open It

            DB = this.openOrCreateDatabase("leaderboard", MODE_PRIVATE, null);

            //Create table in database if it doesnt exist allready

            DB.execSQL("CREATE TABLE IF NOT EXISTS scores (name TEXT, score TEXT);");

            //Select all rows from the table

            Cursor cursor = DB.rawQuery("SELECT * FROM scores", null);

            //If there are no rows (data) then insert some in the table

            if (cursor != null) {
                if (cursor.getCount() == 0) {

                    DB.execSQL("INSERT INTO scores (name, score) VALUES ('Andy', '7');");
                    DB.execSQL("INSERT INTO scores (name, score) VALUES ('Marie', '4');");
                    DB.execSQL("INSERT INTO scores (name, score) VALUES ('George', '1');");

                }

            }


        } catch (Exception e) {

        } finally {

            //Initialize and create a new adapter with layout named list found in activity_main layout

            listView = (ListView) findViewById(R.id.list);
            adapter = new CustomListAdapter(this, itemsList);
            listView.setAdapter(adapter);

            Cursor cursor = DB.rawQuery("SELECT * FROM scores ORDER BY score DESC", null);

            if (cursor.moveToFirst()) {

                //read all rows from the database and add to the Items array

                while (!cursor.isAfterLast()) {

                    Items items = new Items();

                    items.setName(cursor.getString(0));
                    items.setScore(cursor.getString(1));

                    itemsList.add(items);
                    cursor.moveToNext();


                }
            }

            //All done, so notify the adapter to populate the list using the Items Array

            adapter.notifyDataSetChanged();

        }
    }

    public void returnToMenu(){
        Intent intent = new Intent(this,MainActiivity.class);
        startActivity(intent);
    }
}

