package com.us.ld31.utils.tiles;

import com.badlogic.gdx.scenes.scene2d.Group;

public class WorldMap extends Group {
	
	private final int tilesY = 40;
	private int tilesX;
	private float tileWH;
	
	public WorldMap() {
		
	}

	public float getTileSize() {
		return tileWH;
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		tileWH = height / 40;
		tilesX = Math.round(width/tileWH)+1;
	}

	public boolean isWalkable(final int indexX, final int indexY) {
		return ((Tile)getChildren().get(indexY+(indexX - 1)*tilesY)).isWalkable();
	}
	
	public int getTilesX() {
		return tilesX;
	}
	
	public int getTilesY() {
		return tilesY;
	}
	
}
