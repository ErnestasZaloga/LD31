package com.us.ld31.game;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.us.ld31.utils.SpriteActor;
import com.us.ld31.utils.steps.Steps;
import com.us.ld31.utils.steps.scene.ActorSteps;

public class Character extends SpriteActor {

	private final GameWorld gameWorld;
	
	private float tileSize;
	
	private Action moveUpAction;
	private Action moveDownAction;
	private Action moveLeftAction;
	private Action moveRightAction;
	
	public Character(final GameWorld gameWorld) {
		this.gameWorld = gameWorld;
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
	
	public void movementUp(final boolean enable) {
		if(!enable) {
			removeAction(moveUpAction);
		}
		else {
			moveUpAction = Steps.action(
					Steps.repeat(
							ActorSteps.moveBy(0f, tileSize, 1f)));
			addAction(moveUpAction);
		}
	}
	
	public void movementDown(final boolean enable) {
	}
	
	public void movementLeft(final boolean enable) {
		
	}
	
	public void movementRight(final boolean enable) {
		
	}

	@Override
	public void act(final float delta) {
		super.act(delta);
	}
	
}
