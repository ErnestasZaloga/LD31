package com.us.ld31.game.foestuff;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.us.ld31.game.GameWorld;


public class FoeManager {
	private final GameWorld world;
	
	private Pool<Foe> foePool;
	private Array<Foe> foes;
	
	public FoeManager(final GameWorld world) {
		this.world = world;
		
		foePool = new Pool<Foe>() {
			@Override
			protected Foe newObject() {
				return new Foe(world);
			}
		};
	}
	
	public Foe makeFoe(TextureRegion region, int distance, int speed) {
		Foe foe = foePool.obtain();
		foe.setRegion(region);
		foe.setAstar(world.getAstar());
		foe.setDistance(distance);
		foe.setSpeed(speed);
		
		return foe;
	}
}
