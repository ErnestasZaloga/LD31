package com.us.ld31.game.skills.bowmastery;

import com.us.ld31.game.GameWorld;
import com.us.ld31.game.pawn.Pawn;
import com.us.ld31.game.skills.Skill;

public class MultiShot implements Skill {

	private float cooldown = 5;
	
	@Override
	public float activate(Pawn user, GameWorld gameWorld, int skillLevel) {
		
		
		return cooldown;
	}

}
