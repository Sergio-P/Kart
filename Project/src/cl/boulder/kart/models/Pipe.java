package cl.boulder.kart.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import cl.boulder.kart.MyKartGame;
import cl.boulder.kart.stages.GameStage;

public class Pipe extends PerspectiveActor{

    public boolean hitable;

    public Pipe(MyKartGame kg, float xi, float di){
        super(kg,"barrel",xi,di);
        hitable = true;
    }

    @Override
    public void act(float d){
        super.act(d);
        if(visible && hitable && checkCollision()){
            if(car.hit(1))
                hitable = false;
        }
    }

    private boolean checkCollision(){
        return (yp < 25 && xp > 350 && xp < 450);
    }

}
