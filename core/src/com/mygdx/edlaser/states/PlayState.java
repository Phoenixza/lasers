package com.mygdx.edlaser.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.edlaser.MyGdxGame;

import java.util.Random;



/**
 * Created by welser on 03.08.2015.
 */
public class PlayState extends State {
    private static final int TUBE_SPACING = 180;
    private static final int TUBE_COUNT = 4;
    private com.mygdx.edlaser.sprites.Bird bird;
    private Texture bg = new Texture("bgd.png");
    private Texture group = new Texture("group.png");
    private BitmapFont font;
    private Random rand;
    private Music sound;
    private Texture team = new Texture("rb.png");
    public static String highscore;
    public static CharSequence str = "DAS IST UNMOEGLICH!";
    public static CharSequence stra = "Lasergeschwindigkeit!";
    CharSequence goal = "Ziel";
    private Texture ground;
    private Texture eddy = new Texture("eddy.png");
    private Array<com.mygdx.edlaser.sprites.Tube> tubes;
    private Vector2 groundPos1, groundPos2;
    private Texture bohne= new Texture("bohne.png");
    private Sound first;
    private Music second;
    private Sound third;
    private static CharSequence strend = "SPALTEN!";
    private Sound fourth;
    private static final int GROUND_Y_OFFSET = -60;
    private Texture snerd = new Texture("krakeddy.png");
    private Texture hex = new Texture("denis.png");
    private Texture schlo = new Texture("schlotzke.png");
    private Texture eddys = new Texture("eddys.png");
    private Texture groundfok;
    public Texture[] rdm = {snerd,hex,bohne,team,eddy};
    public int r0 = (int)(Math.random()*5);
    public int r1 = (int)(Math.random()*5);
    public int r2 = (int)(Math.random()*5);
    public int r3 = (int)(Math.random()*5);
    public int r4 = (int)(Math.random()*5);
    public int r5 = (int)(Math.random()*5);
    public int r6 = (int)(Math.random()*5);
    public int r7 = (int)(Math.random()*5);
    public int r8 = (int)(Math.random()*5);
    public int r9 = (int)(Math.random()*5);
    public static int character = 0;

    protected PlayState(GameStateManager gsm, int c) {
        super(gsm);
        character = c;
        //first = Gdx.audio.newSound(Gdx.files.internal("data/diesesmal.mp3"));


        rand = new Random();
        bird = new com.mygdx.edlaser.sprites.Bird(50,300);

        if(character == 1){
            second = Gdx.audio.newMusic(Gdx.files.internal("data/faust.mp3"));
            second.play();
        } if(character == 0){
            second = Gdx.audio.newMusic(Gdx.files.internal("data/cod.MP3"));
            second.play();
        }

        if(character == 3 || character == 2){
            character = character - 2;
        }

        if(character == 1){
            sound = Gdx.audio.newMusic(Gdx.files.internal("data/lasertheme.mp3"));
        } else {
            //sound = Gdx.audio.newMusic(Gdx.files.internal("data/lauchboymusic.mp3"));
            sound = Gdx.audio.newMusic(Gdx.files.internal("data/lasertheme.mp3"));
        }


        cam.setToOrtho(false, MyGdxGame.WIDTH/2, MyGdxGame.HEIGHT/2);


        //bg = new Texture("bgd.png");
        //group = new Texture("group.png");
        ground = new Texture("grounded.png");
        groundfok = new Texture("grounded.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth/2) + groundfok.getWidth(),GROUND_Y_OFFSET);
        tubes = new Array<com.mygdx.edlaser.sprites.Tube>();
        //snerd = new Texture("krakeddy.png");



        sound.play();
        //team = new Texture("rb.png");
        //eddy = new Texture("eddy.png");
        //bohne = new Texture("bohne.png");
        //hex = new Texture("denis.png");
        sound.setLooping(true);

        //http://www.java-gaming.org/topics/libgdx-queue-sound-effects/32864/view.html


        for (int i =1; i <= TUBE_COUNT; i++){
            tubes.add(new com.mygdx.edlaser.sprites.Tube(i*(TUBE_SPACING + com.mygdx.edlaser.sprites.Tube.TUBE_WIDTH)));

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
    public void update(float dt) {
        handleImput();
        updateGround();
        if(bird.getPosition().x > 3000){
            bird.update(dt*3/2);
        } else {
            bird.update(dt);
        }


        cam.position.x = bird.getPosition().x + 80;
        for(int i = 0; i < tubes.size; i++){
            com.mygdx.edlaser.sprites.Tube tube = tubes.get(i);
            if(cam.position.x - (cam.viewportWidth /2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x+((com.mygdx.edlaser.sprites.Tube.TUBE_WIDTH+TUBE_SPACING)*TUBE_COUNT));
            }

            if(tube.collides(bird.getBounce())){
                //gsm.set(new PlayState(gsm));
                //second.play();
                gsm.set(new GameOverState(gsm));
            }
        }
        //if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){
        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
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
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for(com.mygdx.edlaser.sprites.Tube tube: tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        //font.draw(sb, String.valueOf(bird.getPosition().x * rand.nextInt(100)+100)  , cam.position.x+39, cam.position.y+193);
        font.draw(sb, String.valueOf(bird.getPosition().x), cam.position.x + 39, cam.position.y + 193);
        //sb.draw(rdm[(int)(Math.random()*3)],1000, cam.position.y/5);
        if(character == 1){
            sb.draw(rdm[r0],1300, cam.position.y/5);
            sb.draw(rdm[r1],2100, cam.position.y/5);
            sb.draw(rdm[r2],2900, cam.position.y/5);
            sb.draw(rdm[r3],3900, cam.position.y/5);
            sb.draw(rdm[r4],4700, cam.position.y/5);
            sb.draw(rdm[r5],5500, cam.position.y/5);
            sb.draw(rdm[r6],6300, cam.position.y/5);
            sb.draw(rdm[r7],7100, cam.position.y/5);
            sb.draw(rdm[r8],7900, cam.position.y/5);
            sb.draw(rdm[r9],8600, cam.position.y/5);
        } else {
            sb.draw(bohne,1300, cam.position.y/5);
            sb.draw(bohne,2100, cam.position.y/5);
            sb.draw(bohne,2900, cam.position.y/5);
            sb.draw(bohne,3900, cam.position.y/5);
            sb.draw(bohne,4700, cam.position.y/5);
            sb.draw(bohne,5500, cam.position.y/5);
            sb.draw(bohne,6300, cam.position.y/5);
            sb.draw(bohne,7100, cam.position.y/5);
            sb.draw(bohne,7900, cam.position.y/5);
            sb.draw(bohne,8600, cam.position.y/5);
        }

        sb.draw(group, 290, 46);
        sb.draw(eddy, 590, cam.position.y/5);
        //sb.draw(hex, 1000, cam.position.y/5);
        //sb.draw(snerd, 1500, cam.position.y/5);
        //sb.draw(team, 2000, cam.position.y/5);
        //sb.draw(bohne,3000, cam.position.y / 2);
        //sb.draw(bohne,4000, cam.position.y/2);
        //sb.draw(bohne,5000, cam.position.y/2);
        //sb.draw(team, 5500, cam.position.y/5);
        //sb.draw(bohne, 6000, cam.position.y / 2);
        font.draw(sb, stra,2800,180);
        font.draw(sb,str,7200,180);
        //font.draw(sb,str,7400,180);
        //font.draw(sb,str,7600,180);
        sb.draw(team, 8000, cam.position.y/5);
        sb.draw(bohne,9000, cam.position.y / 2);
        font.draw(sb,strend, 9500, 180);
        sb.draw(groundfok, groundPos1.x, groundPos1.y);
        sb.draw(groundfok, groundPos2.x, groundPos2.y);

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for(com.mygdx.edlaser.sprites.Tube tube : tubes){
            tube.dispose();
            System.out.print("Play State Disposed");
        }
        //first.dispose();
        /*
        rdm[0].dispose();
        rdm[1].dispose();
        rdm[2].dispose();
        rdm[3].dispose();
        rdm[4].dispose();
        rdm[5].dispose();
        rdm[6].dispose();
        rdm[7].dispose();
        rdm[8].dispose();
        rdm[9].dispose(); */
        if (second != null){
            second.dispose();
        }
        groundfok.dispose();
        sound.dispose();
        group.dispose();
        hex.dispose();
        team.dispose();
        snerd.dispose();
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
