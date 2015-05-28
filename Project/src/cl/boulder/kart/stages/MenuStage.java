package cl.boulder.kart.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import cl.boulder.kart.MyKartGame;
import cl.boulder.kart.models.Button;
import cl.boulder.kart.models.Dialog;
import cl.boulder.kart.models.HighScoreView;
import cl.boulder.kart.models.MiniGraphView;

public class MenuStage extends Stage {

    private MyKartGame game;
    private Dialog cc, ct;
    private String carDesc;

    public MenuStage(Viewport v, MyKartGame kg){
        super(v);
        game=kg;
        //createChooseTrackDialog();
        createChooseCarDialog();
    }

    private void createChooseCarDialog(){
        if(cc!=null){
            cc.setVisible(true);
            return;
        }
        cc = new Dialog(game.view,800,480);
        cc.setTitle("Elegir Auto");
        this.addActor(cc);
        for (int i = 0; i < game.view.cars.length; i++) {
            final int finalI = i;
            String[] params = game.view.cars[i].split(":");
            Button b = new Button(params[0],100,340-100*i,600,85,new Runnable() {
                @Override
                public void run() {
                    carDesc = game.view.cars[finalI];
                    cc.setVisible(false);
                    createChooseTrackDialog();
                }
            });
            cc.setButton(b);
            createMiniGraphs(params,i);
        }
    }

    private void createMiniGraphs(String[] params, int i) {
        float k = Float.parseFloat(params[1]);
        MiniGraphView mgv = new MiniGraphView(game.view,1+((k>8)?1:0)+((k>10)?1:0),"Vel: "+k);
        this.addActor(mgv);
        mgv.setPosition(200,370-100*i);
        k = Float.parseFloat(params[2]);
        mgv = new MiniGraphView(game.view,1+((k>3)?1:0)+((k>8)?1:0),"Acc: "+k);
        this.addActor(mgv);
        mgv.setPosition(350,370-100*i);
        k = Float.parseFloat(params[3]);
        mgv = new MiniGraphView(game.view,1+((k>10)?1:0)+((k>13)?1:0),"Hand: "+k);
        this.addActor(mgv);
        mgv.setPosition(500,370-100*i);
    }

    private void createChooseTrackDialog() {
        ct = new Dialog(game.view,800,480);
        ct.setTitle("Elegir Pista");
        for (int i = 0; i < game.view.tracks.length; i++) {
            final int finalI = i;
            Button b = new Button(game.view.tracks[i],100,340-100*i,600,85,new Runnable() {
                @Override
                public void run() {
                    game.view.changeStage('G',game.view.tracks[finalI]+"_"+carDesc);
                }
            });
            ct.setButton(b);
        }
        this.addActor(ct);
        //HS
        if(!game.controller.hgs.isLoaded()){
            game.controller.hgs.setOnLoadHS(new Runnable() {
                @Override
                public void run() {
                    createHighScores();
                }
            });
        }
        else{
            createHighScores();
        }
    }

    public void createHighScores(){
        for (int i = 0; i < game.view.tracks.length; i++) {
            //HighScores
            String a = game.controller.hgs.getHighscoreAsText(game.view.tracks[i]);
            if(a.equals("")) continue;
            String[] hgs = a.split("&");
            String t = "GSV";
            for (int j = 0; j < hgs.length; j++) {
                String[] ac = hgs[j].split(":");
                HighScoreView hgv = new HighScoreView(game.view,ac[0],Integer.parseInt(ac[1]),t.charAt(j));
                hgv.setPosition(120+200*j,350-100*i);
                this.addActor(hgv);
            }
        }
    }


}
