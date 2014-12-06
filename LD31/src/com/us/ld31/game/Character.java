package com.us.ld31.game;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.us.ld31.utils.SpriteActor;
import com.us.ld31.utils.steps.Steps;
import com.us.ld31.utils.steps.scene.ActorSteps;

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
	
	private float tileSize;
	
	private final Action[] movementActions = new Action[4];
	
	public Character() {
	}
	
	public void setTileSize(final float tileSize) {
		this.tileSize = tileSize;
	}
	
	public float getTileSize() {
		return tileSize;
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
							ActorSteps.moveBy(tileSize * direction.hMul, tileSize * direction.vMul, 1f)));
			addAction(movementActions[direction.index]);
		}
	}
	
	@Override
	public void act(final float delta) {
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
	}
	
}
