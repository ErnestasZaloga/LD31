package com.us.ld31.game.skills;

import com.badlogic.gdx.utils.Array;

public class SkillTree {

	public static interface Source {
		public SkillTree create();
	}
	
	public final Source source;
	public final Array<SkillState> states;
	
	public SkillTree(final Source source, 
					 final Array<SkillState> states) {
		
		this.source = source;
		this.states = states;
	}
	
}
