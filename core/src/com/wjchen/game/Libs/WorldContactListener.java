package com.wjchen.game.Libs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.wjchen.game.Sprites.Boat;
import com.wjchen.game.Sprites.Coin;
import com.wjchen.game.Sprites.Torpedo;
import com.wjchen.game.TorpedobeatRevolution;

/**
 * Created by WJChen on 2017/5/30.
 */

public class WorldContactListener implements ContactListener{
    TorpedobeatRevolution game;
    public WorldContactListener(TorpedobeatRevolution game) {
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() == "boat" || fixB.getUserData() == "boat"){
            Fixture boat = fixA.getUserData() == "boat" ? fixA : fixB;
            Fixture object = boat == fixA ? fixB : fixA;

            if(object.getUserData() != null && object.getUserData().getClass() == Torpedo.class){
                System.out.printf("Torp hit!\n");
                game.boatIsDead = true;
            }else if (object.getUserData() != null && object.getUserData().getClass() == Coin.class){
                System.out.printf("Coin hit!\n");
                game.score += 1;
                Coin touchedCoin = (Coin)object.getUserData();
                touchedCoin.remove = true;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
