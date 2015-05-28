package cl.boulder.kart.models;

import cl.boulder.kart.GameView;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MiniGraphView extends Actor{

    private Sprite image;
    private BitmapFont font;
    private String text;

    public MiniGraphView(GameView view, int k, String t){
        image = view.atlas.createSprite("gr"+k);
        font = view.normal;
        text = t;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.setColor(getColor());
        batch.draw(image, getX(), getY());
        //font.getData().setScale(0.6f);
        font.setScale(0.6f);
        font.draw(batch,text,getX()-20,getY());
    }

}
