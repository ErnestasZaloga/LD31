package com.us.ld31.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.us.ld31.game.skills.SkillState;
import com.us.ld31.game.skills.SkillTree;
import com.us.ld31.game.ui.SkillBar.SkillButton;
import com.us.ld31.utils.Log;
import com.us.ld31.utils.SpriteActor;
import com.us.ld31.utils.TouchListener;
import com.us.ld31.utils.steps.Steps;
import com.us.ld31.utils.steps.scene.ActorSteps;

public class Skillbook extends Group {

	private static final int ICON_SIZE = 32;
	
	public static class SkillIcon extends Group {
		private final GameUi gameUi;
		private final SkillState skill;
		
		private final SpriteActor icon = new SpriteActor();
		private final Label label;
		private final SpriteActor iconOverlay = new SpriteActor();
		private final SpriteActor levelUpButton = new SpriteActor();
		
		public SkillIcon(final GameUi gameUi, 
						 final SkillState skill) {
			
			this.gameUi = gameUi;
			this.skill = skill;
			
			setTransform(false);
			
			icon.setRegion(skill.getSkillInfo().icon);
			icon.setSize(ICON_SIZE, ICON_SIZE);
			iconOverlay.setRegion(gameUi.getApp().assets.uiBlock);
			iconOverlay.setColor(Color.BLACK);
			iconOverlay.getColor().a = 0.5f;
			iconOverlay.setSize(icon.getWidth(), icon.getHeight());
			label = new Label("0", new LabelStyle(gameUi.getApp().assets.fontBig, Color.YELLOW));
			levelUpButton.setRegion(gameUi.getApp().assets.uiArrowUp);
			
			setSize(icon.getWidth(), icon.getHeight());
			levelUpButton.setSize(icon.getWidth() / 2f, icon.getHeight() / 2f);
			levelUpButton.setX(getWidth() - levelUpButton.getWidth());
			
			addActor(icon);
			addActor(iconOverlay);
			addActor(levelUpButton);
			addActor(label);
			
			iconOverlay.setVisible(skill.getLevel() == 0);
			levelUpButton.setVisible(false);
			label.setVisible(false);
			
			addListener(new TouchListener() {
				private int touchX;
				private int touchY;
				private float startX;
				private float startY;
				private boolean dragged;
				
				@Override
				public void touched() {
					if(levelUpButton.isVisible()) {
						final Delegate delegate = gameUi.getDelegate();
						if(delegate != null) {
							delegate.onLevelUp(SkillIcon.this);
						}
					}
					
					touchX = Gdx.input.getX();
					touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
				
					startX = SkillIcon.this.getX();
					startY = SkillIcon.this.getY();
					
					toFront();
					dragged = false;
				}
				
				@Override
				public void dragged() {
					if(skill.getLevel() > 0) {
						dragged = true;
						final int currentMouseX = Gdx.input.getX();
						final int currentMouseY = (Gdx.graphics.getHeight() - Gdx.input.getY());
						
						final int deltaX = currentMouseX - touchX;
						final int deltaY = currentMouseY - touchY;
					
						setPosition(startX + deltaX, startY + deltaY);
						
						final SkillButton button = gameUi.getSkillBar().findButtonInCoords(currentMouseX, currentMouseY, false);
						if(button != null) {
							gameUi.getSkillBar().changeActiveButton(button);
						}
					}
				}
				
				@Override
				public void untouched() {
					if(dragged && skill.getLevel() > 0) {
						final int mouseX = Gdx.input.getX();
						final int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
					
						if(gameUi.getSkillBar().addSkill(getSkillState(), mouseX, mouseY)) {
							setPosition(startX, startY);
						}
						else {
							setTouchable(Touchable.disabled);
							addAction(
									Steps.action(
											Steps.sequence(
												ActorSteps.moveTo(startX, startY, 0.1f, Interpolation.circleOut),
												ActorSteps.touchable(Touchable.enabled))));
						}
					}
				}
			});
			
			addListener(new InputListener() {
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					gameUi.getSkillbook().showInfo(skill.getSkillInfo().name, skill.getSkillInfo().description);
				}
				
				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
					gameUi.getSkillbook().hideInfo();
				}
			});
		}
		
		public void enableForLevelUp() {
			boolean available = false || skill.getParents().size == 0;
			for(int i = 0; i < skill.getParents().size; i += 1) {
				if(skill.getParents().get(0).getLevel() > 0) {
					available = true;
				}
			}
			
			if(available) {
				levelUpButton.setVisible(skill.getLevel() < skill.getSkillInfo().levelCap);
			}
			
			label.setText("" + skill.getLevel());
			label.pack();
			label.setVisible(skill.getLevel() > 0);
			iconOverlay.setVisible(skill.getLevel() == 0);
		}
		
		public void disableForLevelUp() {
			levelUpButton.setVisible(false);
		}
		
		public SkillState getSkillState() {
			return skill;
		}
	}
	
	public static class SkillTreeUi extends Group {
		private final GameUi gameUi;
		private final Array<SkillIcon> skillIcons = new Array<SkillIcon>();
		private final Group highlighters = new Group();
		private final Group icons = new Group();
		private final Label title;
		
		public SkillTreeUi(final GameUi gameUi) {
			this.gameUi = gameUi;
			setTransform(false);
			
			title = new Label("Title", new LabelStyle(gameUi.getApp().assets.fontSmall, Color.GREEN));
			title.setFontScale(0.8f);
			
			addActor(highlighters);
			addActor(icons);
			addActor(title);
			
			icons.setTransform(false);
			highlighters.setTransform(false);
		}

		public void setSkillTree(final SkillTree skillTree) {
			skillIcons.clear();
			icons.clearChildren();
			highlighters.clearChildren();
			
			final float width = getWidth();
			final Array<SkillState> nodes = skillTree.states;
			
			if(nodes.size == 1) {
				final SkillIcon icon = createSkillIcon(nodes.get(0));
				icon.setX(getWidth() / 2f - icon.getWidth() / 2f);
				icon.setY(-icon.getHeight());
				layoutNode(icon);
			}
			else {
				final SkillIcon first = createSkillIcon(nodes.first());
				final SkillIcon last = createSkillIcon(nodes.peek());
				
				last.setX(width - last.getWidth());
				
				final float div = width / nodes.size - 1;
				for(int i = 1; i < nodes.size - 1; i += 1) {
					final SkillIcon icon = createSkillIcon(nodes.get(i));
					icon.setX(div * i - icon.getWidth() / 2f);
					icon.setY(-icon.getHeight());
					layoutNode(icon);
				}
			}
			
			float minY = Float.MAX_VALUE;
			final Array<Actor> iconsChildren = icons.getChildren();
			for(int i = 0; i < iconsChildren.size; i += 1) {
				if(iconsChildren.get(i).getY() < minY) {
					minY = iconsChildren.get(i).getY();
				}
			}
			
			for(int i = 0; i < iconsChildren.size; i += 1) {
				iconsChildren.get(i).moveBy(0f, -minY);
			}
			
			final Array<Actor> highlightsChildren = highlighters.getChildren();
			for(int i = 0; i < highlightsChildren.size; i += 1) {
				highlightsChildren.get(i).moveBy(0, -minY);
			}

			title.setText(skillTree.name);
			title.pack();
			title.setPosition(getWidth() / 2f - title.getWidth() / 2f, getHeight() - title.getHeight());
			
			highlighters.setHeight(-minY);
			icons.setHeight(-minY);
			
			highlighters.setY(getHeight() - icons.getHeight() - title.getHeight() - gameUi.getApp().space.vertical(1f));
			icons.setY(getHeight() - icons.getHeight() - title.getHeight() - gameUi.getApp().space.vertical(1f));
		}
		
		private SkillIcon createSkillIcon(final SkillState state) {
			final SkillIcon icon = new SkillIcon(gameUi, state);
			skillIcons.add(icon);
			icons.addActor(icon);
			return icon;
		}
		
		private void layoutNode(final SkillIcon parent) {
			final Array<SkillState> sub = parent.getSkillState().getChildren();
			
			if(sub.size == 0) {
				return;
			}
			
			final float space = parent.getWidth() * 1.5f;
			
			if(sub.size == 1) {
				final SkillIcon icon = createSkillIcon(sub.get(0));
				icon.setX(parent.getX());
				icon.setY(parent.getY() - space - icon.getHeight());
				
				makeHighlighter(parent, icon);
				
				layoutNode(icon);
			}
			else {
				
				final float width = (space * (sub.size - 1)) + sub.size * parent.getWidth();
				float x = parent.getX() + parent.getWidth() / 2f - width / 2f;
				
				for(int i = 0; i < sub.size; i += 1) {
					final SkillIcon icon = createSkillIcon(sub.get(i));
					icon.setX(x);
					icon.setY(parent.getY() - space - icon.getHeight());
					
					makeHighlighter(parent, icon);
					
					x += icon.getWidth() + space;

					layoutNode(icon);
				}
			}
		}
		
		private void makeHighlighter(final SkillIcon from, 
									 final SkillIcon to) {
			
			final Vector2 tmp = new Vector2(
					(to.getX() + to.getWidth() / 2f) - (from.getX() + from.getWidth() / 2f),
					(to.getY() + to.getHeight() / 2f) - (from.getY() + from.getHeight() / 2f));
			
			final float angle = tmp.angle();
			final float length = tmp.len();
			
			final SpriteActor highlight = new SpriteActor();
			highlight.setRegion(gameUi.getApp().assets.uiBlock);
			highlight.setSize(1, length);
			highlight.setColor(Color.GREEN);
			highlight.getColor().a = 0.5f;
			
			highlight.setOrigin(highlight.getWidth() / 2f, highlight.getHeight());
			highlight.setRotation(angle + 90f);
			
			highlight.setPosition(from.getX() + from.getWidth() / 2f - highlight.getWidth() / 2f, from.getY() + from.getHeight() / 2f - highlight.getHeight());
		
			highlighters.addActor(highlight);
		}
		
		public void enableForLevelUp() {
			for(int i = 0; i < skillIcons.size; i += 1) {
				skillIcons.get(i).enableForLevelUp();
			}
		}
		
		public void disableForLevelUp() {
			for(int i = 0; i < skillIcons.size; i += 1) {
				skillIcons.get(i).disableForLevelUp();
			}
		}
	}
	
	private final GameUi gameUi;
	private final Group table = new Group();
	private final SpriteActor tableBackground = new SpriteActor();
	private final Label title;

	private final SpriteActor apply = new SpriteActor();
	private final SpriteActor cancel = new SpriteActor();
	
	private final SkillTreeUi[] skillTrees = new SkillTreeUi[3];
	private final SpriteActor sep1 = new SpriteActor();
	private final SpriteActor sep2 = new SpriteActor();
	private final SpriteActor sep3 = new SpriteActor();
	
	private final Label points;
	
	private final Label infoTitle;
	private final Label infoText;
	
	public Skillbook(final GameUi gameUi) {
		setTransform(false);
		this.gameUi = gameUi;
		
		points = new Label("SP: 0", new LabelStyle(gameUi.getApp().assets.fontSmall, Color.YELLOW));
		
		infoTitle = new Label("Title", new LabelStyle(gameUi.getApp().assets.fontSmall, Color.YELLOW));
		infoText = new Label("Text", new LabelStyle(gameUi.getApp().assets.fontSmall, Color.WHITE));
		
		infoTitle.setAlignment(Align.center, Align.center);
		infoTitle.setEllipse(true);
		
		infoText.setFontScale(0.8f);
		infoText.setAlignment(Align.top, Align.center);
		infoText.setWrap(true);
		
		sep1.setRegion(gameUi.getApp().assets.uiBlock);
		sep2.setRegion(gameUi.getApp().assets.uiBlock);
		sep3.setRegion(gameUi.getApp().assets.uiBlock);
		
		sep1.setColor(Color.GREEN);
		sep2.setColor(Color.GREEN);

		sep1.getColor().a = 0.5f;
		sep2.getColor().a = 0.5f;
		
		sep1.setWidth(1f);
		sep2.setWidth(1f);
		sep3.setWidth(1f);
		
		title = new Label("Skill Book", new LabelStyle(gameUi.getApp().assets.fontSmall, new Color(1f, 1f, 1f, 1f)));
		title.pack();
		
		setTouchable(Touchable.childrenOnly);

		table.addActor(tableBackground);
		tableBackground.setRegion(gameUi.getApp().assets.uiSkillbookBg);
		//tableBackground.setColor(Color.BLACK);

		table.addActor(sep1);
		table.addActor(sep2);
		table.addActor(sep3);

		table.addActor(points);
		
		table.addActor(infoTitle);
		table.addActor(infoText);
		
		infoTitle.setVisible(false);
		infoText.setVisible(false);
		
		for(int i = 0; i < skillTrees.length; i += 1) {
			skillTrees[i] = new SkillTreeUi(gameUi);
			table.addActor(skillTrees[i]);
		}
		
		table.addActor(title);
		
		addActor(table);
	}
	
	public void setSkillPoints(final int skillPoints) {
		points.setText("SP: " + skillPoints);
		points.pack();
		
		points.setPosition(gameUi.getApp().space.horizontal(3f), title.getY());
	}
	
	public void enableForLevelUp() {
		for(int i = 0; i < skillTrees.length; i += 1) {
			skillTrees[i].enableForLevelUp();
		}
	}
	
	public void disableForLevelUp() {
		for(int i = 0; i < skillTrees.length; i += 1) {
			skillTrees[i].disableForLevelUp();
		}
	}
	
	public void setSkillTree(final SkillTree[] skillTrees) {
		for(int i = 0; i < skillTrees.length; i += 1) {
			this.skillTrees[i].setSkillTree(skillTrees[i]);
		}
	}
	
	public void begin() {
		
	}
	
	public void onPauseHidden() {
		remove();
	}
	
	private void hideInfo() {
		infoTitle.setVisible(false);
		infoText.setVisible(false);
	}
	
	private void showInfo(final String title, 
						  final String text) {

		infoTitle.setText(title);
		infoText.setText(text);
		
		infoTitle.setVisible(true);
		infoText.setVisible(true);
	}
	
	@Override
	public void setSize(final float width, 
						final float height) {
		
		super.setSize(width, height);
		table.setWidth(width * 0.9f);
		table.setHeight(height * 0.6f);
		tableBackground.setSize(table.getWidth(), table.getHeight());
		
		table.setPosition(
				width / 2f - table.getWidth() / 2f,
				height / 2f - table.getHeight() / 2f);
		
		title.setPosition(
				table.getWidth() / 2f - title.getWidth() / 2f,
				table.getHeight() - title.getHeight());
		
		final float skillTreeWidth = table.getWidth() / 4f;
		final float skillTreeHeight = title.getY() - title.getHeight() * 0.25f;
		
		float skillTreeX = 0f;
		for(int i = 0; i < skillTrees.length; i += 1) {
			skillTrees[i].setX(skillTreeX);
			skillTrees[i].setSize(skillTreeWidth, skillTreeHeight);
			skillTreeX += skillTrees[i].getWidth();
		}
		
		sep1.setHeight(skillTrees[0].getHeight() * 0.9f);
		sep2.setHeight(sep1.getHeight());
		sep3.setHeight(sep1.getHeight());
		
		sep1.setPosition(skillTrees[0].getRight(), skillTrees[0].getHeight() / 2f - sep1.getHeight() / 2f);
		sep2.setPosition(skillTrees[1].getRight(), skillTrees[1].getHeight() / 2f - sep2.getHeight() / 2f);
		sep3.setPosition(skillTrees[2].getRight(), skillTrees[2].getHeight() / 2f - sep3.getHeight() / 2f);
		
		infoTitle.setWidth(skillTreeWidth);
		infoTitle.setY(skillTreeHeight - infoTitle.getHeight());
		infoText.setSize(skillTreeWidth * 0.9f, skillTreeHeight - infoTitle.getHeight());
		
		infoTitle.setX(skillTrees[2].getRight());
		infoText.setX(skillTrees[2].getRight() + skillTreeWidth * 0.1f);
		
		points.setPosition(gameUi.getApp().space.horizontal(3f), title.getY());
	}
	
}
