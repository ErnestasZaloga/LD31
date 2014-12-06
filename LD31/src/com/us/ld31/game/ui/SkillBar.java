package com.us.ld31.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.us.ld31.utils.SpriteActor;
import com.us.ld31.utils.TouchListener;
import com.us.ld31.utils.steps.Steps;
import com.us.ld31.utils.steps.scene.ActorSteps;

public class SkillBar extends Group {

	public static class SkillButton extends Group {
		private final SpriteActor border = new SpriteActor();
		private final SpriteActor icon = new SpriteActor();
		private final Label mapping;
		private int mappingCode;
		
		private boolean shrinked = true;
		
		public SkillButton(final SkillBar bar, final GameUi gameUi, final int mappingCode) {
			border.setRegion(gameUi.getApp().assets.uiBlock);
			border.setColor(Color.BLACK);
			icon.setRegion(gameUi.getApp().assets.debugIcon);
	
			mapping = new Label("", new LabelStyle(gameUi.getApp().assets.fontBig, new Color(1f, 1f, 1f, 1f)));
			
			addActor(border);
			addActor(icon);
			addActor(mapping);
			
			icon.setSize(96, 96);
			border.setSize(106, 106);
			mapping.setPosition(border.getWidth() - icon.getWidth(), border.getHeight() - icon.getHeight());
			
			setSize(border.getWidth(), border.getHeight());
			icon.setPosition(getWidth() / 2f - icon.getWidth() / 2f, getHeight() / 2f - icon.getHeight() / 2f);
			
			setOrigin(0f, 0f);
			
			setScale(0.5f, 0.5f);
			setMappingCode(mappingCode);
			
			addListener(new TouchListener() {
				@Override
				public void touched() {
					bar.changeActiveButton(SkillButton.this);
				}
			});
		}
		
		@Override
		public void setScale(final float scaleX, final float scaleY) {
			super.setScale(scaleX, scaleY);
			mapping.setFontScale(1f + 1f - scaleX);
		}
		
		public boolean isShrinked() {
			return shrinked;
		}
		
		public void grow() {
			clearActions();
			addAction(
					Steps.action(
							Steps.sequence(
									ActorSteps.scaleTo(1f, 1f, 0.3f, Interpolation.circleOut))));
		}
		
		public void shrink() {
			clearActions();
			addAction(
					Steps.action(
							Steps.sequence(
									ActorSteps.scaleTo(0.5f, 0.5f, 0.3f, Interpolation.circleOut))));
		}
		
		public void setMappingCode(final int mappingCode) {
			this.mappingCode = mappingCode;
			mapping.setText("" + mappingCode);
			mapping.pack();
		}
		
		public int getMappingCode() {
			return mappingCode;
		}
	}
	
	private final GameUi gameUi;
	private final SpriteActor holder = new SpriteActor();
	private final Array<SkillButton> buttons = new Array<SkillButton>();
	private int activeIndex;
	
	public SkillBar(final GameUi gameUi) {
		setTransform(false);
		this.gameUi = gameUi;
		holder.setRegion(gameUi.getApp().assets.uiBlock);
		holder.setColor(Color.BLACK);
		
		holder.setHeight(gameUi.getApp().space.vertical(4));
		
		addActor(holder);
		
		for(int i = 0; i < 6; i += 1) {
			addButton(new SkillButton(this, gameUi, i + 1));
		}
		
		setHeight(5f + buttons.get(0).getHeight());
		
		buttons.get(0).grow();
		
		addListener(new InputListener() {
			public boolean scrolled(InputEvent event,
				                    final float x,
				                    final float y,
				                    final int amount) {

				int newIndex = activeIndex + -amount;
				
				if(newIndex < 0) {
					newIndex = buttons.size + newIndex;
				}
				else {
					newIndex %= buttons.size;
				}
				
				changeActiveIndex(newIndex);
				
				return true;
			}
		});
	}
	
	private void changeActiveIndex(final int newIndex) {
		buttons.get(activeIndex).shrink();
		buttons.get(newIndex).grow();
		activeIndex = newIndex;
	}
	
	private void changeActiveButton(final SkillButton button) {
		for(int i = 0; i < buttons.size; i += 1) {
			if(button == buttons.get(i)) {
				changeActiveIndex(i);
				return;
			}
		}
	}
	
	@Override
	public void setStage(final Stage stage) {
		super.setStage(stage);
		if(stage != null) {
			stage.setScrollFocus(this);
		}
	}
	
	public void addButton(final SkillButton button) {
		buttons.add(button);
		addActor(button);
		button.setY(5f);
	}
	
	public Array<SkillButton> getButtons() {
		return buttons;
	}
	
	@Override
	public void act(final float delta) {
		super.act(delta);
		
		final float width = getWidth();
		
		final float space = gameUi.getApp().space.horizontal(1f);
		float layoutX = space;
		for(int i = 0; i < buttons.size; i += 1) {
			final SkillButton button = buttons.get(i);
			
			if(!button.isVisible()) {
				continue;
			}
			
			final float buttonWidth = button.getWidth();
			final float realButtonWidth = buttonWidth * button.getScaleX();

			button.setX(layoutX);
			layoutX += realButtonWidth + space;
		}
		
		setWidth(layoutX);
		//moveBy((layoutX - width) / 2f, 0f);
		setX(getParent().getWidth() / 2f - getWidth() / 2f);
		
		if(Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
			changeActiveIndex(0);
		}
		else if(Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
			changeActiveIndex(1);
		}
		else if(Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
			changeActiveIndex(2);
		}
		else if(Gdx.input.isKeyJustPressed(Keys.NUM_4)) {
			changeActiveIndex(3);	
		}
		else if(Gdx.input.isKeyJustPressed(Keys.NUM_5)) {
			changeActiveIndex(4);
		}
		else if(Gdx.input.isKeyJustPressed(Keys.NUM_6)) {
			changeActiveIndex(5);
		}
	}
	
	@Override
	public void setWidth(final float width) {
		super.setWidth(width);
		holder.setWidth(width);
	}
	
}
