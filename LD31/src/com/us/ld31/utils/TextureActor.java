package com.us.ld31.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextureActor extends Actor {
	
	private TextureRegion region;
	
	public TextureActor() {}
	
	public TextureActor(final TextureRegion region) {
		this.region = region;
		setSize(region.getRegionWidth(), region.getRegionHeight());
	}
	
	public void setRegion(final TextureRegion region) {
		this.region = region;
		if(region != null) {
			setSize(region.getRegionWidth(), region.getRegionHeight());
		}
	}
	
	public TextureRegion getRegion() {
		return region;
	}
	
	@Override
	public void draw(final Batch batch, 
					 final float parentAlpha) {
		
		if(region == null) {
			return;
		}
		
		final Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		
		float x = getX();
		float y = getY();
		float width = getWidth();
		float height = getHeight();
		
		batch.draw(
				region, 
				x, 
				y, 
				getOriginX(), 
				getOriginY(), 
				width, 
				height,
				getScaleX(), 
				getScaleY(), 
				getRotation());

	}
}
