package cl.boulder.kart.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Hashtable;

/*
IMPORTANT: This class has been protected since this class provides information and passwords about the highscore server.
*/
public class HighScoreService{

    private JsonValue json;
    private Runnable callback;

    public HighScoreService(){
        getHighScores(false);
    }

    public void setOnLoadHS(Runnable cb){
        callback = cb;
    }

    public void getHighScores(boolean update) {
        return;
    }

    public void putHighScore(String track, int time, String name){
        return;
    }

    public String getHighscoreAsText(String track){
        String out = "";
        return out;
    }

    public boolean isLoaded(){
        return (json!=null);
    }

    public int[] getHighscoreTimes(String track){
        return null;
    }

}
