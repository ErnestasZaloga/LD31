package com.us.ld31.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.us.ld31.utils.SpriteActor;
import com.us.ld31.utils.TouchListener;

public class TopBar extends Group {

	public static enum Stat {
		STR("STR"),
		DEX("DEX"),
		INT("INT"),
		PT("PT"),
		GP("GOLD"),
		LVL("LVL");
		
		private final String name;
		
		private Stat(final String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	public static class StatWidget extends Group {
		private final SpriteActor arrowUp = new SpriteActor();
		private final SpriteActor arrowDown = new SpriteActor();
		private final Label title;
		private final Label value;
		private boolean editable;
		
		public StatWidget(final GameUi gameUi, 
						  final Stat stat,
						  final Color colorMod) {

			setTransform(false);
			
			final LabelStyle style = new LabelStyle(gameUi.getApp().assets.fontSmall, colorMod);
			title = new Label(stat.getName() + ": ", style);
			value = new Label("99", style);
			
			arrowUp.setRegion(gameUi.getApp().assets.uiArrowUp);
			arrowDown.setRegion(gameUi.getApp().assets.uiArrowDown);
			arrowUp.setSizeScale(0.35f);
			arrowDown.setSizeScale(0.35f);
			
			addActor(title);
			addActor(value);
			addActor(arrowUp);
			addActor(arrowDown);
			
			title.setFontScale(0.7f);
			title.pack();
			value.pack();
			
			arrowUp.addListener(new TouchListener() {
				@Override
				public void touched() {
					final Delegate delegate = gameUi.getDelegate();
					if(delegate != null) {
						delegate.onStatEdited(StatWidget.this, stat, 1);
					}
					arrowUp.setColor(Color.GREEN);
				}
				
				@Override
				public void untouched() {
					arrowUp.setColor(Color.WHITE);
				}
			});
			
			arrowDown.addListener(new TouchListener() {
				@Override
				public void touched() {
					final Delegate delegate = gameUi.getDelegate();
					if(delegate != null) {
						delegate.onStatEdited(StatWidget.this, stat, -1);
					}
					arrowDown.setColor(Color.GREEN);
				}
				
				@Override
				public void untouched() {
					arrowDown.setColor(Color.WHITE);
				}
			});

			title.setY(arrowDown.getTop());
			value.setPosition(title.getRight(), arrowDown.getTop());
			
			arrowDown.setX(value.getX() + value.getWidth() / 2f - arrowUp.getWidth() / 2f);
			arrowUp.setPosition(arrowDown.getX(), value.getTop() - arrowUp.getHeight() * 2f);
			
			setWidth(title.getWidth() + value.getWidth());
			setHeight(arrowUp.getTop());
			
			arrowDown.setVisible(false);
			arrowUp.setVisible(false);
		}
		
		public void setEditable(final boolean editable) {
			this.editable = editable;
			arrowDown.setVisible(editable);
			arrowUp.setVisible(editable);
			
			if(editable) {
				value.setColor(Color.YELLOW);
			}
			else {
				value.setColor(Color.WHITE);
			}
		}
		
		public boolean isEditable() {
			return editable;
		}
		
		public void setValue(final int value) {
			final float currentSize = this.value.getWidth();
			this.value.setText("" + value);
			this.value.pack();
			this.value.moveBy((this.value.getWidth() - currentSize) / 2f, 0f);
		}
	}
	
	private final GameUi gameUi;
	private final SpriteActor background = new SpriteActor();
	private final StatWidget strWidget;
	private final StatWidget dexWidget;
	private final StatWidget intWidget;
	private final StatWidget ptWidget;
	private final StatWidget gpWidget;
	private final StatWidget lvlWidget;
	
	public TopBar(final GameUi gameUi) {
		this.gameUi = gameUi;
		
		setTransform(false);
		
		background.setRegion(gameUi.getApp().assets.uiBlock);
		background.setColor(Color.BLACK);
		
		strWidget = new StatWidget(gameUi, Stat.STR, new Color(1f, 1f, 1f, 1f));
		dexWidget = new StatWidget(gameUi, Stat.DEX, new Color(1f, 1f, 1f, 1f));
		intWidget = new StatWidget(gameUi, Stat.INT, new Color(1f, 1f, 1f, 1f));
		ptWidget = new StatWidget(gameUi, Stat.PT, new Color(1f, 1f, 1f, 1f));
		gpWidget = new StatWidget(gameUi, Stat.GP, Color.valueOf("FFE100"));
		lvlWidget = new StatWidget(gameUi, Stat.LVL, new Color(1f, 1f, 1f, 1f));
		
		addActor(background);
		addActor(strWidget);
		addActor(dexWidget);
		addActor(intWidget);
		addActor(ptWidget);
		addActor(gpWidget);
		addActor(lvlWidget);
		
		final float space = gameUi.getApp().space.vertical(2);
		setHeight(lvlWidget.getHeight() + space);
		strWidget.setY(space / 2f);
		dexWidget.setY(space / 2f);
		intWidget.setY(space / 2f);
		ptWidget.setY(space / 2f);
		gpWidget.setY(space / 2f);
		lvlWidget.setY(space / 2f);
		
		background.setHeight(getHeight());
	}
	
	public StatWidget getStrWidget() {
		return strWidget;
	}
	
	public StatWidget getDexWidget() {
		return dexWidget;
	}
	
	public StatWidget getIntWidget() {
		return intWidget;
	}
	
	public StatWidget getPtWidget() {
		return ptWidget;
	}
	
	public StatWidget getGpWidget() {
		return gpWidget;
	}
	
	public StatWidget getLvlWidget() {
		return lvlWidget;
	}
	
	public void begin() {
		ptWidget.setVisible(false);
	}
	
	@Override
	public void setWidth(final float width) {
		super.setWidth(width);
		
		background.setWidth(width);
		final float space = gameUi.getApp().space.horizontal(4f);
		
		gpWidget.setX(width - gpWidget.getWidth() - space);
		lvlWidget.setX(gpWidget.getX() - lvlWidget.getWidth() - space);
		
		strWidget.setX(space);
		dexWidget.setX(strWidget.getRight() + space);
		intWidget.setX(dexWidget.getRight() + space);
		ptWidget.setX(intWidget.getRight() + space);
	}
	
}