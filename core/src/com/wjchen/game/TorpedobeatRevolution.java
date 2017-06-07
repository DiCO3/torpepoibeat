package com.wjchen.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wjchen.game.Libs.GameCallback;
import com.wjchen.game.Screens.PlayScreen;

public class TorpedobeatRevolution extends Game implements GameCallback {
	public GameCallback gameCallback;
	public static final int V_WIDTH = 540;
	public static final int V_HEIGHT = 960;
	public static final int PPM = 100;
	public SpriteBatch batch;
	public boolean boatIsDead = false;
	public int score = 0;

	public TorpedobeatRevolution (GameCallback gameCallback){ //GameCallback gameCallback
		super();
		this.gameCallback = gameCallback;
	}

	public boolean isDead(){
		return boatIsDead;
	}

	public int getScore(){
		return score;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void startScoreActivity() {
		gameCallback.startScoreActivity();
	}
}
