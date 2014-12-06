package com.us.ld31.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntArray;
import com.us.ld31.utils.Astar;
import com.us.ld31.utils.SpriteActor;

public class Foe extends SpriteActor{
	
	private float movementSpeed = 10;
	private boolean shouldTravel;
	
	private final GameWorld world;
	private final Astar astar;
	
	private IntArray path;
	private int pathIndex = 0;
	private float tileSize;
	
	
	public Foe(TextureRegion region, final GameWorld world) {
		super(region);
		this.world = world;
		this.astar = world.getAstar();
		//tileSize = world
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(shouldTravel) {
			if(pathIndex >= path.size) {
				shouldTravel = false;
				pathIndex = 0;
			} else {
				this.setPosition(path.get(pathIndex++) * world.getWorldMap().getTileSize(), 
								 path.get(pathIndex++) * world.getWorldMap().getTileSize());
			}
		}
	}
	
	public void travelTo(float x, float y) {
		//path = astar.getPath((int)getX(), (int)getY(), (int)x, (int)y);
		path = astar.getPath((int)x, 
							 (int)y, 
							 (int)(getX() / world.getWorldMap().getTileSize()), 
							 (int)(getY() / world.getWorldMap().getTileSize()));
		shouldTravel = true;
		pathIndex = 0;
	}
	
}
