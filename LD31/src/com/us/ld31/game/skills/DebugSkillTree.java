package com.us.ld31.game.skills;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.us.ld31.LD31;
import com.us.ld31.game.GameWorld;
import com.us.ld31.utils.Log;

public class DebugSkillTree implements SkillTree.Source {

	private final LD31 app;
	
	public DebugSkillTree(final LD31 app) {
		this.app = app;
	}
	
	@Override
	public SkillTree create() {
		final Array<SkillState> states = new Array<SkillState>();
		final Skill debugSkill = new Skill() {
			@Override
			public float activate(final Actor owner, 
								  final GameWorld gameWorld, 
								  final int skillLevel) {
				
				Log.trace(this, "I am casting a skill of level:", skillLevel);
				return 1f;
			}
		};
		
		final SkillInfo blink = new SkillInfo.Builder()
			.name("Blink")
			.descrption("Something something blablabla")
			.icon(app.assets.uiMissing)
			.skill(debugSkill)
		.build();
		
		final SkillState skillRow1Column1 = new SkillState(blink);
		final SkillState skillRow2Column1 = new SkillState(blink);
		final SkillState skillRow2Column2 = new SkillState(blink);
		final SkillState skillRow3Column1 = new SkillState(blink);
		final SkillState skillRow3Column2 = new SkillState(blink);
		final SkillState skillRow3Column3 = new SkillState(blink);

		skillRow1Column1.addChild(skillRow2Column1);
		skillRow1Column1.addChild(skillRow2Column2);
		
		skillRow2Column1.addChild(skillRow3Column1);
		
		skillRow2Column2.addChild(skillRow3Column2);
		skillRow2Column2.addChild(skillRow3Column3);
		
		return new SkillTree(this, states);
	}

}
