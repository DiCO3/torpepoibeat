package com.wjchen.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wjchen.game.Libs.WorldContactListener;
import com.wjchen.game.Scenes.Hud;
import com.wjchen.game.Sprites.Boat;
import com.wjchen.game.Sprites.Coin;
import com.wjchen.game.Sprites.Torpedo;
import com.wjchen.game.TorpedobeatRevolution;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by WJChen on 2017/5/21.
 */

public class PlayScreen implements Screen{
    public static final float MIN_TORP_SPAWN_TIME = 0.2f;
    public static final float MAX_TORP_SPAWN_TIME = 0.5f;
    public static final float MIN_COIN_SPAWN_TIME = 2.0f;
    public static final float MAX_COIN_SPAWN_TIME = 5.0f;

    public static int attemptScore;

    private TorpedobeatRevolution game;
    private OrthographicCamera gameCam;

    private Viewport gamePort;
    private Hud hud;
    private Boat boat;
    ScrollingBackground scrollingBackground;
    ArrayList<Torpedo> torps;
    ArrayList<Coin> coins;
    Random random;
    float torpSpawnTimer ,coinSpawnTimer;
    Vector2 torpPosition ,torpDestination,coinPosition ,coinDestination;

    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(TorpedobeatRevolution game){
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(TorpedobeatRevolution.V_WIDTH,TorpedobeatRevolution.V_HEIGHT,gameCam);
        game.score = 0;
        game.boatIsDead = false;
        hud = new Hud(game.batch,game);

        scrollingBackground = new ScrollingBackground();
        scrollingBackground.resize(TorpedobeatRevolution.V_WIDTH,TorpedobeatRevolution.V_HEIGHT); //Gdx.graphics.getWidth(),Gdx.graphics.getHeight()
        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();
        random = new Random();
        torpSpawnTimer = random.nextFloat() * (MAX_TORP_SPAWN_TIME-MIN_TORP_SPAWN_TIME) + MIN_TORP_SPAWN_TIME;
        coinSpawnTimer = random.nextFloat() * (MAX_COIN_SPAWN_TIME-MIN_COIN_SPAWN_TIME) + MIN_COIN_SPAWN_TIME;

        boat = new Boat(world ,this);
        torps = new ArrayList<Torpedo>();
        coins = new ArrayList<Coin>();

        world.setContactListener(new WorldContactListener(game));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2dr.render(world,gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        scrollingBackground.updateAndRender(delta,game.batch);
        for(Coin coin :coins){
            coin.draw(game.batch);
        }
        for(Torpedo torp :torps){
            torp.draw(game.batch);
        }
        boat.draw(game.batch);

        boat.draw(game.batch);
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(gameOver()){
            attemptScore = game.score;
            game.startScoreActivity();
            dispose();
        }
    }

    public void handleInput(float dt){
        boat.boatbody.setLinearDamping(Math.abs(1.2f-Math.abs(boat.boatbody.getLinearVelocity().x/120f)));
        System.out.printf("%f\n",boat.boatbody.getLinearDamping()); //boat.boatbody.getLinearVelocity().x
        if (Gdx.input.isTouched(0)){
            if(Gdx.input.getX(0) > gamePort.getScreenWidth()/2 && boat.boatbody.getLinearVelocity().x <= 120f){
                boat.boatbody.applyLinearImpulse(new Vector2(3600f,0),boat.boatbody.getWorldCenter(),true);
            }else if(Gdx.input.getX(0) < gamePort.getScreenWidth()/2 && boat.boatbody.getLinearVelocity().x >= -120f){
                boat.boatbody.applyLinearImpulse(new Vector2(-3600f,0),boat.boatbody.getWorldCenter(),true);
            }
        }
    }

    public void update(float dt){
        handleInput(dt);
        world.step(1/60f,6,2);
        boat.update();
        hud.update();

        torpSpawnTimer -= dt;
        coinSpawnTimer -= dt;
        if (torpSpawnTimer <= 0){
            torpSpawnTimer = random.nextFloat() * (MAX_TORP_SPAWN_TIME - MIN_TORP_SPAWN_TIME) + MIN_TORP_SPAWN_TIME;
            torpPosition    = new Vector2(random.nextFloat()*(this.gameCam.viewportWidth)-this.gameCam.viewportWidth/2f,this.gameCam.viewportHeight/2f + 5f);
            torpDestination = new Vector2(random.nextFloat()*(this.gameCam.viewportWidth)-this.gameCam.viewportWidth/2f,this.boat.boatbody.getWorldCenter().y+20f);
            torps.add(new Torpedo(this.world,this,torpPosition,torpDestination.sub(torpPosition)));
        }
        if (coinSpawnTimer <= 0) {
            coinSpawnTimer = random.nextFloat() * (MAX_COIN_SPAWN_TIME - MIN_COIN_SPAWN_TIME) + MIN_COIN_SPAWN_TIME;
            coinPosition = new Vector2(random.nextFloat() * (this.gameCam.viewportWidth) - this.gameCam.viewportWidth / 2f, this.gameCam.viewportHeight / 2f + 5f);
            coinDestination = new Vector2(random.nextFloat() * (this.gameCam.viewportWidth) - this.gameCam.viewportWidth / 2f, this.boat.boatbody.getWorldCenter().y + 20f);
            coins.add(new Coin(this.world, this, coinPosition, coinDestination.sub(coinPosition)));
        }
        ArrayList<Torpedo> torpToRemove = new ArrayList<Torpedo>();
        ArrayList<Coin> coinToRemove = new ArrayList<Coin>();
        for(Torpedo torp :torps){
            torp.update();
            if (torp.remove)
                torpToRemove.add(torp);
        }
        for(Torpedo torp : torpToRemove){
            torp.killbody();
        }
        torps.removeAll(torpToRemove);
        for(Coin coin :coins){
            coin.update();
            if (coin.remove)
                coinToRemove.add(coin);
        }
        for(Coin coin :coinToRemove){
            coin.killbody();
        }
        coins.removeAll(coinToRemove);

//        torptest.update(); //TEST
        //gameCam.update();
    }


    public OrthographicCamera getGameCam() {
        return gameCam;
    }

    public boolean gameOver(){
        return game.isDead();
    }
    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
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
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        scrollingBackground.dispose();
    }
}
