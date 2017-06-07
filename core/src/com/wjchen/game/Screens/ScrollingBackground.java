package com.wjchen.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by WJChen on 2017/6/7.
 */

public class ScrollingBackground implements Disposable{
    public static final int SPEED = 80;

    Texture image;
    float y1, y2;
    float imageScale ,width ,height;

    public ScrollingBackground(){
        image = new Texture("scrollingBg.png");

        y1=0;
        y2=0;
        imageScale=0;
        width=0;
        height=0;
    }

    public void updateAndRender(float dt , SpriteBatch batch){
        y1 -= SPEED *dt;
        y2 -= SPEED *dt;

        if(y1 +image.getHeight()*imageScale<=0) y1 = y2+image.getHeight()*imageScale;
        if(y2 +image.getHeight()*imageScale<=0) y2 = y1+image.getHeight()*imageScale;

        batch.draw(image,0-width/2f,y1-height/2f,width,image.getHeight()*imageScale);
        batch.draw(image,0-width/2f,y2-height/2f,width,image.getHeight()*imageScale);
//        System.out.printf("%f;%f.\n",y1,y2);
//        System.out.printf("%f;%f.\n",width,height);
    }

    public void resize (float width ,float height){
        this.imageScale= width/image.getWidth();
        this.width = width;
        this.height = height;
        this.y2 = image.getHeight()*imageScale;
    }

    @Override
    public void dispose() {
        image.dispose();
    }
}
