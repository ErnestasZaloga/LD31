package com.us.ld31.game.skills;

import com.badlogic.gdx.utils.Array;
import com.us.ld31.LD31;
import com.us.ld31.game.skills.translocations.Blink;
import com.us.ld31.game.skills.translocations.BlinkOther;
import com.us.ld31.game.skills.translocations.BlinkOtherAway;
import com.us.ld31.game.skills.translocations.BlinkOtherCloser;
import com.us.ld31.game.skills.translocations.BlinkOthersAway;
import com.us.ld31.game.skills.translocations.BlinkOthersCloser;
import com.us.ld31.game.skills.translocations.ControlledBlink;
import com.us.ld31.game.skills.translocations.Portal;


public class TranslocationsSkillTree implements SkillTree.Source {
	
	private final LD31 app;
	
	public TranslocationsSkillTree(final LD31 app) {
		this.app = app;
	}
	
	@Override
	public SkillTree create() {
		final Array<SkillState> states = new Array<SkillState>();
		final SkillInfo.Builder builder = new SkillInfo.Builder();
		
		final Skill blink = new Blink();
		final SkillInfo blinkInfo = builder
			.name("Blink")
			.descrption("Blinks to a random location short distance away. Distance depends on the level of this skill.")
			.icon(app.assets.tileRoad)
			.skill(blink)
			.levelCap(2)
		.build();
		
		final Skill controlledBlink = new ControlledBlink();
		final SkillInfo controlledBlinkInfo = builder
			.name("Controlled Blink")
			.descrption("Blinks towards the mouse cursor. Distance depends on the level of this skill.")
			.icon(app.assets.tileRoad)
			.skill(controlledBlink)
			.levelCap(2)
		.build();
		
		final Skill blinkOther = new BlinkOther();
		final SkillInfo blinkOtherInfo = builder
			.name("Blink Other")
			.descrption("Randomly teleports target enemy. Distance depends on the level of this skill.")
			.icon(app.assets.tileRoad)
			.skill(blinkOther)
			.levelCap(2)
		.build();
		
		final Skill portal = new Portal();
		final SkillInfo portalInfo = builder
			.name("Portal")
			.descrption("Creates a portal near your current location. Reactivating this skill teleports you back to the portal and closes it.")
			.icon(app.assets.tileRoad)
			.skill(portal)
			.levelCap(1)
		.build();
		
		final Skill blinkOtherAway = new BlinkOtherAway();
		final SkillInfo blinkOtherAwayInfo = builder
			.name("Blink Other Away")
			.descrption("Teleports your target away from you.")
			.icon(app.assets.tileRoad)
			.skill(blinkOtherAway)
			.levelCap(1)
		.build();
		
		final Skill blinkOtherCloser = new BlinkOtherCloser();
		final SkillInfo blinkOtherCloserInfo = builder
			.name("Blink Other Closer")
			.descrption("Teleports your target closer to you.")
			.icon(app.assets.tileRoad)
			.skill(blinkOtherCloser)
			.levelCap(1)
		.build();
		
		final Skill blinkOthersAway = new BlinkOthersAway();
		final SkillInfo blinkOthersAwayInfo = builder
			.name("Blink Others Away")
			.descrption("Teleports surrounding enemies further away from you.")
			.icon(app.assets.tileRoad)
			.skill(blinkOthersAway)
			.levelCap(1)
		.build();
		
		final Skill blinkOthersCloser = new BlinkOthersCloser();
		final SkillInfo blinkOthersCloserInfo = builder
			.name("Blink Others Closer")
			.descrption("Teleports surrounding enemies closer to you.")
			.icon(app.assets.tileRoad)
			.skill(blinkOthersCloser)
			.levelCap(1)
		.build();
		
		final SkillState skillRow1Column1 = new SkillState(blinkInfo);
		
		final SkillState skillRow2Column1 = new SkillState(controlledBlinkInfo);
		skillRow1Column1.addChild(skillRow2Column1);
		final SkillState skillRow2Column2 = new SkillState(blinkOtherInfo);
		skillRow1Column1.addChild(skillRow2Column2);
		
		final SkillState skillRow3Column1 = new SkillState(portalInfo);
		skillRow2Column1.addChild(skillRow3Column1);
		final SkillState skillRow3Column2 = new SkillState(blinkOtherAwayInfo);
		skillRow2Column2.addChild(skillRow3Column2);
		final SkillState skillRow3Column3 = new SkillState(blinkOtherCloserInfo);
		skillRow2Column2.addChild(skillRow3Column3);
		
		final SkillState skillRow4Column1 = new SkillState(blinkOthersAwayInfo);
		skillRow3Column2.addChild(skillRow4Column1);
		final SkillState skillRow4Column2 = new SkillState(blinkOthersCloserInfo);
		skillRow3Column3.addChild(skillRow4Column2);
		
		states.add(skillRow1Column1);
		
		return new SkillTree("Translocations Skill Tree", this, states);
	}

}
