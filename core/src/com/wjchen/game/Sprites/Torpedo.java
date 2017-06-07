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
import com.badlogic.gdx.utils.Disposable;
import com.wjchen.game.Libs.B2DVars;
import com.wjchen.game.Libs.BodyEditorLoader;
import com.wjchen.game.Screens.PlayScreen;
import com.wjchen.game.TorpedobeatRevolution;

/**
 * Created by WJChen on 2017/5/21.
 */

public class Torpedo extends Sprite{
    private static final float DEGREES_TO_RADIANS = (float)(Math.PI/180);
    private static final float SPEED_SCALE = 3200;
    private static final int WIDTH = 4;
    private World world;
    private PlayScreen screen;
    public Body torpbody;
    public boolean remove = false;
    private Vector2 torpOrigin;
    private Vector2 position0;
    private Vector2 direction;
    private Fixture fixture;

    public Torpedo (World world , PlayScreen screen ,Vector2 position0 ,Vector2 direction){
        super(new Texture("torpedo.fw.png"));
        this.world = world;
        this.screen = screen;
        this.position0 = position0;
        this.direction = direction;
        defineTorpedo();
        this.setScale(WIDTH/this.getWidth());
    }
    public void defineTorpedo() {
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/boat.json"));
        BodyDef bdef = new BodyDef();
        bdef.position.set(position0);
        bdef.type = BodyDef.BodyType.DynamicBody;


        FixtureDef fdef = new FixtureDef();
        fdef.density = 0.01f;
        fdef.friction = 0.5f;
        fdef.restitution = 0.0f;
        fdef.filter.categoryBits = B2DVars.BIT_TORP | B2DVars.BIT_BOAT;
        fdef.isSensor = true;

        torpbody = world.createBody(bdef);

        loader.attachFixture(torpbody ,"torpedo.fw.png",fdef,WIDTH);
        fixture = torpbody.getFixtureList().first();
        fixture.setUserData(this);
        torpOrigin = loader.getOrigin("torpedo.fw.png",WIDTH).cpy();

        direction = direction.setLength(120f);

//        System.out.printf("%.3f %.3f\t",direction.x,direction.y);

        torpbody.setLinearVelocity(direction);
//        torpbody.applyLinearImpulse(new Vector2(direction.x,direction.y),torpbody.getWorldCenter(),true);

//        System.out.printf("%.3f %.3f\n",torpbody.getLinearVelocity().x,torpbody.getLinearVelocity().y);
        torpbody.setTransform(torpbody.getPosition(),direction.angleRad()+90f*DEGREES_TO_RADIANS);
        this.setOrigin(torpOrigin.x,torpOrigin.y);
        this.setRotation(torpbody.getAngle() * MathUtils.radiansToDegrees);
    }
    public void killbody(){
        world.destroyBody(this.torpbody);
    }

    public void update(){
        Vector2 torpPos = torpbody.getPosition().sub(torpOrigin);
        this.setPosition(torpPos.x,torpPos.y);

        if(this.torpbody.getWorldCenter().y < -(screen.getGameCam().viewportHeight/2 +20)){
            remove = true;
        }

    }

    public void draw (Batch batch){
        super.draw(batch);
    }
}
