package com.us.ld31.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.us.ld31.LD31;
import com.us.ld31.utils.Astar;
import com.us.ld31.utils.TouchListener;

public class GameWorld extends Group {

	private final LD31 app;
	private final Character character;
	private final Astar astar;
	private Foe foe;
	
	public GameWorld(final LD31 app) {
		this.app = app;
		
		character = new Character(this);
		addListener(new TouchListener() {
			@Override
			public void touched() {
				
			}
		});
		
		Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fillCircle(5, 5, 5);
		
		astar = new Astar(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new Astar.Listener() {
			
			@Override
			public boolean isValid(int x, int y) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		
		addListener(new TouchListener() {
			@Override
			public boolean touchDown(final InputEvent event, 
								  	 final float x, 
								  	 final float y, 
								  	 final int pointer, 
								  	 final int button) {
				foe.travelTo(x, y);
				
				
				
				return true;
			}
		});
		
		foe = new Foe(new TextureRegion(new Texture(pixmap)), this);
		addActor(foe);
	}
	
	public void begin() {
		addActor(character);
	}
	
	public Astar getAstar() {
		return astar;
	}
	
}
