package cl.boulder.kart;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.IOException;

import cl.boulder.kart.models.Car;
import cl.boulder.kart.models.Pipe;
import cl.boulder.kart.stages.GameStage;

public class MyKartGame extends ApplicationAdapter {
    public GameController controller;
	public GameView view;

	@Override
	public void create () {
		controller = new GameController(this);
        view = new GameView(this);
        view.changeStage('M',"");
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(view.bgcolor.r,view.bgcolor.g,view.bgcolor.b,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		controller.update(Gdx.graphics.getDeltaTime());
        view.update(Gdx.graphics.getDeltaTime());
	}

    @Override
    public void resize(int width, int height){
        view.resize(width,height);
    }
}
