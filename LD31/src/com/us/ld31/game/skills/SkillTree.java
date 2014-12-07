package com.us.ld31.game.skills;

import com.badlogic.gdx.utils.Array;

public class SkillTree {

	public static interface Source {
		public SkillTree create();
	}
	
	public final String name;
	public final Source source;
	public final Array<SkillState> states;
	
	public SkillTree(final String name,
					 final Source source, 
					 final Array<SkillState> states) {
		
		this.name = name;
		this.source = source;
		this.states = states;
	}
	
}
