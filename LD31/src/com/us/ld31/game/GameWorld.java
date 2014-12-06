package com.us.ld31.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.us.ld31.LD31;
import com.us.ld31.utils.Astar;
import com.us.ld31.utils.SpriteActor;
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
		
		astar = new Astar(worldMap.getTilesX(), worldMap.getTilesY(), new Astar.Listener() {
			
			@Override
			public boolean isValid(int x, int y) {
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
				
				//Log.trace(location);
				//foe.travelTo(x / worldMap.getTileSize(), y / worldMap.getTileSize());
				
				return true;
			}
		});
		
		foe = new Foe(app.assets.tileHouse, this);
		foe.setSize(32, 32);
		addActor(foe);
	}
	
	public void begin() {
		WorldGenerator.generateWorld(app, worldMap);
		addActor(character);
		character.begin();
		
		character.setPosition(getWidth() / 2f, getHeight() / 2f);
		
		final SpriteActor test = new SpriteActor(app.assets.tileRock);
		addActor(test);
		test.setSize(worldMap.getTileSize(), worldMap.getTileSize());
		
		test.setPosition(worldMap.getTileSize(), worldMap.getTileSize());
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
