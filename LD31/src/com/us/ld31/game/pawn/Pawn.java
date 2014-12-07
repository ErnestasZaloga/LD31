package com.us.ld31.game.pawn;

import com.us.ld31.game.stats.Stats;
import com.us.ld31.utils.SpriteActor;

abstract public class Pawn extends SpriteActor {

	private Stats stats;
	
	public void setStats(final Stats stats) {
		this.stats = stats;
	}
	
	public Stats getStats() {
		return stats;
	}
	
}
