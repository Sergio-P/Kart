package cl.boulder.kart.models;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import cl.boulder.kart.GameController;
import cl.boulder.kart.GameView;
import cl.boulder.kart.MyKartGame;

public class Bar extends Actor{

    private Car car;
    private BitmapFont font,normfont;
    private GameController ctx;
    private Sprite heart;

    public Bar(GameView view, GameController controller){
        font = view.numeric;
        normfont = view.normal;
        ctx = controller;
        heart = view.atlas.createSprite("heart");
    }

    public void setCar(Car c){
        car = c;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        if(car==null) return;
        font.draw(batch,""+((int) (100*car.getVel()))/100f,710,480);
        font.draw(batch,String.format("%d:%02d",(int) ctx.getTime()/60,(int) ctx.getTime()%60),360,480);
        for (int i = 0; i < car.getLives(); i++) {
            batch.draw(heart,5+40*i,420);
        }
        normfont.setScale(0.6f);
        //normfont.getData().setScale(0.6f);
        normfont.setColor(Color.BLACK);
        normfont.draw(batch,ctx.getLapCaption(),10,478);
    }

}
