package com.mygdx.edlaser.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.edlaser.MyGdxGame;

/**
 * Created by welser on 03.08.2015.
 */
public class MenuState extends State {
    private Texture background;
    private Texture plyBtn;
    private Texture plyBtnL;
    BitmapFont font;
    private Texture logo;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        //mit cam ist neu
        cam.setToOrtho(false, MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2);
        background = new Texture("ed.png");
        plyBtn = new Texture("played.png");
        plyBtnL = new Texture("playlauch.png");
        logo = new Texture("logso.png");
        font = new BitmapFont();
        font.getData().setScale(3,3);
    }

    @Override
    public void handleImput() {
        //if(Gdx.input.justTouched()){
        if(Gdx.input.isTouched()){
            // Check which button is clicked
            Rectangle bounds = new Rectangle(Gdx.graphics.getWidth()/ 3 - plyBtn.getWidth(),Gdx.graphics.getHeight()/ 2,Gdx.graphics.getHeight() /3 ,Gdx.graphics.getWidth()/4);
            Rectangle bound  = new Rectangle(Gdx.graphics.getWidth()/ 3 - plyBtn.getWidth(),Gdx.graphics.getHeight()/ 3/2 *2,Gdx.graphics.getHeight() /3 ,Gdx.graphics.getWidth()/4);
            Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
            // Lauchboy Modus
            if(bounds.contains(tmp.x, tmp.y)){
                gsm.set(new PlayState(gsm,0));
            // EdLaser Modus
            } else if(bound.contains(tmp.x, tmp.y)){
                gsm.set(new PlayState(gsm,1));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleImput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.enableBlending();
        //sb.setProjectionMatrix(cam.combined);
        sb.begin();
        //sb.draw(background, 0, 0, MyGdxGame.WIDTH * 3, MyGdxGame.HEIGHT * 3);
        sb.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(sb,"\n" +"\n" + "Game Desing and Programming\n" + "Michael Welser\n" + "Artwork\n" + "Rocketbeans.tv\n"  + "Music\n"
                + "Rocketbeans.tv\n"+ "Dennis Paluch" +"\n", Gdx.graphics.getWidth()/100, Gdx.graphics.getHeight()/3);
        //edlaser.setColor(Color.RED);
        //edlaser.draw(sb, ed, (MyGdxGame.WIDTH / 3) + (MyGdxGame.WIDTH / 2) + (plyBtn.getWidth() / 2), MyGdxGame.HEIGHT - MyGdxGame.HEIGHT * 2, 500, 200, false);
        sb.draw(plyBtn, (Gdx.graphics.getWidth() / 3 - plyBtn.getWidth()), Gdx.graphics.getHeight() / 2, Gdx.graphics.getHeight()/3, Gdx.graphics.getWidth()/4);
        sb.draw(plyBtnL, (Gdx.graphics.getWidth() /3 - plyBtnL.getWidth()), Gdx.graphics.getHeight() / 3/2 *2, Gdx.graphics.getHeight()/3, Gdx.graphics.getWidth()/4);
        sb.draw(logo, 0, Gdx.graphics.getHeight() /2 + Gdx.graphics.getHeight()/7, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() /3);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        plyBtn.dispose();
        plyBtnL.dispose();
        logo.dispose();
        font.dispose();
        System.out.print("Menu Stiate Disposed");
    }
}
