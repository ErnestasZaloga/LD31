package com.us.ld31.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.us.ld31.LD31;
import com.us.ld31.game.PlayerCharacter.SkillSlot;
import com.us.ld31.game.combat.Projectile;
import com.us.ld31.game.combat.ProjectileFactory;
import com.us.ld31.game.foestuff.Foe;
import com.us.ld31.game.foestuff.FoeManager;
import com.us.ld31.game.skills.DebugSkillTree;
import com.us.ld31.game.skills.MeleeAttack;
import com.us.ld31.game.skills.warrior.Charge;
import com.us.ld31.game.skills.warrior.WarriorSkillTree;
import com.us.ld31.game.ui.Delegate;
import com.us.ld31.game.ui.GameUi;
import com.us.ld31.game.ui.SkillBar.SkillButton;
import com.us.ld31.game.ui.Skillbook.SkillIcon;
import com.us.ld31.utils.Astar;
import com.us.ld31.utils.TouchListener;
import com.us.ld31.utils.tiles.WorldGenerator;
import com.us.ld31.utils.tiles.WorldMap;

public class GameWorld extends Group {

	private final LD31 app;
	private final PlayerCharacter character;
	private Astar astar;
	private FoeManager foeManager;
	private final WorldMap worldMap;
	private final GameUi gameUi;
	private final Group foeGroup;
	private final Actor characterController = new Actor();
	
	private Group projectiles;
	private ProjectileFactory projectileFactory;
	
	private boolean paused;
	
	public GameWorld(final LD31 app) {
		this.app = app;
		
		projectiles = new Group();
		projectileFactory = new ProjectileFactory(app, projectiles);
		
		setTouchable(Touchable.childrenOnly);
		
		worldMap = new WorldMap();
		addActor(worldMap);
		
		// Svarbu kad butu sukurtas PO worldMap
		character = new PlayerCharacter(this);
		character.setPrimarySkill(new MeleeAttack());
		characterController.addListener(new TouchListener() {
			@Override
			public void touched() {
				if(getButton() == Input.Buttons.LEFT) {
//					projectileFactory.createRangedAttack(character.getX()+character.getWidth()/2, character.getY()+character.getHeight()/2, 90);
					character.performSkill(SkillSlot.Primary);
				}
				else if(getButton() == Input.Buttons.RIGHT) {
					character.performSkill(SkillSlot.Secondary);
				}
			}
		});
		
		character.setRegion(app.assets.tileTree);
		character.setSize(32, 32);
		
		foeManager = new FoeManager(this);
		foeGroup = new Group();
		
		gameUi = new GameUi(app);
		gameUi.setDelegate(new Delegate() {
			@Override
			public void onPauseStateChanged() {
				if(!paused) {
					paused = true;
					gameUi.showPauseUi();
				}
				else {
					if(character.getStats().getSkillPoints() > 0) {
						gameUi.getSkillBar().markForLevelUp();
					}
					
					gameUi.hidePauseUi();
					paused = false;
				}
			}
			
			@Override
			public void onActiveSkillChanged(final SkillButton button) {
				character.setSecondarySkill(button.getState());
			}

			@Override
			public void onLevelUp(final SkillIcon skillIcon) {
				skillIcon.getSkillState().setLevel(skillIcon.getSkillState().getLevel() + 1);
				character.getStats().setSkillPoints(character.getStats().getSkillPoints() - 1);
				gameUi.getSkillbook().setSkillPoints(character.getStats().getSkillPoints());
				
				gameUi.getSkillbook().enableForLevelUp();
				if(character.getStats().getSkillPoints() == 0) {
					gameUi.getSkillbook().disableForLevelUp();
				}
			}
		});
		
		/*characterController.addListener(new TouchListener() {
			@Override
			public void touched() {
				Foe foe = foeManager.makeFoe(app.assets.tileHouse, 7, 6);
				foe.setSize(32, 32);
				
				Vector2 pos = foeManager.getRandomSpawnPoint();
				foe.setPosition(pos.x, pos.y);
				
				foeGroup.addActor(foe);
			}
		});*/
		
		
	}
	
	public LD31 getApp() {
		return app;
	}
	
	public void begin() {
		WorldGenerator.generateWorld(app, worldMap);
		addActor(character);
		character.begin();
		
		final CharacterStats stats = new CharacterStats();
		/*stats.getSkills()[0] = new DebugSkillTree(app).create();
=======
		stats.getSkills()[0] = new TranslocationsSkillTree(app).create();
>>>>>>> origin/master
		stats.getSkills()[1] = new DebugSkillTree(app).create();
		stats.getSkills()[2] = new DebugSkillTree(app).create();*/
		
		stats.getSkills()[0] = new WarriorSkillTree(app).create();
		stats.getSkills()[1] = new WarriorSkillTree(app).create();
		stats.getSkills()[2] = new DebugSkillTree(app).create();

		character.setStats(stats);
		character.setPosition(getWidth() / 2f, getHeight() / 2f);

		addActor(foeGroup);
		addActor(characterController);
		addActor(projectiles);
		
		// Sitas turi buti paskutinis pridetas aktorius
		addActor(gameUi);
		
		gameUi.begin();
		gameUi.getSkillbook().setSkillTree(character.getStats().getSkills());
		gameUi.getSkillbook().setSkillPoints(character.getStats().getSkillPoints());
		
		if(character.getStats().getSkillPoints() > 0) {
			gameUi.getSkillbook().enableForLevelUp();
		}
	}
	
	@Override
	public void act(final float delta) {
		if(Gdx.input.isKeyJustPressed(Keys.F)) {
			//Charge skill = new Charge();
			Charge skill = new Charge();
			skill.activate(character, this, 1);
		}
			
		if(paused) {
			gameUi.act(delta);
		}
		else {
			super.act(delta);
			
			foeManager.update(delta);
			if(foeManager.canSpawn()) {
				Foe foe = foeManager.makeFoe(app.assets.tileHouse, 7, 6);
				foe.setSize(32, 32);
				
				Vector2 pos = foeManager.getRandomSpawnPoint();
				foe.setPosition(pos.x, pos.y);
				
				foeGroup.addActor(foe);
			}
			
			
			for(int i = 0; i < PlayerCharacter.MovementDirection.list.length; i += 1) {
				final PlayerCharacter.MovementDirection direction = PlayerCharacter.MovementDirection.list[i];
				if(Gdx.input.isKeyJustPressed(direction.key)) {
					character.movement(direction, true);
				}
				else if(!Gdx.input.isKeyPressed(direction.key) && character.isMoving(direction)) {
					character.movement(direction, false);
				}
			}
			//Update projectiles
			for(int i=0; i<projectiles.getChildren().size; i++) {
				Projectile p = (Projectile) projectiles.getChildren().get(i);
				p.update(delta);
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
		foeGroup.setSize(width, height);
		
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
	
	public PlayerCharacter getCharacter() {
		return character;
	}
	
	public FoeManager getFoeManager() {
		return foeManager;
	}

	public GameUi getGameUi() {
		return gameUi;
	}

	public ProjectileFactory getProjectileFactory() {
		return projectileFactory;
	}
	
}
