package cl.boulder.kart.models;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import cl.boulder.kart.MyKartGame;
import cl.boulder.kart.stages.GameStage;

public class PerspectiveActor extends Actor {

    public float x,dist,xp,yp;
    public boolean visible;
    protected Road road;
    public Car car;
    protected Sprite image;

    public PerspectiveActor(MyKartGame kg, String imgstr, float xi, float di){
        image = kg.view.atlas.createSprite(imgstr);
        setWidth(image.getWidth());
        setHeight(image.getHeight());
        setOrigin(getWidth()/2,getHeight()/2);
        road = ((GameStage) kg.view.getCurrentStage()).getRoad();
        setPos(xi,di);
    }

    private void setPos(float xi, float di) {
        x = xi;
        dist = di;
    }

    @Override
    public void act(float d){
        if(car==null) car = ((GameStage)getStage()).car;
        yp = (dist-car.getDist())*25;
        visible = (yp>0 && yp<=200);
        if(visible){
            int at = (int) ((dist-car.getDist())/2) + 1;
            float rem = ((dist-car.getDist())/2) - (at-1);
            float xb = road.getVertx()[2*at]*(1+x)*0.5f + road.getVertx()[2*at+1]*(1-x)*0.5f;
            at--;
            float xa = road.getVertx()[2*at]*(1+x)*0.5f + road.getVertx()[2*at+1]*(1-x)*0.5f;
            xp = xa + (xb-xa)*rem;
            setScale(1.2f-(dist-car.getDist())/8);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        if(visible){
            batch.setColor(getColor());
            batch.draw(image,xp-getWidth()*getScaleX()/2,yp,0,0,getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
        }
    }



}
