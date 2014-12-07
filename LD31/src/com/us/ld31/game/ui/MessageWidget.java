package com.us.ld31.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.us.ld31.utils.steps.Steps;
import com.us.ld31.utils.steps.scene.ActorSteps;

public class MessageWidget extends Group {

	private final GameUi gameUi;
	private final LabelStyle warningLabelStyle;
	private final LabelStyle damageLabelStyle;
	
	public MessageWidget(final GameUi gameUi) {
		setTransform(false);
		this.gameUi = gameUi;
		
		setTouchable(Touchable.disabled);
		
		warningLabelStyle = new LabelStyle(gameUi.getApp().assets.fontSmall, Color.valueOf("FFCC00"));
		damageLabelStyle = new LabelStyle(gameUi.getApp().assets.fontSmall, new Color(1f, 0f, 0f, 1f));
	}
	
	public void showWarning(final String warning) {
		createLabel(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), warning, warningLabelStyle);
	}
	
	public void showDamage(final Actor location, 
						   final int damage) {
		
		createLabel(location.getX() + location.getWidth() / 2f, location.getTop(), "" + damage, damageLabelStyle);
	}
	
	public void showMiss(final Actor location) {
		createLabel(location.getX() + location.getWidth() / 2f, location.getTop(), "MISS", damageLabelStyle);
	}
	
	private void createLabel(final float x, 
							 final float y,
							 final String text,
							 final LabelStyle labelStyle) {
		
		final Label label = new Label(text, labelStyle);
		label.pack();
		label.addAction(
				Steps.action(
						Steps.sequence(
								Steps.parallel(
										ActorSteps.alphaTo(0f, 1f),
										ActorSteps.moveBy(0f, label.getHeight() * 2f, 1f)),
								ActorSteps.remove())));
		
		addActor(label);
		label.setPosition(x - label.getWidth() / 2f, y);
	}
	
}
