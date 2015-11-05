package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

/**
 * Created by welser on 03.08.2015.
 */
public class MenuState extends State {
    private Texture background;
    private Texture plyBtn;
    private BitmapFont edlaser;
    private String ed;
    BitmapFont font;
    private Texture logo;
    public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("ed.png");
        plyBtn = new Texture("playbtn.png");
        logo = new Texture("logo.png");
        font = new BitmapFont();
        font.getData().setScale(3,3);
    }

    @Override
    public void handleImput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));

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
        font.draw(sb, "Game Desing and Programming\n" + "Michael Welser\n" + "Artwork\n" + "Rocketbeans.tv\n"  + "Music\n"
                + "Rocketbeans.tv\n"+ "Jens Hienzsch" +"\n", Gdx.graphics.getWidth()/100, Gdx.graphics.getHeight()/3);
        //edlaser.setColor(Color.RED);
        //edlaser.draw(sb, ed, (MyGdxGame.WIDTH / 3) + (MyGdxGame.WIDTH / 2) + (plyBtn.getWidth() / 2), MyGdxGame.HEIGHT - MyGdxGame.HEIGHT * 2, 500, 200, false);
        sb.draw(plyBtn, (Gdx.graphics.getWidth() / 3 - plyBtn.getWidth()), Gdx.graphics.getHeight() / 2, Gdx.graphics.getHeight()/3, Gdx.graphics.getWidth()/4);
        sb.draw(logo, 0, Gdx.graphics.getHeight() /2 + Gdx.graphics.getHeight()/7, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() /3);

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        plyBtn.dispose();
        logo.dispose();
        font.dispose();
        System.out.print("Menu Stiate Disposed");
    }
}
