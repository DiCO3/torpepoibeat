package com.wjchen.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wjchen.game.TorpedobeatRevolution;

/**
 * Created by WJChen on 2017/5/30.
 */

public class GameOverScreen implements Screen{
    private Viewport viewport;
    private Stage stage;
    OrthographicCamera gameCam;

    private TorpedobeatRevolution game;

    public GameOverScreen (TorpedobeatRevolution game ,OrthographicCamera gameCam){
        this.game = game;
        this.gameCam = gameCam;
        viewport = new FitViewport(TorpedobeatRevolution.V_WIDTH,TorpedobeatRevolution.V_HEIGHT,gameCam);
        stage = new Stage(viewport ,((TorpedobeatRevolution)game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER",font);  gameOverLabel.setFontScale(3);
        Label scoreLabel = new Label(String.format("Score : %03d",((TorpedobeatRevolution) game).getScore()),font); scoreLabel.setFontScale(2);
        Label playAgainLabel = new Label("Touch to Play Again",font);   playAgainLabel.setFontScale(2);
        table.add(gameOverLabel).expandX();
        table.row();
        table.add(scoreLabel).expandX().padTop(10f);
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);


        stage.addActor(table);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            game.setScreen(new PlayScreen(game));
            dispose();
        }
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
