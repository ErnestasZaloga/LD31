package com.us.ld31.utils.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;

public class WorldMap extends Group {
	
	private final int tilesY = 40;
	private float width = Gdx.graphics.getWidth();
	private float height = Gdx.graphics.getHeight();
	private float tileWH = height / 40;
	private int tilesX = MathUtils.ceil(Math.abs(width/tileWH)); 
	
	public WorldMap() {
		
	}

	public float getTileSize() {
		return tileWH;
	}
	
	public int getTilesY() {
		return tilesY;
	}

	public int getTilesX() {
		return tilesX;
	}

	public boolean isWalkable(final int x, final int y) {
		return true;
	}
	
}
