package com.us.ld31.game;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface Skill {
	public float activate(final Actor user, 
						  final GameWorld gameWorld);
}
