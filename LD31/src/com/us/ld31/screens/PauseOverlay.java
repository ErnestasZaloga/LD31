package com.us.ld31.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.us.ld31.game.ui.Delegate;
import com.us.ld31.game.ui.GameUi;
import com.us.ld31.utils.SpriteActor;
import com.us.ld31.utils.TouchListener;
import com.us.ld31.utils.steps.Steps;
import com.us.ld31.utils.steps.scene.ActorSteps;

public class PauseOverlay extends Group {

	private final GameUi gameUi;
	private final SpriteActor background = new SpriteActor();
	private final Label title;
	
	public PauseOverlay(final GameUi gameUi) {
		this.gameUi = gameUi;
		setTransform(false);
		title = new Label("PAUSED", new LabelStyle(gameUi.getApp().assets.fontBig, new Color(1f, 1f, 1f, 1f)));
		title.pack();
		
		background.setRegion(gameUi.getApp().assets.uiBlock);
		background.setColor(Color.BLACK);
		
		addActor(background);
		addActor(title);
		
		addListener(new TouchListener() {
			@Override
			public void touched() {
				final Delegate delegate = gameUi.getDelegate();
				
				if(delegate != null) {
					delegate.onPauseStateChanged();
				}
			}
		});
	}
	
	public void begin() {
		background.getColor().a = 0f;
		background.clearActions();
		background.addAction(
				Steps.action(ActorSteps.alphaTo(0.8f, 0.3f, Interpolation.circleOut)));
	}
	
	@Override
	public void setSize(final float width, 
						final float height) {
		
		super.setSize(width, height);
		background.setSize(width, height);
		title.setPosition(width / 2f - title.getWidth() / 2f, height / 2f - title.getHeight() / 2f);
	}
	
}
