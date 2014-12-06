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
	
	
	public Foe(TextureRegion region, final GameWorld world) {
		super(region);
		this.world = world;
		this.astar = world.getAstar();
		//tileSize = world
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
		
		playerX = (int)(character.getX() / tileSize);
		playerY = (int)(character.getY() / tileSize);
		
		if(playerX != lastPlayerX || playerY != lastPlayerY) {
			travelTo(playerX, playerY);
			lastPlayerX = playerX;
			lastPlayerY = playerY;
		}
		
		if(shouldTravel) {
			if(pathIndex >= path.size - 2) {
				shouldTravel = false;
				pathIndex = 0;
			} else {
				time += delta;
				float div = time / secondsPerTile > 1f? 1f : time / secondsPerTile;
				//Log.trace(delta, time, div);
				
				float x = MathUtils.lerp(tmpX, path.get(pathIndex +2) * tileSize, div);
				float y = MathUtils.lerp(tmpY, path.get(pathIndex + 3) * tileSize, div);
				
				this.setPosition(x, y);
				
				if(time >= secondsPerTile) {
					pathIndex += 2;
					time %= secondsPerTile;
					tmpX = x;
					tmpY = y;
				}
			}
		}
	}
	
	private float tmpX = 0, tmpY = 0;
	
	/**
	 * coordinates in tile system
	 * */
	public void travelTo(int x, int y) {
		tileSize = world.getWorldMap().getTileSize();
		tmpX = getX();
		tmpY = getY();
		path = astar.getPath(x, 
							 y, 
							 (int)(getX() / tileSize), 
							 (int)(getY() / tileSize));
		
		/*path = astar.getPath((int)(getX() / world.getWorldMap().getTileSize()), 
							 (int)(getY() / world.getWorldMap().getTileSize()), 
							 x, 
							 y);*/
		
		shouldTravel = true;
		pathIndex = 0;
	}
	
	public void setAstar(Astar astar) {
		this.astar = astar;
	}
	
}
