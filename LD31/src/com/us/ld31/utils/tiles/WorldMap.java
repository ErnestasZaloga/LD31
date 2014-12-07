package com.us.ld31.utils.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

public class WorldMap extends Group {
	
	public Array<Vector2> occupiedIndexes = new Array<Vector2>();
	
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
		for(Vector2 v : occupiedIndexes) {
			if((int) v.x == indexX && (int) v.y == indexY) {
				return false;
			}
		}
		return ((Tile)getChildren().get(indexX * tilesY + indexY)).isWalkable();
	}
	
	public int getTile(final float x, final float y) {
		final int tileX = (int)(x / tileWH);
		final int tileY = (int)(y / tileWH);
		
		return mergeCoords(tileX, tileY);
	}
	
	public int mergeCoords(final int tileX, final int tileY) {
//		return tileY * tilesX + tileX;
		return tileX * tilesY + tileY;
	}
	
	public int getTilesX() {
		return tilesX;
	}
	
	public int getTilesY() {
		return tilesY;
	}
	
	@Override
	public void draw(final Batch batch, final float parentAlpha) {
		final long a = System.currentTimeMillis();
		super.draw(batch, parentAlpha);
		//System.out.println("Takes: " + (System.currentTimeMillis() - a));
	}
	
}
