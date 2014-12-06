package com.us.ld31.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.us.ld31.utils.SpriteActor;

public class SideBar extends Group {

	private final SpriteActor barBackground = new SpriteActor();
	private final SpriteActor barFill = new SpriteActor();
	private float percent;
	
	private final int originalFillHeight;
	
	public SideBar(final GameUi gameUi, final Color fillColor) {
		setTransform(false);
		
		barBackground.setRegion(gameUi.getApp().assets.uiBlock);
		barFill.setRegion(new TextureRegion(gameUi.getApp().assets.uiBlock));
		
		originalFillHeight = barFill.getRegion().getRegionHeight();
		
		barFill.setColor(fillColor);
		final Color backgroundColor = new Color().set(fillColor);
		backgroundColor.a = 0.5f;
		
		barBackground.setColor(backgroundColor);
		
		addActor(barBackground);
		addActor(barFill);
	}
	
	@Override
	public void setSize(final float width, 
						final float height) {
		
		super.setSize(width, height);
		barBackground.setSize(width, height);
		barFill.setSize(width, height);
		setPercent(percent);
	}
	
	public void setPercent(final float percent) {
		this.percent = percent;
		barFill.getRegion().setRegionHeight((int)(originalFillHeight * percent));
		barFill.setHeight(barBackground.getHeight() * percent);
	}
	
}
