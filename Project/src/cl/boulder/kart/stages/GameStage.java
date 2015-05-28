package cl.boulder.kart.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.IOException;
import java.util.ArrayList;

import cl.boulder.kart.MyKartGame;
import cl.boulder.kart.models.Bar;
import cl.boulder.kart.models.Button;
import cl.boulder.kart.models.Car;
import cl.boulder.kart.models.Deco;
import cl.boulder.kart.models.Dialog;
import cl.boulder.kart.models.Fissure;
import cl.boulder.kart.models.Goal;
import cl.boulder.kart.models.HighScoreView;
import cl.boulder.kart.models.Pipe;
import cl.boulder.kart.models.Road;
import cl.boulder.kart.utils.MapItem;
import cl.boulder.kart.utils.MapLoader;

public class GameStage extends Stage {

    private Group obstacles, grounds;
    private MapLoader mapLoader;
    private MyKartGame game;
    public Road road;
    private Bar bar;
    public String track;
    public Car car;
    private String carDesc;
    private HighScoreView hgv;
    private Music bgmusic;

    public GameStage(Viewport v, MyKartGame kg, String p){
        super(v);
        game = kg;
        mapLoader = new MapLoader();
        String[] params = p.split("_");
        track = params[0];
        carDesc = params[1];
        playBGMusic();
    }

    private void playBGMusic(){
        if(bgmusic==null)
            bgmusic = Gdx.audio.newMusic(Gdx.files.internal("sfx/bgmusic.mp3"));
        bgmusic.play();
        bgmusic.setLooping(true);
        bgmusic.setVolume(0.5f);
    }

    public void loadLevel() throws IOException {
        ArrayList<MapItem> mapData = mapLoader.getMapData(track);
        for(MapItem item : mapData){
            switch(item.getType()){
                case 'K':
                    car = new Car(game.view,game.controller,item.getX(),item.getY(),carDesc);
                    this.addActor(car);
                    bar.setCar(car);
                    break;
                case 'P':
                    if(obstacles==null){
                        obstacles = new Group();
                        this.addActor(obstacles);
                    }
                    Pipe p = new Pipe(game,item.getX(),item.getY());
                    obstacles.addActor(p);
                    break;
                case '#':
                    if(road!=null){
                        bar = new Bar(game.view,game.controller);
                        this.addActor(bar);
                        break;
                    }
                    road = new Road((int) item.getX(),game.view);
                    game.controller.setTotalLaps((int) item.getY());
                    this.addActor(road);
                    break;
                case 'L':
                    road.addPoint(item.getY(),'L');
                    break;
                case 'R':
                    road.addPoint(item.getY(),'R');
                    break;
                case 'F':
                    road.addPoint(item.getY(),'F');
                    break;
                case 'X':
                    if(grounds==null){
                        grounds = new Group();
                        this.addActor(grounds);
                    }
                    Fissure f = new Fissure(game,item.getX(),item.getY());
                    grounds.addActor(f);
                    break;
                case 'G':
                    if(grounds==null){
                        grounds = new Group();
                        this.addActor(grounds);
                    }
                    Goal g = new Goal(game,item.getX(),item.getY());
                    grounds.addActor(g);
                    break;
            }
        }
        for (int i = 0; i < 3; i++) {
            if(grounds==null){
                grounds = new Group();
                this.addActor(grounds);
            }
            Deco d = new Deco(game,"bush",1,3*i,track);
            grounds.addActor(d);
        }
        for (int i = 0; i < 2; i++) {
            Deco d = new Deco(game,"roadline",0,4*i+2,track);
            grounds.addActor(d);
        }
        if(grounds!=null)
            grounds.toBack();
        road.toBack();
        car.setZIndex(this.getActors().size);
        if(hgv!=null)
            hgv.toFront();
        //this.getViewport().update(game.view.w,game.view.h);
    }

    public Road getRoad() {
        return road;
    }

    public void showGameOverDialog(){
        Dialog go = new Dialog(game.view,300,100);
        go.setTitle("Game Over");
        Button b = new Button("Reintentar",160,20,100,40,new Runnable() {
            @Override
            public void run() {
                game.view.changeStage('G',track+"_"+carDesc);
            }
        });
        go.setButton(b);
        Button b2 = new Button("Volver",40,20,100,40,new Runnable() {
            @Override
            public void run() {
                game.view.changeStage('M',"");
            }
        });
        go.setButton(b2);
        this.addActor(go);
    }

    @Override
    public void dispose(){
        super.dispose();
        bgmusic.stop();
        bgmusic.dispose();
    }

    public void changeHGV(int k, int time){
        String tp = "GSB";
        if(hgv!=null){
            hgv.remove();
        }
        if(k==3) return;
        hgv = new HighScoreView(game.view,time,tp.charAt(k));
        hgv.setPosition(440,450);
        this.addActor(hgv);
    }

    public void resetObstacles(){
        if(obstacles!=null){
            for(Actor a : obstacles.getChildren()){
                ((Pipe) a).hitable = true;
            }
        }
        if(grounds!=null){
            for(Actor a : grounds.getChildren()){
                if(a!=null && a.getClass()==Fissure.class){
                    ((Fissure) a).hitable = true;
                }
            }
        }
    }

    public void showWinDialog() {
        Dialog go = new Dialog(game.view,400,250);
        go.setTitle("Circuito Finalizado!");
        Button b = new Button("Reintentar",220,10,100,40,new Runnable() {
            @Override
            public void run() {
                game.view.changeStage('G',track+"_"+carDesc);
            }
        });
        go.setButton(b);
        Button b2 = new Button("Volver",80,10,100,40,new Runnable() {
            @Override
            public void run() {
                game.view.changeStage('M',"");
            }
        });
        go.setButton(b2);
        this.addActor(go);
    }
}
