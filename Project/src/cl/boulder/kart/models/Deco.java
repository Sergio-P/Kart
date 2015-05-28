package cl.boulder.kart.models;

import com.badlogic.gdx.Gdx;

import java.util.Random;

import cl.boulder.kart.MyKartGame;

public class Deco extends PerspectiveActor{

    private boolean chgPos, lap;
    private Random rnd;
    private MyKartGame game;
    private String[] chgDecos;

    public Deco(MyKartGame kg, String t, float xi, float di, String track){
        super(kg,t,xi,di);
        game = kg;
        chgPos = !t.equals("roadline");
        if(chgPos) {
            rnd = new Random();
            getDecoSet(track);
            changeImg();
        }
        lap = false;
    }

    private void getDecoSet(String track) {
        if(track.equals("Hills"))
            chgDecos = new String[]{"bush", "bushg", "tree"};
        else if(track.equals("City"))
            chgDecos = new String[]{"farol","shadow","shadow"};
        else if(track.equals("Desert"))
            chgDecos = new String[]{"cactus", "rock"};
        else //Snow
            chgDecos = new String[]{"pine"};
    }


    @Override
    public void act(float d){
        super.act(d);
        if(yp<0 && !lap){
            dist = (dist+8+((chgPos)?rnd.nextFloat()*4:0));
            if(dist>road.getRoadLength()){
                lap = true;
                dist = dist%road.getRoadLength();
            }
            if(chgPos) {
                x = (1f + rnd.nextFloat() * 1.5f) * ((rnd.nextInt(2) == 0) ? 1 : -1);
                changeImg();
            }
        }
        else if(lap && yp>0)
            lap = false;
        if(!chgPos) {
            setRotation((400-xp)/20);
        }
    }

    private void changeImg(){
        int a = rnd.nextInt(chgDecos.length);
        image = game.view.atlas.createSprite(chgDecos[a]);
        setWidth(image.getWidth());
        setHeight(image.getHeight());
        setOrigin(getWidth()/2,getHeight()/2);
    }

}
