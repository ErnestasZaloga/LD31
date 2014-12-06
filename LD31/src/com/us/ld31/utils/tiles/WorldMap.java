package com.us.ld31.utils.tiles;

import com.badlogic.gdx.scenes.scene2d.Group;

public class WorldMap extends Group {
	
	private float tileSize = 64;
	
	public WorldMap() {
		
	}

	public float getTileSize() {
		return tileSize;
	}

	public boolean isWalkable(final int x, final int y) {
		return true;
	}
	
}
