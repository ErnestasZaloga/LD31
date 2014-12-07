package com.us.ld31.game.skills.translocations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.skills.Skill;

public class BlinkOther implements Skill {

	private float cooldown = 35;
	
	@Override
	public float activate(Actor user, GameWorld gameWorld, int skillLevel) {
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight()-Gdx.input.getY();
		
		Blink b = new Blink();
		Actor targetActor = gameWorld.getFoeManager().getFoeByCoords(mouseX, mouseY);
		if(targetActor != null) {
			b.activate(targetActor, gameWorld, skillLevel);
		} else {
			//TODO: Warning msg and sound
		}
		
		return cooldown;
	}

}
