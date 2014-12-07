package com.us.ld31.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.us.ld31.game.skills.SkillState;
import com.us.ld31.game.skills.SkillTree;
import com.us.ld31.utils.SpriteActor;
import com.us.ld31.utils.TouchListener;

public class Skillbook extends Group {

	private static final int ICON_SIZE = 48;
	
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
			levelUpButton.setRegion(gameUi.getApp().assets.uiMissing);
			
			setSize(icon.getWidth(), icon.getHeight());
			
			addActor(icon);
			addActor(iconOverlay);
			addActor(levelUpButton);
			addActor(label);
			
			iconOverlay.setVisible(skill.getLevel() == 0);
			levelUpButton.setVisible(false);
			label.setVisible(false);
			
			levelUpButton.addListener(new TouchListener() {
				@Override
				public void touched() {
					final Delegate delegate = gameUi.getDelegate();
					if(delegate != null) {
						delegate.onLevelUp(SkillIcon.this);
					}
				}
			});
		}
		
		public void enableForLevelUp() {
			label.setVisible(skill.getLevel() > 0);
			levelUpButton.setVisible(skill.getLevel() < 2);
		}
		
		public void disableForLevelUp() {
			levelUpButton.setVisible(false);
		}
		
		public void updateLevel() {
			iconOverlay.setVisible(skill.getLevel() == 0);
			levelUpButton.setVisible(skill.getLevel() < 2);
			label.setText("" + skill.getLevel());
			label.pack();
			label.setVisible(true);
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
		
		public SkillTreeUi(final GameUi gameUi) {
			this.gameUi = gameUi;
			setTransform(false);
			
			addActor(highlighters);
			addActor(icons);
			
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
					layoutNode(icon);
				}
			}
			
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
			
			final float space = parent.getWidth();
			
			if(sub.size == 1) {
				final SkillIcon icon = createSkillIcon(sub.get(0));
				icon.setX(parent.getX());
				icon.setY(parent.getY() - space - icon.getHeight());
				
				makeHighlighter(parent, icon);
			}
			else {
				
				final float width = (space * (sub.size - 1)) + sub.size * parent.getWidth();
				float x = parent.getX() - width / 2f;
				
				for(int i = 0; i < sub.size; i += 1) {
					final SkillIcon icon = createSkillIcon(sub.get(i));
					icon.setX(x);
					icon.setY(parent.getY() - space - icon.getHeight());
					
					makeHighlighter(parent, icon);
					
					x += icon.getWidth() + space;
					
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
			highlight.setSize(3, length);
			
			highlight.setOrigin(highlight.getWidth() / 2f, highlight.getHeight());
			highlight.setRotation(angle);
			
			highlight.setPosition(from.getX() + from.getWidth() / 2f, from.getY() + from.getHeight() / 2f);
		
			highlighters.addActor(highlight);
		}
		
		public void enableForLevelUp() {
			
		}
		
		public void disableForLevelUp() {
			
		}
	}
	
	private final GameUi gameUi;
	private final Group table = new Group();
	private final SpriteActor tableBackground = new SpriteActor();
	private final Label title;

	private final SpriteActor apply = new SpriteActor();
	private final SpriteActor cancel = new SpriteActor();
	
	private final SkillTreeUi[] skillTrees = new SkillTreeUi[3];
	
	public Skillbook(final GameUi gameUi) {
		setTransform(false);
		this.gameUi = gameUi;
		
		title = new Label("Skill Book", new LabelStyle(gameUi.getApp().assets.fontSmall, new Color(1f, 1f, 1f, 1f)));
		title.pack();
		
		setTouchable(Touchable.childrenOnly);

		table.addActor(tableBackground);
		tableBackground.setRegion(gameUi.getApp().assets.uiBlock);
		tableBackground.setColor(Color.BLACK);

		for(int i = 0; i < skillTrees.length; i += 1) {
			skillTrees[i] = new SkillTreeUi(gameUi);
			table.addActor(skillTrees[i]);
		}
		
		table.addActor(title);
		
		addActor(table);
	}
	
	public void enableForLevelUp() {}
	public void disableForLevelUp() {}
	
	public void setSkillTree(final SkillTree[] skillTrees) {
		
	}
	
	public void begin() {
		
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
		
		final float skillTreeWidth = width / 4f;
		float skillTreeX = 0f;
		for(int i = 0; i < skillTrees.length; i += 1) {
			skillTrees[i].setX(skillTreeX);
			skillTreeX += skillTrees[i].getWidth();
		}
	}
	
}
