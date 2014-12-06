package com.us.ld31.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.us.ld31.LD31;
import com.us.ld31.utils.TouchListener;

public class GameWorld extends Group {

	private final LD31 app;
	private final Character character;
	
	public GameWorld(final LD31 app) {
		this.app = app;
		
		character = new Character(this);
		addListener(new TouchListener() {
			@Override
			public void touched() {
				
			}
		});
	}
	
	public void begin() {
		addActor(character);
	}
	
}
