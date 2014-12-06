package com.us.ld31;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.us.ld31.game.GameWorld;
import com.us.ld31.screens.MenuOverlay;
import com.us.ld31.utils.Space;

public class LD31 implements ApplicationListener {
	
	public Stage stage;
	public PolygonSpriteBatch batch;
	
	private boolean initialized;
	
	public GameWorld gameWorld;
	public MenuOverlay menuOverlay;
	public Assets assets;
	public Space space;
	
	@Override
	public void create() {
		batch = new PolygonSpriteBatch(4000);
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
		Gdx.input.setInputProcessor(stage);
		assets = new Assets();
		space = new Space();
	}

	@Override
	public void dispose() {
		batch.dispose();
		assets.dispose();
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
	
}
