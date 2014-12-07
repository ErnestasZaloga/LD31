package com.us.ld31.game;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.us.ld31.game.foestuff.Foe;
import com.us.ld31.game.skills.Skill;
import com.us.ld31.game.skills.SkillState;
import com.us.ld31.game.skills.SkillTree;
import com.us.ld31.utils.Log;
import com.us.ld31.utils.SpriteActor;
import com.us.ld31.utils.steps.Steps;
import com.us.ld31.utils.steps.scene.ActorSteps;
import com.us.ld31.utils.tiles.Tile;
import com.us.ld31.utils.tiles.WorldMap;

public class PlayerCharacter extends SpriteActor {

	public static enum SkillSlot {
		Primary,
		Secondary;
	}
	
	public static enum MovementDirection {
		Up(0, 0, 4, Keys.W),
		Down(1, 0, -4, Keys.S),
		Left(2, -4, 0, Keys.A),
		Right(3, 4, 0, Keys.D);
		
		public static final MovementDirection[] list = new MovementDirection[] {
			Up,
			Down,
			Left,
			Right
		};
		
		public final int index;
		public final float hMul;
		public final float vMul;
		public final int key;
		
		private MovementDirection(final int index, 
								  final float hMul, 
								  final float vMul,
								  final int key) {
			
			this.index = index;
			this.hMul = hMul;
			this.vMul = vMul;
			this.key = key;
		}
	}

	private float tileSize;
	private int mapTilesX;
	private int mapTilesY;
	
	private final GameWorld gameWorld;
	private final WorldMap worldMap;
	private final Action[] movementActions = new Action[4];
	private final IntArray tiles = new IntArray();
	
	private CharacterStats stats;
	private Skill primarySkill;
	private SkillState secondarySkill;
	
	public float portalX = -1;
	public float portalY = -1;
	
	public PlayerCharacter(final GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		this.worldMap = gameWorld.getWorldMap();
	}
	
	public void setPrimarySkill(final Skill primarySkill) {
		this.primarySkill = primarySkill;
	}
	
	public Skill getPrimarySkill() {
		return primarySkill;
	}
	
	public void setSecondarySkill(final SkillState secondarySkill) {
		this.secondarySkill = secondarySkill;
	}
	
	public SkillState getSecondarySkill() {
		return secondarySkill;
	}
	
	public void setStats(final CharacterStats stats) {
		this.stats = stats;
	}
	
	public CharacterStats getStats() {
		return stats;
	}
	
	public void performSkill(final SkillSlot slot) {
		if(slot == SkillSlot.Secondary && secondarySkill != null) {
			if(secondarySkill.getCooldownLeft() == 0) {
				final float cooldown = secondarySkill.getSkillInfo().skill.activate(this, gameWorld, secondarySkill.getLevel());
				secondarySkill.setCooldown(cooldown);
				secondarySkill.setCooldownLeft(cooldown);
			}
			else {
				Log.trace(this, "Still in cooldown");
			}
		}
		else if(slot == SkillSlot.Primary && primarySkill != null) {
			primarySkill.activate(this, gameWorld, 1);
		}
	}
	
	public void begin() {
		tileSize = worldMap.getTileSize();
		mapTilesX = worldMap.getTilesX();
		mapTilesY = worldMap.getTilesY();
	}
	
	public boolean isMoving(final MovementDirection direction) {
		return movementActions[direction.index] != null;
	}
	
	public void movement(final MovementDirection direction, 
						 final boolean enable) {
		
		if(!enable) {
			removeAction(movementActions[direction.index]);
			movementActions[direction.index] = null;
		}
		else {
			movementActions[direction.index] = Steps.action(
					Steps.repeat(
							ActorSteps.moveBy(worldMap.getTileSize() * direction.hMul, worldMap.getTileSize() * direction.vMul, 0.5f)));
			addAction(movementActions[direction.index]);
		}
	}
	
	@Override
	public void act(final float delta) {
		for(int i = 0; i < stats.getSkills().length; i += 1) {
			final SkillTree skillTree = stats.getSkills()[i];
			skillTree.updateCooldowns(delta);
		}
		
		final float x = getX();
		final float y = getY();
		
		super.act(delta);
		
		if(getX() < 0) {
			setX(0);
		}
		else if(getX() > getParent().getWidth() - getWidth()) {
			setX(getParent().getWidth() - getWidth());
		}
		
		if(getY() < 0) {
			setY(0);
		}
		else if(getY() > getParent().getHeight() - getHeight()) {
			setY(getParent().getHeight() - getHeight());
		}

		final float newX = getX();
		final float newY = getY();

		setPosition(newX, y);
		collectTileBounds();
		final boolean xAffectsCollision = isCollision();
		
		setPosition(x, newY);
		collectTileBounds();
		final boolean yAffectsCollision = isCollision();
		
		if(!xAffectsCollision && !yAffectsCollision) {
			setPosition(newX, newY);
			collectTileBounds();
			if(isCollision()) {
				setPosition(x, y);
			}
		}
		else {
			setPosition(x, y);
			
			if(!xAffectsCollision) {
				setX(newX);
			}
			else if(!yAffectsCollision) {
				setY(newY);
			}
		}
	}
	
	private void collectTileBounds() {
		tiles.clear();

		final int bottomLeft = getLocalTile(0f, 0f);
		final int bottomRight = getLocalTile(1f, 0f);
		final int topLeft = getTile(getX(), getY() + worldMap.getTileSize());
		final int topRight = getTile(getRight(), getY() + worldMap.getTileSize());
		
		final int xStart = getTileX(bottomLeft);
		final int xEnd = getTileX(topRight);
		final int yStart = getTileY(bottomLeft);
		final int yEnd = getTileY(topRight);
		
		for(int yi = yStart, yn = yEnd; yi <= yn; yi += 1) {
			for(int xi = xStart, xn = xEnd; xi <= xn; xi += 1) {
				tiles.add(mergeCoords(xi, yi));
			}
		}
	}
	
	private int getLocalTile(final float xScale, final float yScale) {
		return getTile(getX() + getWidth() * xScale, getY() + getHeight() * yScale);
	}
	
	private int getTileX(final int tile) {
		return tile % mapTilesX;
	}
	
	private int getTileY(final int tile) {
		return tile / mapTilesX;
	}
	
	private int mergeCoords(final int tileX, final int tileY) {
		return tileY * mapTilesX + tileX;
	}
	
	private int getTile(final float x, final float y) {
		final int tileX = (int)(x / tileSize);
		final int tileY = (int)(y / tileSize);
		
		return mergeCoords(tileX, tileY);
	}
	
	private boolean isCollision() {
		for(int i = 0, n = tiles.size; i < n; i += 1) {
			final int tile = tiles.get(i);
			if(!worldMap.isWalkable(getTileX(tile), getTileY(tile))) {
				return true;
			}
		}
		return false;
	}
	
	public Array<Tile> getTilesInRange(int range) {
		Array<Tile> result = new Array<Tile>();
		for(int i=0; i<worldMap.getChildren().size; i++) {
			Tile t = (Tile) worldMap.getChildren().get(i);
			float dx = getX()-t.getX();
			float dy = getY()-t.getY();
			if(Math.sqrt(dx*dx+dy*dy) <= range*worldMap.getTileSize()) {
				result.add(t);
			}
		}
		return result;
	}
	
	public Array<Foe> getFoesInRange(int range) {
		Array<Foe> result = new Array<Foe>();
		for(Foe f : gameWorld.getFoeManager().getAllFoes()) {
			float dx = getX()-f.getX();
			float dy = getY()-f.getY();
			if(Math.sqrt(dx*dx+dy*dy) <= range*worldMap.getTileSize()) {
				result.add(f);
			}
		}
		return result;
	}
	
}
