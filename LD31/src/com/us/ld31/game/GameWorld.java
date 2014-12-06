package com.us.ld31.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.us.ld31.LD31;

public class GameWorld extends Group {

	private final LD31 app;
	
	public GameWorld(final LD31 app) {
		this.app = app;
		
		Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fillCircle(5, 5, 5);
		
		Foe foe = new Foe(new TextureRegion(new Texture(pixmap)));
		
		addActor(foe);
	}
	
	public void begin() {
		
	}
	
}
