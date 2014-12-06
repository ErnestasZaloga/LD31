package com.us.ld31.utils.steps;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;

abstract public class Step implements Poolable {
	
	public static <T extends Step> T obtain(final Class<T> type) {
		final Pool<T> pool = Pools.get(type);
		final T step = pool.obtain();
		step.setPool(pool);
		
		return step;
	}
	
	/**
	 * INTENDED FOR USE IN TIMELINE, MODIFY WITH CAUSION!
	 * if modified outside of timeline call update method on the timeline(the one step is contained in).
	 * */
	public float timelinePosition;
	
	@SuppressWarnings("rawtypes")
	private Pool pool;
	
	abstract public Step getPooledCopy();
	abstract public Step getCopy();
	
	@SuppressWarnings("rawtypes")
	public void setPool(final Pool pool) {
		this.pool = pool;
	}
	
	@SuppressWarnings("rawtypes")
	public Pool getPool() {
		return pool;
	}
	
	abstract public boolean perform(final float delta, 
						   	 		final Object object);
	
	public void restart() {}
	
	@Override
	public void reset() {
		restart();
		timelinePosition = 0f;
	}
	
	@SuppressWarnings("unchecked")
	public void free() {
		if(pool != null) {
			pool.free(this);
			pool = null;
		}
	}
}
