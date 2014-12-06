package com.us.ld31;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.MenuOverlay;

public class LD31 implements ApplicationListener {
	
	private Stage stage;
	private PolygonSpriteBatch batch;
	
	private boolean initialized;
	private GameWorld gameWorld;
	private MenuOverlay menuOverlay;
	
	@Override
	public void create() {
		batch = new PolygonSpriteBatch(1000);
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(final int width, 
					   final int height) {
		
		stage.getViewport().update(width, height, true);
		
		if(!initialized) {
			gameWorld = new GameWorld(this);
			menuOverlay = new MenuOverlay(this);
			
			stage.addActor(gameWorld);
			stage.addActor(menuOverlay);
		}
		
		gameWorld.setSize(width, height);
		menuOverlay.setSize(width, height);
		
		if(!initialized) {
			initialized = true;
			menuOverlay.begin();
		}
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	public GameWorld getGameWorld() {
		return gameWorld;
	}
	
	public MenuOverlay getMenuOverlay() {
		return menuOverlay;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public PolygonSpriteBatch getBatch() {
		return batch;
	}
}
