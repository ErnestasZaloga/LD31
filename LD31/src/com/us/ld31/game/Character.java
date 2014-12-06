package com.us.ld31.game;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.us.ld31.utils.SpriteActor;
import com.us.ld31.utils.steps.Steps;
import com.us.ld31.utils.steps.scene.ActorSteps;
import com.us.ld31.utils.tiles.WorldMap;

public class Character extends SpriteActor {

	public static enum MovementDirection {
		Up(0, 0, 1, Keys.W),
		Down(1, 0, -1, Keys.S),
		Left(2, -1, 0, Keys.A),
		Right(3, 1, 0, Keys.D);
		
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

	private final WorldMap worldMap;
	private final Action[] movementActions = new Action[4];
	
	public Character(final WorldMap worldMap) {
		this.worldMap = worldMap;
	}
	
	public void begin() {
		
	}
	
	public void attack() {
		
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

		if(!checkWalkable(0.5f, 0f) ||
		   !checkWalkable(0.5f, 1f) ||
		   !checkWalkable(0f, 0.5f) ||
		   !checkWalkable(1f, 0.5f) ||
		   !checkWalkable(0f, 0f) ||
		   !checkWalkable(0f, 1f) ||
		   !checkWalkable(1f, 0f) ||
		   !checkWalkable(1f, 1f)) {
			
			setPosition(x, y);
		}
	}
	
	private boolean checkWalkable(final float xScale, final float yScale) {
		final float tileSize = worldMap.getTileSize();
		
		final int tileX = (int)((getX() + getWidth() * xScale) / tileSize);
		final int tileY = (int)((getY() + getHeight() * yScale) / tileSize);
		
		return worldMap.isWalkable(tileX, tileY);
	}
	
}
