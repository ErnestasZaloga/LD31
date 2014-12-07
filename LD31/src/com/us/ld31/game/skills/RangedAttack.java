package com.us.ld31.game.skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.pawn.Pawn;

public class RangedAttack implements Skill {

	private float cooldown = 1f;
	
	@Override
	public float activate(Pawn user, GameWorld gameWorld, int skillLevel) {
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight()-Gdx.input.getY();
		float dir = (new Vector2(mouseX-gameWorld.getCharacter().getX(), mouseY-gameWorld.getCharacter().getY())).angle();
		gameWorld.getProjectileFactory().createRangedAttack(gameWorld.getCharacter().getX(), gameWorld.getCharacter().getY(), dir);
		
		return cooldown;
	}

}
