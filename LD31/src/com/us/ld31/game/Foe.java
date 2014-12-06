package com.us.ld31.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.us.ld31.utils.SpriteActor;

public class Foe extends SpriteActor{
	
	public Foe(TextureRegion region) {
		super(region);
		
		
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		//setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
	}
	
}
