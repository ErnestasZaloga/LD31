package com.us.ld31.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.us.ld31.game.skills.SkillState;
import com.us.ld31.utils.Log;
import com.us.ld31.utils.SpriteActor;
import com.us.ld31.utils.TouchListener;
import com.us.ld31.utils.steps.Steps;
import com.us.ld31.utils.steps.scene.ActorSteps;

public class SkillBar extends Group {

	public static class SkillButton extends Group {
		private final GameUi gameUi;
		
		private final SpriteActor border = new SpriteActor();
		private final SpriteActor icon = new SpriteActor();
		private final SpriteActor cooldownOverlay = new SpriteActor();
		private final Label mapping;
		private SkillState state;
		
		private boolean coolingDown;
		private boolean shrinked = true;
		
		public SkillButton(final SkillBar bar, 
						   final GameUi gameUi, 
						   final int mappingCode) {
			
			this.gameUi = gameUi;
			
			border.setRegion(gameUi.getApp().assets.uiBlock);
			border.setColor(Color.BLACK);
			icon.setRegion(gameUi.getApp().assets.uiMissing);
	
			cooldownOverlay.setRegion(gameUi.getApp().assets.uiBlock);
			cooldownOverlay.setColor(Color.RED);
			cooldownOverlay.getColor().a = 0f;
			
			mapping = new Label("", new LabelStyle(gameUi.getApp().assets.fontBig, new Color(1f, 1f, 1f, 1f)));
			
			addActor(border);
			addActor(icon);
			addActor(cooldownOverlay);
			addActor(mapping);
			
			icon.setSize(48, 48);
			border.setSize(58, 58);
			mapping.setPosition(border.getWidth() - icon.getWidth(), border.getHeight() - icon.getHeight());
			
			setSize(border.getWidth(), border.getHeight());
			icon.setPosition(getWidth() / 2f - icon.getWidth() / 2f, getHeight() / 2f - icon.getHeight() / 2f);
			cooldownOverlay.setPosition(icon.getX(), icon.getY());
			cooldownOverlay.setSize(icon.getWidth(), icon.getHeight());
			
			setOrigin(0f, 0f);
			
			setScale(0.6f, 0.6f);
			
			mapping.setText("" + mappingCode);
			mapping.pack();
			
			addListener(new TouchListener() {
				private int touchX;
				private int touchY;
				private float startX;
				private float startY;
				private boolean dragged;
				
				@Override
				public void touched() {
					bar.changeActiveButton(SkillButton.this);
					touchX = Gdx.input.getX();
					touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
				
					startX = SkillButton.this.getX();
					startY = SkillButton.this.getY();
					
					toFront();
					dragged = false;
				}
				
				@Override
				public void dragged() {
					dragged = true;
					final int deltaX = Gdx.input.getX() - touchX;
					final int deltaY = (Gdx.graphics.getHeight() - Gdx.input.getY()) - touchY;
				
					setPosition(startX + deltaX, startY + deltaY);
					bar.disableLayout();
				}
				
				@Override
				public void untouched() {
					if(dragged) {
						final int mouseX = Gdx.input.getX();
						final int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
					
						if(bar.exchangeSkill(SkillButton.this, mouseX, mouseY)) {
							bar.enableLayout();
							setPosition(startX, startY);
						}
						else {
							setTouchable(Touchable.disabled);
							addAction(
									Steps.action(
											Steps.sequence(
												ActorSteps.moveTo(startX, startY, 0.1f, Interpolation.circleOut),
												ActorSteps.touchable(Touchable.enabled),
												Steps.run(new Runnable() {
													@Override
													public void run() {
														bar.enableLayout();
													}
												}))));
						}
					}
				}
			});
			
			setState(null);
		}
		
		@Override
		public void act(final float delta) {
			super.act(delta);
			
			if(state != null && !gameUi.isPauseUiVisible()) {
				if(state.getCooldownLeft() > 0f) {
					coolingDown = true;
					cooldownOverlay.setColor(Color.BLACK);
					cooldownOverlay.getColor().a = 0.5f;
					cooldownOverlay.setHeight(icon.getHeight() * (state.getCooldownLeft() / state.getCooldown()));
				}
				else {
					if(coolingDown) {
						coolingDown = false;
						cooldownOverlay.setHeight(icon.getHeight());
						cooldownOverlay.clearActions();
						cooldownOverlay.setColor(Color.GREEN);
						cooldownOverlay.getColor().a = 0f;
						cooldownOverlay.addAction(
								Steps.action(
										Steps.sequence(
												ActorSteps.alphaTo(0.7f, 0.5f, Interpolation.circleOut),
												ActorSteps.alphaTo(0f, 0.5f))));
					}
				}
			}
		}

		public void setState(final SkillState state) {
			this.state = state;
			
			coolingDown = false;
			
			final float iconSize = icon.getWidth();
			if(state != null) {
				icon.setRegion(state.getSkillInfo().icon);
			}
			else {
				icon.setRegion(gameUi.getApp().assets.uiMissing);
			}
			
			icon.setSize(iconSize, iconSize);
		}
		
		public SkillState getState() {
			return state;
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
									ActorSteps.scaleTo(0.6f, 0.6f, 0.3f, Interpolation.circleOut))));
		}
	}
	
	private final GameUi gameUi;
	private final SpriteActor holder = new SpriteActor();
	private final Array<SkillButton> buttons = new Array<SkillButton>();
	private int activeIndex;
	private final SpriteActor skillbookIcon = new SpriteActor();
	private final Label levelUpRemainder;
	private boolean layoutEnabled = true;
	
	public SkillBar(final GameUi gameUi) {
		setTransform(false);
		this.gameUi = gameUi;
		
		levelUpRemainder = new Label("Click to Level Up!", new LabelStyle(gameUi.getApp().assets.fontSmall, new Color(1f, 1f, 1f, 1f)));
		levelUpRemainder.pack();
		levelUpRemainder.addAction(
				Steps.action(
						Steps.repeat(
							Steps.sequence(
									ActorSteps.alphaTo(0f, 1f, Interpolation.linear),
									ActorSteps.alphaTo(1f, 0.3f, Interpolation.circleOut)))));
		skillbookIcon.setRegion(gameUi.getApp().assets.uiMissing);
		
		holder.setRegion(gameUi.getApp().assets.uiBlock);
		holder.setColor(Color.BLACK);
		
		holder.setHeight(gameUi.getApp().space.vertical(4));
		
		addActor(holder);
		addActor(skillbookIcon);
		
		for(int i = 0; i < 6; i += 1) {
			addButton(new SkillButton(this, gameUi, i + 1));
		}
		setHeight(5f + buttons.get(0).getHeight());
		
		skillbookIcon.setSize(32, 32);
		
		buttons.get(0).grow();
		
		final TouchListener skillBookListener = new TouchListener() {
			@Override
			public void touched() {
				levelUpRemainder.remove();
				gameUi.showSkillbook();
			}
		};
		skillbookIcon.addListener(skillBookListener);
		levelUpRemainder.addListener(skillBookListener);
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
	
	public boolean exchangeSkill(final SkillButton original, final float x, final float y) {
		final SkillButton targetButton = findButtonInCoords(x, y, true);
		if(targetButton != null) {
			final SkillState targetSkill = targetButton.getState();
			targetButton.setState(original.getState());
			original.setState(targetSkill);
			
			changeActiveButton(targetButton);
		}
		
		return false;
	}
	
	public boolean addSkill(final SkillState skill, 
							final float x, 
							final float y) {
		
		final SkillButton button = findButtonInCoords(x, y, false);
		if(button != null) {
			button.setState(skill);
			if(buttons.indexOf(button, true) == activeIndex) {
				final Delegate delegate = gameUi.getDelegate();
				if(delegate != null) {
					delegate.onActiveSkillChanged(button);
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public SkillButton findButtonInCoords(final float x, final float y, final boolean skipActive) {
		final Vector2 tmp = new Vector2();
		for(int i = 0; i < buttons.size; i += 1) {
			if(skipActive && i == activeIndex) {
				continue;
			}
			
			final SkillButton button = buttons.get(i);
			button.localToStageCoordinates(tmp.set(0f, 0f));
			
			final float btnX = tmp.x;
			final float btnY = tmp.y;
			
			button.localToStageCoordinates(tmp.set(button.getWidth(), button.getHeight()));
			
			final float btnRight = tmp.x;
			final float btnTop = tmp.y;

			/*System.out.println("MouseX: " + x);
			System.out.println("MouseY: " + y);
			System.out.println("BtnX: " + btnX);
			System.out.println("BtnY: " + btnY);
			System.out.println("BtnRight: " + btnRight);
			System.out.println("BtnTop: " + btnTop);*/
			
			if(x < btnX || x > btnRight || y < btnY || y > btnTop) {
				continue;
			}
			
			return button;
		}
		
		return null;
	}
	
	public void markForLevelUp() {
		addActor(levelUpRemainder);
		levelUpRemainder.setPosition(skillbookIcon.getRight(), skillbookIcon.getY() + skillbookIcon.getHeight() / 2f - levelUpRemainder.getHeight() * 0.3f);
	}
	
	private void changeActiveIndex(final int newIndex) {
		if(newIndex == activeIndex) {
			return;
		}
		
		buttons.get(activeIndex).shrink();
		buttons.get(newIndex).grow();
		activeIndex = newIndex;
		
		if(gameUi.getDelegate() != null) {
			gameUi.getDelegate().onActiveSkillChanged(buttons.get(newIndex));
		}
	}
	
	public void changeActiveButton(final SkillButton button) {
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
		
		if(layoutEnabled) {
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
			setX(getParent().getWidth() / 2f - getWidth() / 2f);
		}
		
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
	
	private void enableLayout() {
		layoutEnabled = true;
	}
	
	private void disableLayout() {
		layoutEnabled = false;
	}
	
	@Override
	public void setWidth(final float width) {
		super.setWidth(width);
		holder.setWidth(width);
		skillbookIcon.setX(width + gameUi.getApp().space.horizontal(1f));
	}
	
}
