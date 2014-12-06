package com.us.ld31;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

	private final TextureAtlas atlas;
	
	public final TextureRegion tileGrass;
	public final TextureRegion tileHouse;
	public final TextureRegion tileRoad;
	public final TextureRegion tileRock;
	public final TextureRegion tileTree;
	
	public Assets() {
		atlas = new TextureAtlas(Gdx.files.internal("textures/Game.pack"));
		tileGrass = atlas.findRegion("grass");
		tileHouse = atlas.findRegion("house");
		tileRoad = atlas.findRegion("road");
		tileRock = atlas.findRegion("rock");
		tileTree = atlas.findRegion("tree");
	}
	
	@Override
	public void dispose() {
		atlas.dispose();
	}
	
}
