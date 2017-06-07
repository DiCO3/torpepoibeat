package com.wjchen.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wjchen.game.Libs.GameCallback;

/**
 * Created by WJChen on 2017/6/7.
 */

public class GameCallbackAndroid implements GameCallback {
    Context appContext;
    Handler uiThread;

    public GameCallbackAndroid(Context appContext){
        uiThread = new Handler();
        this.appContext = appContext;
    }
    @Override
    public void startScoreActivity() {
        Intent intent = new Intent(this.appContext,ScoreActivity.class);
        appContext.startActivity(intent);
    }
}
