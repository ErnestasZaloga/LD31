package com.us.ld31.utils;

import com.badlogic.gdx.Gdx;

public class Space {

	public final float horizontal;
	public final float vertical;
	
	public Space() {
		horizontal = Gdx.graphics.getWidth() / 100f;
		vertical = Gdx.graphics.getHeight() / 100f;
	}
	
	public float horizontal(final float amount) {
		return horizontal * amount;
	}
	
	public float vertical(final float amount) {
		return vertical * amount;
	}
	
}
