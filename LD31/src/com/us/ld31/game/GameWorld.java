package com.us.ld31.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.us.ld31.LD31;
import com.us.ld31.game.foestuff.Foe;
import com.us.ld31.game.foestuff.FoeManager;
import com.us.ld31.game.skills.translocations.Blink;
import com.us.ld31.game.skills.translocations.ControlledBlink;
import com.us.ld31.game.ui.GameUi;
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
	private final GameUi gameUi;
	
	public GameWorld(final LD31 app) {
		this.app = app;
		
		worldMap = new WorldMap();
		addActor(worldMap);
		
		character = new Character(worldMap);
		addListener(new TouchListener() {
			@Override
			public void touched() {
				gameUi.getTopBar().getStrWidget().setEditable(true);
				gameUi.getTopBar().getDexWidget().setEditable(true);
				gameUi.getTopBar().getIntWidget().setEditable(true);
				gameUi.getTopBar().getPtWidget().setVisible(true);
				
				character.attack();
			}
		});
		
		character.setRegion(app.assets.tileTree);
		character.setSize(32, 32);
		
		foeManager = new FoeManager(this);
		gameUi = new GameUi(app);
		
		Log.trace(worldMap.getTilesX());
		
		addListener(new TouchListener() {
			@Override
			public void touched() {
				Foe foe = foeManager.makeFoe(app.assets.tileHouse, 7, 6);
				foe.setSize(32, 32);
				
				Vector2 pos = foeManager.getRandomSpawnPoint();
				foe.setPosition(pos.x, pos.y);
				
				addActor(foe);
			}
		});
	}
	
	public LD31 getApp() {
		return app;
	}
	
	public void begin() {
		WorldGenerator.generateWorld(app, worldMap);
		addActor(character);
		character.begin();
		
		character.setPosition(getWidth() / 2f, getHeight() / 2f);
		
		//foe.setPosition(500, 100);
		//addActor(foe);
		
		// Sitas turi buti paskutinis pridetas aktorius
		addActor(gameUi);
		
		gameUi.begin();
	}
	
	@Override
	public void act(final float delta) {
		super.act(delta);
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
//			Blink skill = new Blink();
			ControlledBlink skill = new ControlledBlink();
			skill.activate(character, this, 1);
		}
		
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
						float height) {
		
		height -= gameUi.getTopBar().getHeight();
		super.setSize(width, height);
		
		worldMap.setSize(width, height);
		gameUi.setSize(width, height + gameUi.getTopBar().getHeight());
		
		astar = new Astar(worldMap.getTilesX(), worldMap.getTilesY(), new Astar.Listener() {
			
			@Override
			public boolean isValid(int x, int y) {
				return worldMap.isWalkable(x, y);
			}
		});
		
		foeManager.setupSpawnPositions();
		
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
