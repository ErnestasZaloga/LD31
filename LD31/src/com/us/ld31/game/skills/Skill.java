package com.us.ld31.game.skills;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.game.GameWorld;

public interface Skill {
	public float activate(final Actor user, final GameWorld gameWorld, final int skillLevel);
}
