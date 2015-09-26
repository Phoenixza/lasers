package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.MyGdxGame;
import java.util.HashMap;
import java.util.Map;
import us.monoid.json.JSONException;

/***
 * Created by welser on 04.08.2015.
 */
public class GameOverState extends State implements Net.HttpResponseListener, Input.TextInputListener{
    private String urlg = "http://141.28.100.152/phpandroid/get_all.php";
    private String urlp = "http://141.28.100.152/phpandroid/highscore.php";
    private Texture bg;
    private BitmapFont go;
    private CharSequence gameOver = "Game Over \n\nHighscore:\n "  + PlayState.highscore;
    private String f = PlayState.highscore;
    private BitmapFont topscore;
    private Texture plyBtn;
    private TextField.TextFieldStyle style;
    private TextField textfield;
    public String txtVal;
    private Music anders;
    private Skin skin;
    private Table table;
    private Stage stage;
    private String value;
    private String [][]arr;
    private String maxScore;
    public int i = 0;
    public String [] points = new String[10];
    public String [] name = new String[10];
    public Array<Highscoredata> highscoredata;
    public boolean touch = false;

    protected GameOverState(GameStateManager gsm) throws JSONException {
        super(gsm);
        //anders = Gdx.audio.newMusic(Gdx.files.internal("data/faust.mp3"));
        anders = Gdx.audio.newMusic(Gdx.files.internal("data/diesesmal.mp3"));
        bg = new Texture("ed.png");
        cam.setToOrtho(false, MyGdxGame.WIDTH/2, MyGdxGame.HEIGHT/2);
        go = new BitmapFont();
        topscore = new BitmapFont();
        plyBtn = new Texture("playbtn.png");
        arr = new String[1][1];
        PlayerScore score = new PlayerScore(1231.12, "user");
        Json obj = new Json();
        obj.toJson(score);
        maxScore = "test";
        getData();
        anders.play();
        anders.setLooping(true);

        //if((Float.parseFloat(PlayState.highscore) > Float.parseFloat((points[9])))){
          //  Gdx.input.getTextInput(GameOverState.this, "Higscore Liste", null, "Eduard Laser");
       // }
        //Gdx.input.getTextInput(GameOverState.this, "Higscore Liste", null, "Eduard Laser");



    }

    @Override
    protected void handleImput() {

        Vector3 touchPos = new Vector3();
        if(Gdx.input.isTouched() && (touch == true)){
            // New Game
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            cam.unproject(touchPos);

               // if(touchPos.x > 70+plyBtn.getWidth() | touchPos.x<70 & touchPos.y > 155 + plyBtn.getHeight() | touchPos.y < 140){


                    //gsm.set(new PlayState(gsm));
               // } else {
                    if(txtVal != null){
                        postData();
                        gsm.set(new PlayState(gsm));
                    } else{
                        gsm.set(new PlayState(gsm));
                    }

               // }
        } /*
        if(Gdx.input.isTouched()){
            gsm.set(new PlayState(gsm));
        }*/





    }

    @Override
    public void update(float dt) {
        handleImput();
    }

    public void postData(){
        Map<String, String> parameters;
        parameters = new HashMap<String, String>();
        parameters.put(f, "user");
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl(urlp);
        String sent = "";
        if(txtVal.length()>11){
            sent = txtVal.substring(0, 12);
        }
        else {
            sent = txtVal;
        }
        request.setContent("points=" + PlayState.highscore + "&name=" + sent);

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
            }

            @Override
            public void failed(Throwable t) {
            }

            @Override
            public void cancelled() {

            }
        });
    }

    public void getData(){
        String requestContent = null;
        Map<String, String> parameters;
        parameters = new HashMap<String, String>();
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(urlg);
        request.setHeader("Content-Type", "text/plain");
        request.setContent(requestContent);
        //request.setContent(HttpParametersUtils.convertHttpParameters(parameters));
        

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                final int statusCode = httpResponse.getStatus().getStatusCode();
                System.out.println(statusCode);
                System.out.println("Content: ");
                String result = httpResponse.getResultAsString();
                System.out.println(result);
                //{
                //  "edlaser": [
                //      {"points":"points","name":"name"},
                //      {"points":"points","name":"name"},
                //      {"points":"points","name":"name"}
                //  ]
                // }
                
                JsonValue json = new JsonReader().parse(result);
                //Array<Highscoredata> highscoredata = new Array<Highscoredata>();
                highscoredata = new Array<Highscoredata>();
                JsonValue highscoreJson = json.get("edlaser");
                for (JsonValue highscoreJsons : highscoreJson.iterator()){
                    Highscoredata newHighScore = new Highscoredata();
                    newHighScore.points = highscoreJsons.getString("points");
                    points[i] = highscoreJsons.getString("points");
                    name[i] = highscoreJsons.getString("name");
                    System.out.println(points[i]);
                    System.out.println(name[i]);
                    newHighScore.name = highscoreJsons.getString("name");
                    highscoredata.add(newHighScore);
                    i++;
                }

                if((Float.parseFloat(PlayState.highscore) > Float.parseFloat((points[9])))){
                    Gdx.input.getTextInput(GameOverState.this, "Higscore Liste", null, "Eduard Laser");
                    //touch = true;
                }
                //Gdx.input.getTextInput(GameOverState.this, "Higscore Liste", null, "Eduard Laser");
                touch = true;
            }

            @Override
            public void failed(Throwable t) {
                value = "failed";
            }

            @Override
            public void cancelled() {

            }
        });

    }



    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportHeight / 2), 0);
        go.draw(sb, gameOver, 90, 350);
        topscore.draw(sb,"\n############################" + "\n#Bestenliste                                  #\n" +"############################"
                +"\n#Platz   1 " + name[0] + " " + points[0] + "\n#Platz   2 " + name[1] + " " + points[1] + "\n#Platz   3 " + name[2] + " " + points[2]
                +"\n#Platz   4 " + name[3] + " " + points[3] + "\n#Platz   5 " + name[4] + " " + points[4] + "\n#Platz   6 " + name[5] + " " + points[5]
                +"\n#Platz   7 " + name[6] + " " + points[6] + "\n#Platz   8 " + name[7] + " " + points[7] + "\n#Platz   9 " + name[8] + " " + points[8]
                +"\n#Platz 10 " + name[9] + " " + points[9] +"\n############################", 5, 270);
        //sb.draw(plyBtn, 180, 350, 50, 40);
        //sb.draw(plyBtn, (MyGdxGame.WIDTH/2)+(MyGdxGame.WIDTH/2), MyGdxGame.HEIGHT, 300 , 220);
        sb.end();
    }

    @Override
    public void dispose() {
        anders.dispose();
        bg.dispose();
        go.dispose();
        topscore.dispose();
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {

    }

    @Override
    public void failed(Throwable t) {

    }

    @Override
    public void cancelled() {

    }

    @Override
    public void input(String text) {
        txtVal = text;
    }

    @Override
    public void canceled() {

    }

}

