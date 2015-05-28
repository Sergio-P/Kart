package cl.boulder.kart.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import cl.boulder.kart.GameView;

public class HighScoreView extends Actor{

    private Sprite image;
    private String name,time;
    private BitmapFont font;

    public HighScoreView(GameView view, String text, int sec, char type){
        name = text;
        time = String.format("%d:%02d",sec/60,(sec%60));
        font = view.normal;
        if(type=='G')
            image = view.atlas.createSprite("tgold");
        else if(type=='S')
            image = view.atlas.createSprite("tsilver");
        else
            image = view.atlas.createSprite("tbronze");
        setWidth(image.getWidth());
        setHeight(image.getHeight());
    }

    public HighScoreView(GameView view, int sec, char type){
        this(view,"",sec,type);
    }

     @Override
    public void draw(Batch batch, float parentAlpha){
         font.setScale(0.6f);
         //font.getData().setScale(0.6f);
         if(name.equals("")){
             font.setColor(Color.BLACK);
             batch.draw(image,getX(),getY(),getWidth()/2,getHeight()/2);
             font.draw(batch,time,getX()+24,getY()+22);
         }
         else{
             font.setColor(Color.WHITE);
             batch.draw(image,getX(),getY());
             font.draw(batch,name,getX()+50,getY()+42);
             font.draw(batch,time,getX()+50,getY()+22);
         }
     }


}
