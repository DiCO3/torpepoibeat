package com.wjchen.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.wjchen.game.Libs.B2DVars;
import com.wjchen.game.Libs.BodyEditorLoader;
import com.wjchen.game.Screens.PlayScreen;

/**
 * Created by WJChen on 2017/6/5.
 */

public class Coin extends Sprite{
    private static final int WIDTH = 24;
    private World world;
    private PlayScreen screen;
    public Body coinbody;
    public boolean remove = false;
    private Vector2 coinOrigin;
    private Vector2 position0;
    private Vector2 direction;
    private Fixture fixture;

    public Coin (World world , PlayScreen screen ,Vector2 position0 ,Vector2 direction){
        super(new Texture("coin.fw.png"));
        this.world = world;
        this.screen = screen;
        this.position0 = position0;
        this.direction = direction;
        defineCoin();
        this.setScale(WIDTH/this.getWidth());
    }
    public void defineCoin() {
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/boat.json"));
        BodyDef bdef = new BodyDef();
        bdef.position.set(position0);
        bdef.type = BodyDef.BodyType.DynamicBody;


        FixtureDef fdef = new FixtureDef();
        fdef.density = 0.01f;
        fdef.friction = 0.5f;
        fdef.restitution = 0.0f;
        fdef.filter.categoryBits = B2DVars.BIT_COIN | B2DVars.BIT_BOAT;
        fdef.isSensor = true;

        coinbody = world.createBody(bdef);

        loader.attachFixture(coinbody ,"coin.fw.png",fdef,WIDTH);
        fixture = coinbody.getFixtureList().first();
        fixture.setUserData(this);
        coinOrigin = loader.getOrigin("coin.fw.png",WIDTH).cpy();

        direction = direction.setLength(90f);

//        System.out.printf("%.3f %.3f\t",direction.x,direction.y);

        coinbody.setLinearVelocity(direction);
//        coinbody.applyLinearImpulse(new Vector2(direction.x,direction.y),coinbody.getWorldCenter(),true);

//        System.out.printf("%.3f %.3f\n",torpbody.getLinearVelocity().x,torpbody.getLinearVelocity().y);
//        coinbody.setTransform(coinbody.getPosition(),direction.angleRad()-90f*DEGREES_TO_RADIANS);
        this.setOrigin(coinOrigin.x,coinOrigin.y);
        this.setRotation(coinbody.getAngle() * MathUtils.radiansToDegrees);
    }
    public void killbody(){
        world.destroyBody(this.coinbody);
    }

    public void update(){
        Vector2 coinPos = coinbody.getPosition().sub(coinOrigin);
        this.setPosition(coinPos.x,coinPos.y);

        if(this.coinbody.getWorldCenter().y < -(screen.getGameCam().viewportHeight/2 +20)){
            remove = true;
        }

    }

    public void draw (Batch batch){
        super.draw(batch);
    }
}
