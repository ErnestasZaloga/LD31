package com.us.ld31;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

	private final TextureAtlas atlas;
	
	public final TextureRegion uiBlock;
	public final TextureRegion uiArrowUp;
	public final TextureRegion uiArrowDown;
	public final BitmapFont fontBig;
	public final BitmapFont fontSmall;
	
	public final TextureRegion tileGrass;
	public final TextureRegion tileHouse;
	public final TextureRegion tileRoad;
	public final TextureRegion tileRock;
	public final TextureRegion tileTree;
	public final TextureRegion debugIcon;
	
	public Assets() {
		atlas = new TextureAtlas(Gdx.files.internal("textures/Game.pack"));
		
		uiBlock = atlas.findRegion("Block");
		uiArrowUp = atlas.findRegion("arrow");
		uiArrowDown = new TextureRegion(uiArrowUp);
		uiArrowDown.flip(false, true);

		uiBlock.getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		tileGrass = atlas.findRegion("grass");
		tileHouse = atlas.findRegion("house");
		tileRoad = atlas.findRegion("road");
		tileRock = atlas.findRegion("rock");
		tileTree = atlas.findRegion("tree");

		debugIcon = tileRock;
		
		fontBig = new BitmapFont(Gdx.files.internal("fonts/FontBig.fnt"), atlas.findRegion("FontBig"));
		fontSmall = new BitmapFont(Gdx.files.internal("fonts/FontSmall.fnt"), atlas.findRegion("FontSmall"));
	}
	
	@Override
	public void dispose() {
		atlas.dispose();
		fontBig.dispose();
		fontSmall.dispose();
	}
	
}
