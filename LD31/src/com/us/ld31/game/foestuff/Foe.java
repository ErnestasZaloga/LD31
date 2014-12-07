package com.us.ld31.game.foestuff;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.PlayerCharacter;
import com.us.ld31.utils.Astar;
import com.us.ld31.utils.Log;
import com.us.ld31.utils.SpriteActor;
import com.us.ld31.utils.tiles.Tile;

public class Foe extends SpriteActor{
	
	private Array<SpriteActor> tiles = new Array<SpriteActor>();
	
	private float tilesPerSecond = 5;
	private float secondsPerTile = 1f/tilesPerSecond;
	private boolean shouldTravel;
	
	private final GameWorld world;
	private Astar astar;
	private PlayerCharacter character;
	
	private IntArray path = new IntArray();
	private int pathIndex = 0;
	private float tileSize;
	
	private int playerX;
	private int playerY;
	private int lastPlayerX;
	private int lastPlayerY;
	
	private int nextTileX;
	private int nextTileY;
	
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
		
		if((playerX != lastPlayerX || playerY != lastPlayerY) && allowNextTravel && Vector2.dst(character.getX(), character.getY(), getX(), getY()) > distance * tileSize) {
			travelTo(playerX, playerY);
			allowNextTravel = false;
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
				allowNextTravel = true;
			} else {
				allowNextTravel = false;
				nextTileX = path.get(pathIndex);
				nextTileY = path.get(pathIndex + 1);
				
				int tileXChange = 0;
				int tileYChange = 0;
				
				tileXChange = MathUtils.clamp(nextTileX - getTileX(), -1, 1);
				tileYChange = MathUtils.clamp(nextTileY - getTileY(), -1, 1);
				
				time += delta;
				double div = time / secondsPerTile > 1f? 1f : time / secondsPerTile;
				
				float x = (float)(lerpX + ((tileSize * tileXChange) * div));//MathUtils.lerp(lerpX, foeTileX * tileSize, div);
				float y = (float)(lerpY + ((tileSize * tileYChange) * div)); //MathUtils.lerp(lerpY, foeTileY * tileSize, div);
				
				this.setPosition(x, y);
				
				lerpX = x;
				lerpY = y;
				
				if(time > secondsPerTile) {
					//moved = true;
					pathIndex += 2;
					time %= secondsPerTile;
					lerpX = x;//foeTileX * tileSize;
					lerpY = y;//foeTileY * tileSize;
					this.setPosition(lerpX, lerpY);
					allowNextTravel = true;
				}
			}
		}
	}
	
	public int getTileX() {
		return (int)(getX() / tileSize);
	}
	
	public int getTileY() {
		return (int)(getY() / tileSize);
	}
	
	private boolean moved = true;
	
	public void translocate(float x, float y) {
		lerpX = x;
		lerpY = y;
		this.setPosition(x, y);
		playerX = lastPlayerX = (int)(character.getX() / tileSize);
		playerY = lastPlayerY = (int)(character.getY() / tileSize);
		travelTo(lastPlayerX, lastPlayerY);
	}
	
	private boolean allowNextTravel;
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
		
		for(int i=0; i < path.size; i += 2) {
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

	public Array<Tile> getTilesInRange(int range) {
		Array<Tile> result = new Array<Tile>();
		for(int i=0; i<world.getWorldMap().getChildren().size; i++) {
			Tile t = (Tile) world.getWorldMap().getChildren().get(i);
			t.getColor().a = 1;
			float dx = getX()-t.getX();
			float dy = getY()-t.getY();
			if(Math.sqrt(dx*dx+dy*dy) <= range*world.getWorldMap().getTileSize()) {
				result.add(t);
			}
		}
		return result;
	}
	
}
