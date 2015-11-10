package com.mygdx.edlaser.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;



/**
 * Created by welser on 03.08.2015.
 */
public class Tube {
    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBotTube;
    private Random rand;
    public static final int TUBE_WIDTH= 52;
    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    private Rectangle boundsTop, boundsBot;


    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    public void reposition(float x){
        posTopTube.set(x,rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(posTopTube.x, posTopTube.y+5);
        boundsBot.setPosition(posBotTube.x, posBotTube.y-5);
    }

    public boolean collides(Rectangle player){
        return  player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    public Tube(float x){
        topTube = new Texture("toptubevn.png");
        bottomTube = new Texture("bottomtubem.png");
        rand = new Random();

        posTopTube = new Vector2(x,rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight()+5);
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight()-5);

    }

    public void dispose(){
        topTube.dispose();
        bottomTube.dispose();
    }

}
