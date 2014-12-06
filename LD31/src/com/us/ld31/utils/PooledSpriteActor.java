package com.us.ld31.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;

public class PooledSpriteActor extends SpriteActor implements Poolable {

	public static void clearPools() {
		Pools.get(PooledSpriteActor.class).clear();
	}
	
	@Override
	protected void setParent(final Group parent) {
		super.setParent(parent);
		
		if(parent == null) {
			Pools.free(this);
		}
	}
	
	@Override
	public void reset() {
		setOrigin(0f, 0f);
		setRotation(0f);
		setScale(1f);
		setPosition(0f, 0f);
		setRegion(null);
		setSize(0f, 0f);
		setColor(Color.WHITE);
		sizeScale = 1f;

		clear();
	}
}
