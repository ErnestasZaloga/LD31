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
	
	public void updateCooldowns(final float delta) {
		updateCooldowns(delta, states);
	}
	
	private void updateCooldowns(final float delta, 
								 final Array<SkillState> states) {
		
		for(int i = 0; i < states.size; i += 1) {
			states.get(i).updateCooldown(delta);
			updateCooldowns(delta, states.get(i).getChildren());
		}
	}
	
}
