package com.us.ld31.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.us.ld31.utils.SpriteActor;

public class Skillbook extends Group {

	private final GameUi gameUi;
	private final Group table = new Group();
	private final SpriteActor tableBackground = new SpriteActor();
	private final Label title;

	private final SpriteActor apply = new SpriteActor();
	private final SpriteActor cancel = new SpriteActor();
	
	public Skillbook(final GameUi gameUi) {
		setTransform(false);
		this.gameUi = gameUi;
		
		title = new Label("Skill Book", new LabelStyle(gameUi.getApp().assets.fontSmall, new Color(1f, 1f, 1f, 1f)));
		title.pack();
		
		setTouchable(Touchable.childrenOnly);

		table.addActor(tableBackground);
		tableBackground.setRegion(gameUi.getApp().assets.uiBlock);
		tableBackground.setColor(Color.BLACK);

		table.addActor(title);
		
		addActor(table);
	}
	
	public void onPauseHidden() {
		remove();
	}
	
	@Override
	public void setSize(final float width, 
						final float height) {
		
		super.setSize(width, height);
		table.setWidth(width * 0.8f);
		table.setHeight(height * 0.6f);
		tableBackground.setSize(table.getWidth(), table.getHeight());
		
		table.setPosition(
				width / 2f - table.getWidth() / 2f,
				height / 2f - table.getHeight() / 2f);
		
		title.setPosition(
				table.getWidth() / 2f - title.getWidth() / 2f,
				table.getHeight() - title.getHeight());
	}
	
}
