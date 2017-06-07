package com.wjchen.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.wjchen.game.Libs.GameCallback;
import com.wjchen.game.TorpedobeatRevolution;

public class AndroidLauncher extends AndroidApplication{
	public static GameCallbackAndroid mygameCallback;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mygameCallback = new GameCallbackAndroid(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		initialize(new TorpedobeatRevolution(mygameCallback), config);
	}



}
