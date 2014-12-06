package com.us.ld31.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.us.ld31.LD31;

public class GameUi extends Group {

	private final LD31 app;
	private Delegate delegate;
	private final TopBar topBar;
	
	private final SideBar healthBar;
	private final SideBar expBar;
	
	private final SkillBar skillBar;
	
	public GameUi(final LD31 app) {
		this.app = app;
		topBar = new TopBar(this);
		addActor(topBar);
		
		setTouchable(Touchable.childrenOnly);
		
		healthBar = new SideBar(this, Color.valueOf("AB0000"));
		expBar = new SideBar(this, Color.valueOf("FFCC00"));
		
		addActor(healthBar);
		addActor(expBar);
		
		final float barWidth = app.space.vertical(2);
		final float barHeight = app.space.horizontal(25);
		
		expBar.setPercent(0.7f);
		healthBar.setPercent(0.35f);
		
		healthBar.setSize(barWidth, barHeight);
		expBar.setSize(barWidth, barHeight);
		
		skillBar = new SkillBar(this);
		addActor(skillBar);
	}
	
	public SideBar getHealthBar() {
		return healthBar;
	}
	
	public SideBar getExpBar() {
		return expBar;
	}
	
	public TopBar getTopBar() {
		return topBar;
	}
	
	public LD31 getApp() {
		return app;
	}
	
	public void setDelegate(final Delegate delegate) {
		this.delegate = delegate;
	}
	
	public Delegate getDelegate() {
		return delegate;
	}
	
	public void begin() {
		topBar.begin();
	}
	
	@Override
	public void setSize(final float width, 
						final float height) {
		
		super.setSize(width, height);
		topBar.setWidth(width);
		topBar.setY(height - topBar.getHeight());
		
		final float freeHeight = height - topBar.getHeight();
		
		expBar.setX(width - expBar.getWidth() - app.space.horizontal(1f));
		expBar.setY(freeHeight / 2f - expBar.getHeight() / 2f);
		
		healthBar.setX(app.space.horizontal(1f));
		healthBar.setY(freeHeight / 2f - healthBar.getHeight() / 2f);
		
		skillBar.setPosition(
				width / 2f - skillBar.getWidth() / 2f,
				app.space.vertical(1));
	}
	
}
