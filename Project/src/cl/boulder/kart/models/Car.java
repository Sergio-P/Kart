package cl.boulder.kart.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import cl.boulder.kart.GameController;
import cl.boulder.kart.GameView;
import cl.boulder.kart.stages.GameStage;

public class Car extends Actor {

    private Sprite image;
    private Sound sfcrash,sfacc,sfbreak,sfjump;
    private float x, z, dist, vel, veljump;
    private int inerx, inputx;
    private boolean isAcc, isJump, limMaxVel;
    private Road road;
    private int lives;
    private GameController ctx;

    //Constantes
    private final float maxvel;
    private final float acc;
    private final float handling;


    public Car(GameView view, GameController controller, float xi, float di, String carDesc){
        ctx = controller;

        String[] params = carDesc.split(":");
        image = view.atlas.createSprite(params[0]);
        maxvel = Float.parseFloat(params[1]);
        acc = Float.parseFloat(params[2]);
        handling = Float.parseFloat(params[3]);

        road = ((GameStage) view.getCurrentStage()).getRoad();

        reset();
        setPos(xi, di);

        setWidth(image.getWidth());
        setHeight(image.getHeight());
        setBounds(0, 0, getWidth(), getHeight());
        setOrigin(getWidth()/2,getHeight()/2);

        sfacc = Gdx.audio.newSound(Gdx.files.internal("sfx/acc.wav"));
        sfbreak = Gdx.audio.newSound(Gdx.files.internal("sfx/break.wav"));
        sfcrash = Gdx.audio.newSound(Gdx.files.internal("sfx/crash.wav"));
        sfjump = Gdx.audio.newSound(Gdx.files.internal("sfx/jump.wav"));
    }

    private void reset(){
        isAcc = false;
        vel=0;
        inerx = 0;
        inputx = 0;
        lives = 3;
        isJump = false;
        veljump = 0;
        z = 0;
    }

    //Establece la posicion en el mapa
    public void setPos(float xi, float di){
        x = xi;
        dist = di;
    }

    //Indica si se esta acelerando
    public void accelerate(boolean up){
        if(isJump) return;
        isAcc = up;
    }

    //Indica la direccion del giro en x
    public void xRot(int k){
        inputx = k;
        limMaxVel = (k!=0);
    }

    @Override
    public void act(float d){
        if(lives<=0) return;
        //Velocidad Adelante
        if(isAcc){
            float realmaxvel = (limMaxVel)?maxvel*0.8f:maxvel;
            vel = (vel>=realmaxvel)?realmaxvel:(vel+d*acc);
            if(vel<0.7f*maxvel){
                sfacc.stop();
                sfacc.play();
            }
        }
        else{
            vel = (vel<=0)?0:(vel-d*((isJump)?(8-acc*0.5f):acc));
            if(!isJump && vel>=0.7f*maxvel){
                sfbreak.stop();
                sfbreak.play();
            }
        }

        //Inercia Pista Lateral
        char roadType = road.getTypeAt(dist);
        if(roadType=='L')
            inerx = 1;
        else if(roadType=='R')
            inerx = -1;
        else
            inerx = 0;
        //Posicion en el mapa
        if(vel!=0){
            dist += vel*d;
            //Check new lap
            if(dist>road.getRoadLength()){
                dist = dist%road.getRoadLength();
                ctx.lapComplete();
            }
            x += (inerx+inputx*1.1f)*handling*d;
            road.updateVertx(dist,x);
            //Choque lateral
            if(x>25 || x<-25){
                vel=0;
                sfcrash.stop();
                sfcrash.play();
                x=(x>0)?24:-24;
            }
        }
        //Salto
        if(isJump){
            z += veljump*d;
            veljump -= 400*d;
            setScale(1+z/200);
            if(z<=0){
                z=0;
                setScale(1);
                isJump=false;
            }
        }
    }

    public float getDist() {
        return dist;
    }

    public float getVel(){
        return vel;
    }

    public int getLives(){
        return lives;
    }

    public boolean hit(int dam){
        if(isJump) return false;
        lives -= dam;
        vel=0;
        if(lives<=0)
            ctx.gameOver();
        sfcrash.play();
        return true;
    }

    public void jump(){
        if(isJump) return;
        isJump = true;
        veljump = 200;
        isAcc = false;
        sfjump.play();
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.setColor(getColor());
        batch.draw(image,400-getWidth()/2,20+z,0,0,getWidth(),getHeight(),getScaleX(),getScaleY(),0);
    }


    public void setVel(int v) {
        if(vel>v)
            vel = v;
    }
}
