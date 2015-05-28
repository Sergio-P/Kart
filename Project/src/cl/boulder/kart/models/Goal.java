package cl.boulder.kart.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import cl.boulder.kart.MyKartGame;
import cl.boulder.kart.stages.GameStage;

public class Goal extends PerspectiveActor{

    public Goal(MyKartGame kg, float xi, float di){
        super(kg,"goal",xi,di);
    }


}