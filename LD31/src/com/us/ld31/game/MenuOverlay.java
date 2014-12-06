package com.us.ld31.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.us.ld31.LD31;
import com.us.ld31.utils.TouchListener;

public class MenuOverlay extends Group {

	private LD31 app;
	
	public MenuOverlay(final LD31 app) {
		this.app = app;
		
		addListener(new TouchListener() {
			@Override
			public void touched() {
				MenuOverlay.this.remove();
				app.getGameWorld().begin();
			}
		});
	}
	
	public void begin() {}
	
}
