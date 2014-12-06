package com.us.ld31.utils.tiles;

import com.us.ld31.utils.SpriteActor;

public class Tile extends SpriteActor {

	private boolean walkable;
	private int indexX;
	private int indexy;
	
	public Tile(float x, float y) {
		this.setX(x);
		this.setY(y);
		//TODO calculate indexX and indexY...
	}

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	public int getIndexX() {
		return indexX;
	}

	public void setIndexX(int indexX) {
		this.indexX = indexX;
	}

	public int getIndexy() {
		return indexy;
	}

	public void setIndexy(int indexy) {
		this.indexy = indexy;
	}
	
}
