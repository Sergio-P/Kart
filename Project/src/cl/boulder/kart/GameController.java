package cl.boulder.kart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;

import cl.boulder.kart.models.Car;
import cl.boulder.kart.models.HighScoreView;
import cl.boulder.kart.stages.GameStage;
import cl.boulder.kart.utils.HighScoreService;

public class GameController {
    //Controlador
    private Car car;
    private MyKartGame game;
    private float time;
    private boolean paused;
    public HighScoreService hgs;
    private int[] highscores;
    private int hsAt, lap, totalLaps;
    private Sound sflap;
    private String name;

    public GameController(MyKartGame kg){
        game = kg;
        time = 0;
        paused = false;
        hgs = new HighScoreService();
        sflap = Gdx.audio.newSound(Gdx.files.internal("sfx/lap.wav"));
    }
    
    public void update(float d){
        if(paused) return;
        if(car==null) car = ((GameStage) game.view.getCurrentStage()).car;
        //Time - Highscore control
        time +=d;
        if(hsAt!=3 && time>highscores[hsAt]){
            hsAt++;
            ((GameStage) game.view.getCurrentStage()).changeHGV(hsAt,highscores[hsAt%3]);
        }
        //Accelerometer & Touch Control
        boolean k = false;
        boolean acel = false;
        //Gdx.app.log("Acc",""+Gdx.input.getAccelerometerY());
        if(Gdx.input.getAccelerometerY()>1.5) {
            car.xRot(1);
            acel = true;
        }
        else if(Gdx.input.getAccelerometerY()<-1.5) {
            car.xRot(-1);
            acel = true;
        }
        if(Gdx.input.isTouched(0)){
            if(Gdx.input.getX(0)>400)
                k = true;
            else
                car.jump();
        }
        if(Gdx.input.isTouched(1)){
            if(Gdx.input.getX(1)>400)
                k = true;
            else
                car.jump();
        }
        //Keyboard Control
        if(!acel && Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            car.xRot(1);
        else if(!acel && Gdx.input.isKeyPressed(Input.Keys.LEFT))
            car.xRot(-1);
        else if(!acel)
            car.xRot(0);
        if(Gdx.input.isKeyPressed(Input.Keys.Z))
            k = true;
        car.accelerate(k);
        if(Gdx.input.isKeyPressed(Input.Keys.X))
            car.jump();
    }
    
    public float getTime(){
        return time;
    }

    public void gameOver(){
        paused = true;
        ((GameStage) game.view.getCurrentStage()).showGameOverDialog();
    }

    public void wakeUp(String track){
        paused = false;
        time = 0;
        lap = 0;
        car = ((GameStage) game.view.getCurrentStage()).car;
        highscoreForTrack(track);
    }

    public void pause(){
        paused = true;
    }

    public void highscoreForTrack(String track){
        hsAt = 3;
        if(game.controller.hgs.isLoaded()){
            highscores = game.controller.hgs.getHighscoreTimes(track);
            if(highscores==null) return;
            hsAt = 0;
            ((GameStage) game.view.getCurrentStage()).changeHGV(0,highscores[hsAt]);
        }
    }

    public void setTotalLaps(int tl){
        totalLaps = tl;
    }

    public void lapComplete(){
        lap++;
        sflap.play();
        ((GameStage) game.view.getCurrentStage()).resetObstacles();
        if(lap>=totalLaps){
            Gdx.app.log("Game","Win! :D");
            name = "";
            hgs.setOnLoadHS(new Runnable() {
                @Override
                public void run() {
                    drawWinHS();
                }
            });
            if(hsAt!=3) {
                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input(String text) {
                        setName(text);
                        hgs.putHighScore(((GameStage) game.view.getCurrentStage()).track, (int) time,name);
                        //hgs.getHighScores(true);
                    }
                    @Override
                    public void canceled(){
                        hgs.getHighScores(true);
                    }
                },"Nuevo Highscore!","","Nombre");
            }
            else{
                hgs.getHighScores(true);
            }
            paused = true;
            car.accelerate(false);
            car.setVel(4);
            ((GameStage) game.view.getCurrentStage()).showWinDialog();
        }
    }

    private void drawWinHS(){
        String a = hgs.getHighscoreAsText(((GameStage) game.view.getCurrentStage()).track);
        String[] hgs = a.split("&");
        String t = "GSV";
        for (int j = 0; j < hgs.length; j++) {
            String[] ac = hgs[j].split(":");
            HighScoreView hgv = new HighScoreView(game.view,ac[0],Integer.parseInt(ac[1]),t.charAt(j));
            if(j==0)
                hgv.setPosition(340,270);
            else
                hgv.setPosition(260+160*(j-1),200);
            game.view.getCurrentStage().addActor(hgv);
        }
    }

    public void setName(String k){
        name = k;
    }

    public String getLapCaption(){
        return "Vuelta: "+lap+" / "+totalLaps;
    }

}
