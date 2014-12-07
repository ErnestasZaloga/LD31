package com.us.ld31.game.foestuff;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.us.ld31.game.Character;
import com.us.ld31.game.GameWorld;
import com.us.ld31.utils.Astar;
import com.us.ld31.utils.Log;
import com.us.ld31.utils.SpriteActor;

public class Foe extends SpriteActor{
	
	private Array<SpriteActor> tiles = new Array<SpriteActor>();
	
	private float tilesPerSecond = 5;
	private float secondsPerTile = 1f/tilesPerSecond;
	private boolean shouldTravel;
	
	private final GameWorld world;
	private Astar astar;
	private Character character;
	
	private IntArray path = new IntArray();
	private int pathIndex = 0;
	private float tileSize;
	
	private int playerX;
	private int playerY;
	private int lastPlayerX;
	private int lastPlayerY;
	
	private int foeTileX;
	private int foeTileY;
	
	private int distance = 10;
	private int difficulity;
	private int hp;
	
	public Foe(final GameWorld world) {
		//super();
		this.world = world;
		this.astar = world.getAstar();
	}
	
	//public void setRegion(TextureRegion region) {
	//	super.setRegion(region);
	//}
	
	private float time = 0;
	private boolean firstAct = true;
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(firstAct) {
			lerpX = getX();
			lerpY = getY();
			tileSize = world.getWorldMap().getTileSize();
			character = world.getCharacter();
			
			playerX = lastPlayerX = (int)(character.getX() / tileSize);
			playerY = lastPlayerY = (int)(character.getY() / tileSize);
			travelTo(lastPlayerX, lastPlayerY);
			firstAct = false;
		}
		
		if((playerX != lastPlayerX || playerY != lastPlayerY)) {
			travelTo(playerX, playerY);
			lastPlayerX = playerX;
			lastPlayerY = playerY;
		} else {
			playerX = (int)(character.getX() / tileSize);
			playerY = (int)(character.getY() / tileSize);
		}

		//Log.trace(Vector2.dst(character.getX(), character.getY(), getX(), getY()));
		if(Vector2.dst(character.getX(), character.getY(), getX(), getY()) > distance * tileSize) {
			if(pathIndex >= path.size) {
				//shouldTravel = false;
				//pathIndex = 0;
			} else {
				foeTileX = path.get(pathIndex);
				foeTileY = path.get(pathIndex + 1);
				
				final int tileXCh = foeTileX - (int)(getX() / tileSize);
				final int tileYCh = foeTileY - (int)(getY() / tileSize);
				
				time += delta;
				double div = time / secondsPerTile > 1f? 1f : time / secondsPerTile;
				
				float x = (float)(lerpX + (tileSize * tileXCh) * div);//MathUtils.lerp(lerpX, foeTileX * tileSize, div);
				float y = (float)(lerpY + (tileSize * tileYCh) * div); //MathUtils.lerp(lerpY, foeTileY * tileSize, div);
				
				//float x = foeTileX * tileSize;
				//float y = foeTileY * tileSize;
				
				this.setPosition(x, y);
				//Log.trace(tmpX, tmpY);
				
				if(time > secondsPerTile) {
					pathIndex += 2;
					time = 0;//%= secondsPerTile;
					lerpX = getX();//foeTileX * tileSize;
					lerpY = getY();//foeTileY * tileSize;
					//this.setPosition(lerpX, lerpY);
				}
			}
		} 
	}
	
	private float lerpX = 0, lerpY = 0;
	
	/**
	 * coordinates in tile system
	 * */
	public void travelTo(int x, int y) {
		shouldTravel = false;
		
		for(int i =0; i < tiles.size; i += 1) {
			tiles.get(i).remove();
		}
		tiles.clear();
		
		tileSize = world.getWorldMap().getTileSize();
		astar.getPath(x, 
					  y, 
					 (int)(getX() / tileSize), 
					 (int)(getY() / tileSize),
					 path);
		lerpX = getX();
		lerpY = getY();
		
		pathIndex = 0;
		
		if(path.size >= 2) {
			path.removeRange(0, 1);
		}
		//
		/*int ps = path.size;
		if(ps >= 2 * distance) {
			path.removeRange(ps - distance * 2, ps - 1);
		} else {
			path.clear();
		}*/
		if(path.size == 0) {
			moveBy(0, 1);
			//Log.trace(0);
			return;
		}
		
		for(int i =0; i < path.size; i += 2) {
			final SpriteActor tile = new SpriteActor(world.getApp().assets.uiBlock);
			tile.setSize(world.getWorldMap().getTileSize(), world.getWorldMap().getTileSize());
			tile.setPosition(path.get(i) * world.getWorldMap().getTileSize(), path.get(i + 1) * world.getWorldMap().getTileSize());
			
			getParent().addActor(tile);
			tiles.add(tile);
		}
		
		shouldTravel = true;
		time = 0;
	}
	
	@Override
	public void setParent(Group parent) {
		super.setParent(parent);
		
		if(parent != null) {
			firstAct = true;
		}
	}
	
	public void setAstar(Astar astar) {
		this.astar = astar;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public void setSpeed(float speedInTilesPerSecond) {
		this.tilesPerSecond = speedInTilesPerSecond;
		this.secondsPerTile = 1f / speedInTilesPerSecond;
	}
	
	public int getDifficulity() {
		return difficulity;
	}

	public void setDifficulity(int difficulity) {
		this.difficulity = difficulity;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	
}
