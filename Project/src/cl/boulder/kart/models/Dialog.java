package cl.boulder.kart.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.ArrayList;

import cl.boulder.kart.GameView;


public class Dialog extends Actor {

    private BitmapFont font;
    private NinePatch shadow,button;
    private int w,h;
    private String title = "";
    private ArrayList<Button> buttons;

    public Dialog(GameView view, int width, int height){
        font = view.normal;
        shadow = view.atlas.createPatch("shadow");
        button = view.atlas.createPatch("button");
        w=width;
        h=height;
        buttons = new ArrayList<Button>();

        this.setBounds(400-w/2,240-h/2,w,h);

        this.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
               for(Button b : buttons){
                   if(x>=b.x && x<=b.x+b.w && y>=b.y && y<=b.y+b.h)
                       b.callback.run();
               }
               return false;
            }
        });

    }

    public void setTitle(String t){
        title = t;
    }

    public void setButton(Button b){
        buttons.add(b);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        shadow.draw(batch, 400 - w / 2, 240 - h / 2, w, h);
        font.setColor(Color.WHITE);
        font.setScale(1f);
        //font.getData().setScale(1f);
        font.draw(batch, title, 400 - w / 2 + 8, 240 + h / 2 - 4);
        font.setColor(Color.BLACK);
        font.setScale(0.6f);
        //font.getData().setScale(0.6f);
        for(Button b : buttons){
            //font.setScale(b.h/50f);
            button.draw(batch,b.x+400-w/2,b.y+240-h/2,b.w,b.h);
            font.draw(batch,b.text,b.x+400-w/2+8,b.y+b.h+240-h/2-8);
        }
    }

}
