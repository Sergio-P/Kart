package cl.boulder.kart.models;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.Color;


import cl.boulder.kart.GameView;

public class Road extends Actor {

    private float[] dists;
    private char[] types;
    private ShapeRenderer render;
    private int fillidx, n;
    private float vertx[], verty[];
    private float path[], smoothpath[];
    private Sprite bg;
    private GameView view;

    public Road(int ni, GameView gv){
        view = gv;
        Gdx.app.log("HOLA",""+view.w);
        float sx = view.w/800f;
        float sy = view.h/480f;
        render = new ShapeRenderer();
        render.setAutoShapeType(true);
        render.scale(sx,sy,1);
        render.setColor(Color.valueOf("999999"));
        bg = view.bgsprite;
        n = ni;
        dists = new float[n];
        types = new char[n];
        fillidx = 0;
        vertx = new float[10];
        verty = new float[]{0f, 50f, 100f, 150f, 200f};
        path = new float[5];
        smoothpath = new float[5];
        vertx[0] = 700;
        vertx[1] = 100;
        updateVertx(0,0);
    }

    public void addPoint(float d, char t){
        dists[fillidx] = d;
        types[fillidx] = t;
        fillidx++;
    }

    public char getTypeAt(float dist){
        dist = dist%dists[n-1];
        int k=0;
        while(k<n && dists[k]<dist)
            k++;
        return types[k];
    }

    public void updateVertx(float dist, float xoff){
        path[0] = 0;
        vertx[0] = 700 - 10*xoff;
        vertx[1] = 100 - 10*xoff;

        for (int i = 1; i < 5; i++){
            smoothpath[i] = smoothpath[i]*0.9f + path[i]*0.1f;
            char t = getTypeAt(dist+2*i);
            if(t=='F')
                path[i] = path[i-1];
            else if(t=='R')
                path[i] = path[i-1]*1.3f + 40;
            else if(t=='L')
                path[i] = path[i-1]*1.3f - 40;
            vertx[2*i] = 400 - 10*xoff +smoothpath[i]+ (600-120*i)/2;
            vertx[2*i+1] = 400 - 10*xoff + smoothpath[i] - (600-120*i)/2;
        }
    }

    public float[] getVertx(){
        return vertx;
    }

    public float getRoadLength(){
        return dists[n-1];
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(bg,0,180);
        batch.end();
        render.begin();
        render.set(ShapeRenderer.ShapeType.Filled);
        for (int i = 2; i < 10; i++) {
            render.triangle(vertx[i-2], verty[(i-2)/2], vertx[i-1], verty[(i-1)/2], vertx[i], verty[i/2]);
        }
        render.end();
        batch.begin();
    }

}
