package com.us.ld31.game.skills.translocations;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.skills.Skill;

/**
 * Character only spell.
 * 
 * @author Ernyz
 */
public class Portal implements Skill {

	private float cooldown = 30;
	
	@Override
	public float activate(Actor user, GameWorld gameWorld, int skillLevel) {
		//No portal exists, create new one
		if(gameWorld.getCharacter().portalX == -1 && gameWorld.getCharacter().portalY == -1) {
			gameWorld.getCharacter().portalX = gameWorld.getCharacter().getX();
			gameWorld.getCharacter().portalY = gameWorld.getCharacter().getY();
		} else {
			gameWorld.getCharacter().setX(gameWorld.getCharacter().portalX);
			gameWorld.getCharacter().setY(gameWorld.getCharacter().portalY);
			gameWorld.getCharacter().portalX = -1;
			gameWorld.getCharacter().portalY = -1;
		}
		
		return cooldown;
	}

}
