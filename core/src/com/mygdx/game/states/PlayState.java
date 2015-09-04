package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Tube;

import java.util.Random;

import us.monoid.json.JSONException;


/**
 * Created by welser on 03.08.2015.
 */
public class PlayState extends State {
    private static final int TUBE_SPACING = 180;
    private static final int TUBE_COUNT = 4;
    private Bird bird;
    private Texture bg;
    private Texture group;
    private BitmapFont font;
    private Random rand;
    private Music sound;
    private Texture team;
    public static String highscore;
    public static CharSequence str = "DAS IST UNMOEGLICH!";
    CharSequence goal = "Ziel";
    private Texture ground;
    private Texture eddy;
    private Array<Tube> tubes;
    private Vector2 groundPos1, groundPos2;
    private Texture bohne;
    private Sound first;
    private Sound second;
    private Sound third;
    private Sound fourth;
    private static final int GROUND_Y_OFFSET = -60;
    private Texture snerd;
    private Texture hex;
    private Texture groundfok;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        first = Gdx.audio.newSound(Gdx.files.internal("data/sci_fi_machine_start_up.mp3"));
        //second = Gdx.audio.newSound(Gdx.files.internal("data/copute_voice.mp3"));
        rand = new Random();
        bird = new Bird(50,300);
        sound = Gdx.audio.newMusic(Gdx.files.internal("data/lasertheme.mp3"));
        cam.setToOrtho(false, MyGdxGame.WIDTH/2, MyGdxGame.HEIGHT/2);
        bg = new Texture("bgd.png");
        group = new Texture("group.png");
        ground = new Texture("grounded.png");
        groundfok = new Texture("grounded.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth/2) + groundfok.getWidth(),GROUND_Y_OFFSET);
        tubes = new Array<Tube>();
        snerd = new Texture("krakeddy.png");
        sound.play();
        team = new Texture("rb.png");
        eddy = new Texture("eddy.png");
        bohne = new Texture("bohne.png");
        hex = new Texture("denis.png");
        sound.setLooping(true);
        //http://www.java-gaming.org/topics/libgdx-queue-sound-effects/32864/view.html


        for (int i =1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i*(TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        font = new BitmapFont();
        //(Gdx.files.internal("assets/ven.fnt"),
                //Gdx.files.internal("assets/ven.png"), false);
    }

    @Override
    protected void handleImput() {
        if(Gdx.input.justTouched()){
            bird.jump();}
    }

    @Override
    public void update(float dt) throws JSONException {
        handleImput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;
        for(int i = 0; i < tubes.size; i++){
            Tube tube = tubes.get(i);
            if(cam.position.x - (cam.viewportWidth /2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x+((Tube.TUBE_WIDTH+TUBE_SPACING)*TUBE_COUNT));
            }

            if(tube.collides(bird.getBounce())){
                //gsm.set(new PlayState(gsm));
                gsm.set(new GameOverState(gsm));
            }
        }
        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){
            //gsm.set(new PlayState(gsm));
            gsm.set(new GameOverState(gsm));
        }
        if(cam.position.x == 500){
            first.play();
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        // only what the user should see
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        highscore = String.valueOf(bird.getPosition().x);
        sb.draw(bg, cam.position.x - (cam.viewportHeight / 2), 0);
        /*
        for(int i = 0; i < 1000; i=+100){

        Texture[] pics = new Texture[5];
            pics[0] = bohne;
            sb.draw(pics[0], cam.position.x*i, cam.position.y/2);
        }
        */
        sb.draw(bohne, cam.position.x/2, cam.position.y/2);
        sb.draw(eddy, 590, cam.position.y/5);

        sb.draw(bohne,3000, cam.position.y/2);
        sb.draw(bohne,4000, cam.position.y/2);
        sb.draw(bohne, 5000, cam.position.y/2);
        sb.draw(bohne, 6000, cam.position.y / 2);

        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for(Tube tube: tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        //font.draw(sb, String.valueOf(bird.getPosition().x * rand.nextInt(100)+100)  , cam.position.x+39, cam.position.y+193);
        font.draw(sb, String.valueOf(bird.getPosition().x) , cam.position.x+39, cam.position.y+193);
        font.draw(sb,str,7000,180);



        sb.draw(team, 800, cam.position.y/5);
        sb.draw(snerd, 1800, cam.position.y/5);
        sb.draw(hex, 2600, cam.position.y/5);
        sb.draw(groundfok, groundPos1.x, groundPos1.y);
        sb.draw(groundfok, groundPos2.x, groundPos2.y);
        sb.draw(group,290,46);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube : tubes){
            tube.dispose();
            System.out.print("Play State Disposed");
        }
        first.dispose();
        groundfok.dispose();
        sound.dispose();
        group.dispose();
    }

    private void updateGround(){
        if(cam.position.x - (cam.viewportWidth/2) > groundPos1.x + groundfok.getWidth()){
            groundPos1.add(groundfok.getWidth() * 2,0);
        }
        if(cam.position.x - (cam.viewportWidth/2) > groundPos2.x + groundfok.getWidth()){
            groundPos2.add(groundfok.getWidth() * 2,0);
        }

    }
}
