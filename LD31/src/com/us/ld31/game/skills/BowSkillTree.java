package com.us.ld31.game.skills;

import com.badlogic.gdx.utils.Array;
import com.us.ld31.LD31;
import com.us.ld31.game.skills.bowmastery.ArrowShot;

public class BowSkillTree implements SkillTree.Source {

	private final LD31 app;
	
	public BowSkillTree(final LD31 app) {
		this.app = app;
	}
	
	@Override
	public SkillTree create() {
		final Array<SkillState> states = new Array<SkillState>();
		final SkillInfo.Builder builder = new SkillInfo.Builder();
		
		final Skill arrowShot = new ArrowShot();
		final SkillInfo arrowShotInfo = builder
			.name("Arrow shot")
			.descrption("Shoots an arrow.")
			.icon(app.assets.tileRoad)
			.skill(arrowShot)
			.levelCap(1)
		.build();
		
		final SkillState skillRow1Column1 = new SkillState(arrowShotInfo);
		
		states.add(skillRow1Column1);
		
		return new SkillTree("Bow Skill Tree", this, states);
	}

}
