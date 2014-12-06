package com.us.ld31.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SpriteActor extends Actor {

	private final Sprite sprite;
	private float lastX = Float.MIN_VALUE;
	private float lastY = Float.MIN_VALUE;
	private TextureRegion region;
	protected float sizeScale = 1f;
	
	public SpriteActor() {
		sprite = new Sprite();
		this.sprite.setOrigin(0f, 0f);
	}
	
	public SpriteActor(final TextureRegion region) {
		this.region = region;
		sprite = new Sprite(region);
		
		final float prefWidth = region.getRegionWidth();
		final float prefHeight = region.getRegionHeight();
		
		sprite.setSize(prefWidth, prefHeight);
		sprite.setOrigin(0f, 0f);
		setSize(prefWidth, prefHeight);
	}
	
	public void setSizeScale(final float sizeScale) {
		this.sizeScale = sizeScale;
		applySizeScale();
	}
	
	public float getSizeScale() {
		return sizeScale;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void setRegion(final TextureRegion region) {
		if(region == null) {
			sprite.setTexture(null);
		} 
		else {
			sprite.setRegion(region);
		}
		
		this.region = region;
		
		lastX = Float.MIN_VALUE;
		lastY = Float.MIN_VALUE;
		
		if(region != null) {
			applySizeScale();
		} 
		else {
			setSize(0f, 0f);
			sprite.setSize(0f, 0f);
		}
	}
	
	public TextureRegion getRegion() {
		return region;
	}

	protected void applySizeScale() {
		if(region == null) {
			setSize(0f, 0f);
			return;
		}
		
		setSize(region.getRegionWidth() * sizeScale, region.getRegionHeight() * sizeScale);
	}
	
	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		sprite.setSize(width, getHeight());
	}
	
	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		sprite.setSize(getWidth(), height);
	}
	
	@Override
	public void setSize(float width,
						float height) {
		
		super.setSize(width, height);
		sprite.setSize(width, height);
	}
	
	@Override
	public void sizeBy(final float size) {
		super.sizeBy(size);
		sprite.setSize(getWidth(), getHeight());
	}
	
	@Override
	public void sizeBy(final float width, 
					   final float height) {
		
		super.sizeBy(width, height);
		sprite.setSize(getWidth(), getHeight());
	}
	
	@Override
	public void setBounds(final float x, 
						  final float y, 
						  float width, 
						  float height) {
		
		super.setBounds(x, y, width, height);
		sprite.setSize(width, height);
	}
	
	@Override
	public void setOriginX(final float originX) {
		super.setOriginX(originX);
		sprite.setOrigin(originX, getOriginY());
	}
	
	@Override
	public void setOriginY(final float originY) {
		super.setOriginY(originY);
		sprite.setOrigin(getOriginX(), originY);
	}
	
	@Override
	public void setOrigin(final float originX, 
						  final float originY) {
		
		super.setOrigin(originX, originY);
		sprite.setOrigin(originX, originY);
	}
	
	@Override
	public void setScaleX(final float scaleX) {
		super.setScaleX(scaleX);
		sprite.setScale(scaleX, getScaleY());
	}
	
	@Override
	public void setScaleY(final float scaleY) {
		super.setScaleY(scaleY);
		sprite.setScale(getScaleX(), scaleY);
	}
	
	@Override
	public void setScale(final float scaleX, 
						 final float scaleY) {
		
		super.setScale(scaleX, scaleY);
		sprite.setScale(scaleX, scaleY);
	}
	
	@Override
	public void setScale(final float scale) {
		super.setScale(scale);
		sprite.setScale(scale);
	}
	
	@Override
	public void scaleBy(final float scale) {
		super.scaleBy(scale);
		sprite.scale(scale);
	}
	
	@Override
	public void scaleBy(final float scaleX, 
					    final float scaleY) {
		
		super.scaleBy(scaleX, scaleY);
		sprite.setScale(getScaleX(), getScaleY());
	}
	
	@Override
	public void setRotation(final float rotation) {
		super.setRotation(rotation);
		sprite.setRotation(rotation);
	}
	
	@Override
	public void rotateBy(final float degrees) {
		super.rotateBy(degrees);
		sprite.rotate(degrees);
	}
	
	@Override
	public void setColor(final Color color) {
		super.setColor(color);
		sprite.setColor(color);
	}
	
	@Override
	public void setColor(final float r, 
						 final float g, 
						 final float b, 
						 final float a) {
		
		super.setColor(r, g, b, a);
		sprite.setColor(r, g, b, a);
	}
	
	@Override
	public void draw(final Batch batch, 
					 final float parentAlpha) {
		
		if(sprite.getTexture() != null) {
			final float x = getX();
			final float y = getY();
			
			if(x != lastX || y != lastY) {
				lastX = x;
				lastY = y;
				
				sprite.setPosition(x, y);
			}

			
			sprite.draw(batch, getColor().a * parentAlpha);
		}
	}
}
