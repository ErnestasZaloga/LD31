package com.us.ld31.utils.tiles;

import com.us.ld31.LD31;

public class TileFactory {
	
	private LD31 app;

	public TileFactory(LD31 app) {
		this.app = app;
	}
	
	public Tile createGrassTile(float x, float y) {
		Tile t = new Tile(x, y);
		t.setRegion(app.assets.tileGrass);
		t.setWalkable(true);
		return t;
	}
	
	public Tile createRoadTile(float x, float y) {
		Tile t = new Tile(x, y);
		t.setRegion(app.assets.tileRoad);
		t.setWalkable(true);
		return t;
	}
	
	public Tile createTreeTile(float x, float y) {
		Tile t = new Tile(x, y);
		t.setRegion(app.assets.tileTree);
		t.setWalkable(false);
		return t;
	}
	
	public Tile createRockTile(float x, float y) {
		Tile t = new Tile(x, y);
		t.setRegion(app.assets.tileRock);
		t.setWalkable(false);
		return t;
	}
	
	public Tile createHouseTile(float x, float y) {
		Tile t = new Tile(x, y);
		t.setRegion(app.assets.tileHouse);
		t.setWalkable(false);
		return t;
	}
	
}
