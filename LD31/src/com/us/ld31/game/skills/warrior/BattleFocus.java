package com.us.ld31.game.skills.warrior;

import com.us.ld31.game.GameWorld;
import com.us.ld31.game.pawn.Pawn;
import com.us.ld31.game.skills.Skill;

public class BattleFocus implements Skill{
	
	private boolean skillSuccessful = true;
	
	private float cooldown = 60f;
	
	@Override
	public float activate(Pawn user, GameWorld gameWorld, int skillLevel) {
		
		
		
		return skillSuccessful ? cooldown : 0;
	}
	
}
