package com.us.ld31.game.skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.game.GameWorld;

public class MeleeAttack implements Skill {

	private float cooldown = 1f;
	
	@Override
	public float activate(Actor user, GameWorld gameWorld, int skillLevel) {
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight()-Gdx.input.getY();
		float dir = (new Vector2(mouseX-gameWorld.getCharacter().getX(), mouseY-gameWorld.getCharacter().getY())).angle();
		gameWorld.getProjectileFactory().createMeleeAttack(gameWorld.getCharacter().getX()+gameWorld.getCharacter().getWidth()/2, 
				gameWorld.getCharacter().getY()+gameWorld.getCharacter().getHeight()/2,
				dir);
		
		return cooldown;
	}

}
