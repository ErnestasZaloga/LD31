package com.us.ld31.game.skills;

import com.us.ld31.game.GameWorld;
import com.us.ld31.game.pawn.Pawn;

public interface Skill {
	public float activate(final Pawn user, final GameWorld gameWorld, final int skillLevel);
}
