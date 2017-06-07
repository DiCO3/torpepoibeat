package com.wjchen.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier;
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
import com.wjchen.game.TorpedobeatRevolution;

/**
 * Created by WJChen on 2017/5/21.
 */

public class Boat extends Sprite{
    private static final float DEGREES_TO_RADIANS = (float)(Math.PI/180);
    private static final float WIDTH = 17;
    public World world;
    public Body boatbody;
//    public boolean isDead = false;
    private Vector2 boatOrigin;
    private PlayScreen screen;
    private Fixture fixture;

    public Boat(World world ,PlayScreen screen){
        super(new Texture("boat.fw.png"));
        this.world = world;
        this.screen = screen;
        defineBoat();
        this.setScale(WIDTH/this.getWidth());
    }
    public void defineBoat() {
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/boat.json"));
        BodyDef bdef = new BodyDef();
        bdef.position.set(0,-450);
//        bdef.angularVelocity = 5;
        bdef.type = BodyDef.BodyType.DynamicBody;


        FixtureDef fdef = new FixtureDef();
        fdef.density = 1;
        fdef.friction = 0.5f;
        fdef.restitution = 0.0f;
        fdef.filter.categoryBits = B2DVars.BIT_TORP | B2DVars.BIT_BOAT;
//        fdef.isSensor = true;

        boatbody = world.createBody(bdef);

        loader.attachFixture(boatbody ,"boat.fw.png",fdef,WIDTH);
        fixture = boatbody.getFixtureList().first();
        fixture.setUserData("boat");
        boatOrigin = loader.getOrigin("boat.fw.png",WIDTH).cpy();
    }

    public void update(){
        Vector2 boatPos = boatbody.getPosition().sub(boatOrigin);
        this.setPosition(boatPos.x,boatPos.y);
        this.setOrigin(boatOrigin.x,boatOrigin.y);
        this.setRotation(boatbody.getAngle() * MathUtils.radiansToDegrees);

        if(boatbody.getWorldCenter().x < -screen.getGameCam().viewportWidth/2) boatbody.setTransform(-screen.getGameCam().viewportWidth/2-(boatbody.getWorldCenter().sub(boatPos)).x,boatPos.y,boatbody.getAngle());
        if(boatbody.getWorldCenter().x >  screen.getGameCam().viewportWidth/2) boatbody.setTransform( screen.getGameCam().viewportWidth/2-(boatbody.getWorldCenter().sub(boatPos)).x,boatPos.y,boatbody.getAngle());

        float angle = (new Vector2(this.boatbody.getLinearVelocity().x,270).angleRad()-(this.boatbody.getAngle()+90f)*DEGREES_TO_RADIANS);
        this.boatbody.setTransform(this.boatbody.getPosition(), angle);

    }

    public void draw (Batch batch){
        super.draw(batch);
    }

}
