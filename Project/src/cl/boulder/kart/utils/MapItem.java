package cl.boulder.kart.utils;

public class MapItem{

    private float x,y;
    private char t;

    public MapItem(float xi, float yi, char ti){
        x = xi;
        y = yi;
        t = ti;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public char getType(){
        return t;
    }

}