package com.us.ld31.utils.tiles;

import com.us.ld31.utils.SpriteActor;

public class Tile extends SpriteActor {

	private boolean walkable;
	private int indexX;
	private int indexY;
	
	public Tile(float x, float y) {
		this.setX(x);
		this.setY(y);
	}

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		indexX = (int) Math.floor(getX()/getWidth());
		indexY = (int) Math.floor(getY()/getHeight());
	}

	public int getIndexX() {
		return indexX;
	}

	public void setIndexX(int indexX) {
		this.indexX = indexX;
	}

	public int getIndexY() {
		return indexY;
	}

	public void setIndexy(int indexY) {
		this.indexY = indexY;
	}
	
}
