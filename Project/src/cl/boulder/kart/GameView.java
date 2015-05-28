package cl.boulder.kart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.io.IOException;

import cl.boulder.kart.stages.GameStage;
import cl.boulder.kart.stages.MenuStage;
import cl.boulder.kart.utils.MapLoader;

public class GameView {

    private Stage stage;
    public TextureAtlas atlas;
    public BitmapFont normal = new BitmapFont(Gdx.files.internal("font/normal.fnt"));
    public BitmapFont numeric = new BitmapFont(Gdx.files.internal("font/numbers.fnt"));

    public String[] tracks = {"Hills","City","Desert","Snow"};
    //String[] cars = {"carname:maxvel:acc:handling" , ... }
    public String[] cars = {"Blue:8:12:20","Gray:9.5:8:14","Red:10:5:12","Yellow:12:3:10"};
    public int w = 800;
    public int h = 480;

    private MyKartGame game;

    public Color bgcolor;
    public Sprite bgsprite;

    public GameView(MyKartGame kg){
        atlas = new TextureAtlas(Gdx.files.internal("gpx/Kart.pack"));
        game = kg;
    }

    public Stage getCurrentStage(){
        return stage;
    }

    public void update(float d){
        stage.act(d);
        stage.draw();
    }

    public void resize(int width, int height){
        stage.getViewport().update(width,height);
        w = width;
        h = height;
    }

    public void changeStage(char st, String param){
        if(stage!=null){
            stage.clear();
            stage.dispose();
        }
        if(st=='G'){
            stage = new GameStage(new StretchViewport(800,480),game,param);
            stage.getViewport().update(w,h);
            String track = param.split("_")[0];
            game.controller.wakeUp(track);
            setBGTrack(track);
            try {
                ((GameStage) stage).loadLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(st=='M'){
            stage = new MenuStage(new StretchViewport(800,480),game);
            stage.getViewport().update(w,h);
            game.controller.pause();
            bgcolor = new Color(0.05f, 0.3f, 0.55f, 1);
        }
        Gdx.input.setInputProcessor(stage);
    }

    private void setBGTrack(String track) {
        if(track.equals("Hills")) {
            bgcolor = new Color(0x226E2EFF);
            bgsprite = new Sprite(new Texture(Gdx.files.internal("gpx/bg/Hills.png")));
        }
        else if(track.equals("Desert")){
            bgcolor = new Color(0xF5A547FF);
            bgsprite = new Sprite(new Texture(Gdx.files.internal("gpx/bg/Desert.png")));
        }
        else if(track.equals("Snow")) {
            bgcolor = new Color(0xFFFFFFFF);
            bgsprite = new Sprite(new Texture(Gdx.files.internal("gpx/bg/Snow.png")));
        }
        else if(track.equals("City")) {
            bgcolor = new Color(0x050049FF);
            bgsprite = new Sprite(new Texture(Gdx.files.internal("gpx/bg/City.png")));
        }
    }

}
