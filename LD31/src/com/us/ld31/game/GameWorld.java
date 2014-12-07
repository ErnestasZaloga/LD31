package com.us.ld31.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.us.ld31.LD31;
import com.us.ld31.game.Character.SkillSlot;
import com.us.ld31.game.foestuff.Foe;
import com.us.ld31.game.foestuff.FoeManager;
import com.us.ld31.game.skills.translocations.BlinkAwayOther;
import com.us.ld31.game.ui.Delegate;
import com.us.ld31.game.ui.GameUi;
import com.us.ld31.utils.Astar;
import com.us.ld31.utils.TouchListener;
import com.us.ld31.utils.tiles.WorldGenerator;
import com.us.ld31.utils.tiles.WorldMap;

public class GameWorld extends Group {

	private final LD31 app;
	private final Character character;
	private Astar astar;
	private FoeManager foeManager;
	private final WorldMap worldMap;
	private final GameUi gameUi;
	private final Actor characterController = new Actor();
	
	private boolean paused;
	
	public GameWorld(final LD31 app) {
		this.app = app;
		
		setTouchable(Touchable.childrenOnly);
		
		worldMap = new WorldMap();
		addActor(worldMap);
		
		// Svarbu kad butu sukurtas PO worldMap
		character = new Character(this);
		characterController.addListener(new TouchListener() {
			@Override
			public void touched() {
				if(getButton() == Input.Buttons.LEFT) {
					character.performSkill(SkillSlot.Primary);
					gameUi.getSkillBar().markForLevelUp();
				}
				else if(getButton() == Input.Buttons.RIGHT) {
					character.performSkill(SkillSlot.Secondary);
				}
			}
		});
		
		character.setRegion(app.assets.tileTree);
		character.setSize(32, 32);
		
		foeManager = new FoeManager(this);
		gameUi = new GameUi(app);
		gameUi.setDelegate(new Delegate() {
			@Override
			public void onPauseStateChanged() {
				if(!paused) {
					paused = true;
					gameUi.showPauseUi();
				}
				else {
					gameUi.hidePauseUi();
					paused = false;
				}
			}
		});
		
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
	
	public void begin() {
		WorldGenerator.generateWorld(app, worldMap);
		addActor(character);
		character.begin();
		
		character.setPosition(getWidth() / 2f, getHeight() / 2f);

		addActor(characterController);
		
		// Sitas turi buti paskutinis pridetas aktorius
		addActor(gameUi);
		
		gameUi.begin();
	}
	
	@Override
	public void act(final float delta) {
		super.act(delta);
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
//			Blink skill = new Blink(); 
//			ControlledBlink skill = new ControlledBlink();
//			BlinkOther skill = new BlinkOther();
			BlinkAwayOther skill = new BlinkAwayOther();
			skill.activate(character, this, 1);
		}
			
		if(paused) {
			gameUi.act(delta);
		}
		else {
			super.act(delta);
			
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
		
		characterController.setSize(width, height);
		
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
	
	public FoeManager getFoeManager() {
		return foeManager;
	}
}
