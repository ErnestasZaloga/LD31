package com.us.ld31.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.us.ld31.LD31;
import com.us.ld31.game.foestuff.Foe;
import com.us.ld31.game.foestuff.FoeManager;
import com.us.ld31.utils.Astar;
import com.us.ld31.utils.Log;
import com.us.ld31.utils.TouchListener;
import com.us.ld31.utils.tiles.WorldGenerator;
import com.us.ld31.utils.tiles.WorldMap;



public class GameWorld extends Group {

	private final LD31 app;
	private final Character character;
	private Astar astar;
	//private Foe foe;
	private FoeManager foeManager;
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
		
		foeManager = new FoeManager(this);
		
		addListener(new TouchListener() {
			@Override
			public void touched() {
				Foe foe = foeManager.makeFoe(app.assets.tileHouse, 7, 6);
				foe.setSize(32, 32);
				
				foe.setPosition(getX(), getY());
				
				addActor(foe);
			}
		});
	}
	
	public void begin() {
		WorldGenerator.generateWorld(app, worldMap);
		addActor(character);
		character.begin();
		
		character.setPosition(getWidth() / 2f, getHeight() / 2f);
		
		//foe.setPosition(500, 100);
		//addActor(foe);
		
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
		
		astar = new Astar(worldMap.getTilesX(), worldMap.getTilesY(), new Astar.Listener() {
			
			@Override
			public boolean isValid(int x, int y) {
				return worldMap.isWalkable(x, y);
			}
		});
		
	}
	
	public Astar getAstar() {
		return astar;
	}
	
	public WorldMap getWorldMap() {
		return worldMap;
	}
	
	public Character getCharacter() {
		return character;
	}
	
}
