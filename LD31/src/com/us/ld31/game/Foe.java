package com.us.ld31.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntArray;
import com.us.ld31.utils.Astar;
import com.us.ld31.utils.Log;
import com.us.ld31.utils.SpriteActor;

public class Foe extends SpriteActor{
	
	private float tilesPerSecond = 5;
	private float secondsPerTile = 1f/tilesPerSecond;
	private boolean shouldTravel;
	
	private final GameWorld world;
	private Astar astar;
	private Character character;
	
	private IntArray path;
	private int pathIndex = 0;
	private float tileSize;
	
	private int playerX;
	private int playerY;
	private int lastPlayerX;
	private int lastPlayerY;
	
	private int foeTileX;
	private int foeTileY;
	
	
	public Foe(TextureRegion region, final GameWorld world) {
		super(region);
		this.world = world;
		this.astar = world.getAstar();
	}
	
	private float time = 0;
	private boolean firstAct = true;
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(firstAct) {
			tileSize = world.getWorldMap().getTileSize();
			character = world.getCharacter();
			
			playerX = lastPlayerX = (int)(character.getX() / tileSize);
			playerY = lastPlayerY = (int)(character.getY() / tileSize);
			travelTo(lastPlayerX, lastPlayerY);
			firstAct = false;
		}
		
		if((playerX != lastPlayerX || playerY != lastPlayerY) && allowNextTravel) {
			travelTo(playerX, playerY);
			lastPlayerX = playerX;
			lastPlayerY = playerY;
		} else {
			playerX = (int)(character.getX() / tileSize);
			playerY = (int)(character.getY() / tileSize);
		}
		
		if(shouldTravel) {
			if(pathIndex >= path.size) {
				shouldTravel = false;
				pathIndex = 0;
			} else {
				foeTileX = path.get(pathIndex);
				foeTileY = path.get(pathIndex + 1);
				
				time += delta;
				float div = time / secondsPerTile > 1f? 1f : time / secondsPerTile;
				
				float x = MathUtils.lerp(tmpX, foeTileX * tileSize, div);
				float y = MathUtils.lerp(tmpY, foeTileY * tileSize, div);
				
				allowNextTravel = false;
				
				this.setPosition(x, y);
				//Log.trace(tmpX, tmpY);
				
				if(time >= secondsPerTile) {
					pathIndex += 2;
					time %= secondsPerTile;
					tmpX = foeTileX * tileSize;
					tmpY = foeTileY * tileSize;
					this.setPosition(tmpX, tmpY);
					allowNextTravel = true;
				}
			}
		}
	}
	
	private boolean allowNextTravel;
	private float tmpX = 0, tmpY = 0;
	
	/**
	 * coordinates in tile system
	 * */
	public void travelTo(int x, int y) {
		tileSize = world.getWorldMap().getTileSize();
		path = astar.getPath(x, 
							 y, 
							 (int)(getX() / tileSize), 
							 (int)(getY() / tileSize));
		if(path.size >= 2) {
			path.removeRange(0, 1);
		}
		if(path.size == 0) return;
		
		foeTileX = path.get(0);
		foeTileY = path.get(1);
		shouldTravel = true;
		allowNextTravel = false;
		pathIndex = 0;
	}
	
	public void setAstar(Astar astar) {
		this.astar = astar;
	}
	
}
