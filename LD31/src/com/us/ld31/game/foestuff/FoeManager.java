package com.us.ld31.game.foestuff;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.us.ld31.game.GameWorld;


public class FoeManager {
	private final GameWorld world;
	
	private Pool<Foe> foePool;
	private Array<Foe> foes;
	
	private Vector2 spawnPos = new Vector2();
	
	private int[] spawnPositions = new int[8];
	
	public FoeManager(final GameWorld world) {
		this.world = world;
		
		foePool = new Pool<Foe>() {
			@Override
			protected Foe newObject() {
				return new Foe(world);
			}
		};
		
		foes = new Array<Foe>();
		
	}
	
	float time = 10;
	float spawnInterval = 10;
	private boolean canSpawn = true;
	
	public void update(float delta) {
		time += delta;
		if(time > spawnInterval) {
			canSpawn = true;
		} else {
			canSpawn = false;
		}
	}
	
	public boolean canSpawn() {
		return canSpawn;
	}
	
	public Foe makeFoe(TextureRegion region, int distance, int speed) {
		canSpawn = false;
		time = 0;
		
		Foe foe = foePool.obtain();
		foe.setRegion(region);
		foe.setAstar(world.getAstar());
		foe.setDistance(distance);
		foe.setSpeed(speed);
		
		foes.add(foe);
		
		return foe;
	}
	
	public Vector2 getRandomSpawnPoint() {
		int rnd = MathUtils.random(3) * 2;
		
		spawnPos.set(spawnPositions[rnd], spawnPositions[rnd + 1]);
		
		return spawnPos;
	}
	
	public void setupSpawnPositions() {
		spawnPositions[0] = (int) (0);
		spawnPositions[1] = (int) (world.getHeight() / 2);
		spawnPositions[2] = (int) (world.getWidth() / 2);
		spawnPositions[3] = (int) (world.getHeight()) - 1;//XXX omg hack!
		spawnPositions[4] = (int) (world.getWidth());
		spawnPositions[5] = (int) (world.getHeight() / 2);
		spawnPositions[6] = (int) (world.getWidth() / 2);
		spawnPositions[7] = (int) (0);
	}
	
	public Array<Foe> getAllFoes() {
		return foes;
	}
	
	public Foe getFoeByCoords(float x, float y) {
		for(Foe f : foes) {
			if(f.getX() <= x && x <= f.getX()+f.getWidth() && f.getY() <= y && y <= f.getY()+f.getHeight()) {
				return f;
			}
		}
		return null;
	}
}
