package cl.boulder.kart.models;

public class Button{

    public int x,y,h,w;
    public String text;
    public Runnable callback;

    public Button(String txt, int xi, int yi, int wi, int hi, Runnable cb){
        x = xi;
        y = yi;
        h = hi;
        w = wi;
        text = txt;
        callback = cb;
    }

}