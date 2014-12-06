package com.us.ld31.utils.tiles;

import com.us.ld31.LD31;

public class TileFactory {
	
	private LD31 app;
	private float tileWH;

	public TileFactory(LD31 app, float tileWH) {
		this.app = app;
		this.tileWH = tileWH;
	}
	
	public Tile createGrassTile(float x, float y) {
		Tile t = new Tile(x, y);
		t.setRegion(app.assets.tileGrass);
		t.setSize(tileWH, tileWH);
		t.setWalkable(true);
		return t;
	}
	
	public Tile createRoadTile(float x, float y) {
		Tile t = new Tile(x, y);
		t.setRegion(app.assets.tileRoad);
		t.setSize(tileWH, tileWH);
		t.setWalkable(true);
		return t;
	}
	
	public Tile createTreeTile(float x, float y) {
		Tile t = new Tile(x, y);
		t.setRegion(app.assets.tileTree);
		t.setSize(tileWH, tileWH);
		t.setWalkable(false);
		return t;
	}
	
	public Tile createRockTile(float x, float y) {
		Tile t = new Tile(x, y);
		t.setRegion(app.assets.tileRock);
		t.setSize(tileWH, tileWH);
		t.setWalkable(false);
		return t;
	}
	
	public Tile createShopTile(float x, float y) {
		Tile t = new Tile(x, y);
		t.setRegion(app.assets.tileHouse);
		t.setSize(tileWH*4, tileWH*2);
		t.setWalkable(false);
		return t;
	}
	
	public Tile createHouseTile(float x, float y) {
		Tile t = new Tile(x, y);
		t.setRegion(app.assets.tileHouse);
		t.setSize(tileWH*2, tileWH*2);
		t.setWalkable(false);
		return t;
	}
	
}
