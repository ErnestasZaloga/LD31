package com.us.ld31.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.us.ld31.utils.TextureActor;
import com.us.ld31.utils.steps.FloatStep;
import com.us.ld31.utils.steps.Steps;

public class SideBar extends Group {

	private final TextureActor barBackground = new TextureActor();
	private final TextureActor barFill = new TextureActor();
	private float percent;
	
	private final int originalFillHeight;
	private final int originalFillY;
	
	private final FloatStep.Listener step = new FloatStep.Listener() {
		@Override
		public void onChange(FloatStep floatStep, float value) {
			updateVisuals(value);
		}
	};
	
	public SideBar(final GameUi gameUi, final Color fillColor, final TextureRegion fillRegion) {
		setTransform(false);
		
		barBackground.setRegion(gameUi.getApp().assets.uiBlock);
		barFill.setRegion(new TextureRegion(fillRegion));
		
		originalFillHeight = barFill.getRegion().getRegionHeight();
		originalFillY = barFill.getRegion().getRegionY();
		
		barFill.setColor(fillColor);
		final Color backgroundColor = new Color().set(fillColor);
		backgroundColor.a = 0.5f;
		
		barBackground.setColor(backgroundColor);
		
		addActor(barBackground);
		addActor(barFill);
	}
	
	@Override
	public void act(final float delta) {
		super.act(delta);
		percent += delta;
		percent %= 1f;
	}
	
	@Override
	public void setSize(final float width, 
						final float height) {
		
		super.setSize(width, height);
		barBackground.setSize(width, height);
		barFill.setSize(width, height);
		setPercent(percent, false);
	}
	
	public void setPercent(final float percent, final boolean animated) {
		clearActions();
		
		if(animated) {
			addAction(Steps.action(
					Steps._float(this.percent, percent, 0.2f, Interpolation.circleOut, step)));
		}
		else {
			updateVisuals(percent);
		}
		
		this.percent = percent;
	}
	
	private void updateVisuals(final float percent) {
		barFill.getRegion().setRegionHeight((int)(originalFillHeight * percent));
		barFill.setHeight(barBackground.getHeight() * percent);
	}
	
}
