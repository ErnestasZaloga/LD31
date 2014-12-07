package com.us.ld31.game.skills.warrior;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.skills.Skill;

public class TasteOfBlood implements Skill{
	
	private boolean skillSuccessful = true;
	
	private float cooldown = 60f;
	
	@Override
	public float activate(Actor user, GameWorld gameWorld, int skillLevel) {
		
		
		
		return skillSuccessful ? cooldown : 0;
	}
	
}
