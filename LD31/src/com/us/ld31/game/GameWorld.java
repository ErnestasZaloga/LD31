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
import com.us.ld31.utils.tiles.WorldGenerator;
import com.us.ld31.utils.tiles.WorldMap;

public class GameWorld extends Group {

	private final LD31 app;
	private final Character character;
	private final Astar astar;
	private Foe foe;
	private final WorldMap worldMap;
	
	public GameWorld(final LD31 app) {
		this.app = app;
		
		worldMap = new WorldMap();
		addActor(worldMap);
		
		character = new Character(worldMap);
		addListener(new TouchListener() {
			@Override
			public void touched() {
				character.attack();
			}
		});
		
		character.setRegion(app.assets.tileTree);
		character.setSize(32, 32);
		
		Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fillCircle(5, 5, 5);
		
		astar = new Astar(worldMap.getTilesX(), worldMap.getTilesY(), new Astar.Listener() {
			
			@Override
			public boolean isValid(int x, int y) {
				// TODO Auto-generated method stub
				return worldMap.isWalkable(x, y);
			}
		});
		
		addListener(new TouchListener() {
			@Override
			public boolean touchDown(final InputEvent event, 
								  	 final float x, 
								  	 final float y, 
								  	 final int pointer, 
								  	 final int button) {
				foe.travelTo(x / worldMap.getTileSize(), y / worldMap.getTileSize());
				
				
				
				return true;
			}
		});
		
		foe = new Foe(new TextureRegion(new Texture(pixmap)), this);
		addActor(foe);
	}
	
	public void begin() {
		WorldGenerator.generateWorld(app, worldMap);
		addActor(character);
		character.begin();
		
		character.setPosition(getWidth() / 2f, getHeight() / 2f);
	}
	
	@Override
	public void act(final float delta) {
		super.act(delta);
		
		if(isTouchable()) {
			for(int i = 0; i < Character.MovementDirection.list.length; i += 1) {
				final Character.MovementDirection direction = Character.MovementDirection.list[i];
				if(Gdx.input.isKeyJustPressed(direction.key)) {
					character.movement(direction, true);
				}
				else if(!Gdx.input.isKeyPressed(direction.key) && character.isMoving(direction)) {
					character.movement(direction, false);
				}
			}
		}
	}
	
	@Override
	public void setSize(final float width, 
						final float height) {
		
		super.setSize(width, height);
		worldMap.setSize(width, height);
	}
	
	public Astar getAstar() {
		return astar;
	}
	
	public WorldMap getWorldMap() {
		return worldMap;
	}
	
}
