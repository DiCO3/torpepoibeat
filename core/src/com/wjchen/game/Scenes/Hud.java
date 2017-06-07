package com.wjchen.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wjchen.game.TorpedobeatRevolution;

/**
 * Created by WJChen on 2017/5/21.
 */

public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;
    public TorpedobeatRevolution game;


    Label scoreLabel;

    public Hud (SpriteBatch sb ,TorpedobeatRevolution game){
        this.game = game;

        viewport = new FitViewport(TorpedobeatRevolution.V_WIDTH,TorpedobeatRevolution.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport ,sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%03d",game.getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setFontScale(3);

        table.add(scoreLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    public void update(){
        scoreLabel.setText(String.format("%03d",game.getScore()));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
