package com.us.ld31.game.skills.warrior;

import com.badlogic.gdx.utils.Array;
import com.us.ld31.LD31;
import com.us.ld31.game.skills.SkillInfo;
import com.us.ld31.game.skills.SkillState;
import com.us.ld31.game.skills.SkillTree;

public class WarriorSkillTree implements SkillTree.Source{
	
	private final LD31 app;
	
	public WarriorSkillTree(final LD31 app) {
		this.app = app;
	}

	@Override
	public SkillTree create() {
		final Array<SkillState> states = new Array<SkillState>();
		
		final PowerHit powerHit = new PowerHit();
		
		final SkillInfo.Builder infoBuilder = new SkillInfo.Builder();
		
		final SkillInfo powerHitInfo = infoBuilder
			.name("Power Hit")
			.descrption("Melee atack that deals "
					+ "\n(150 / 175 / 225 / 275) % "
					+ "\nof your damage"
					+ "\n with 15% chance to stun "
					+ "\nyour opponent for"
					+ "\n(0 / 0 / 1 / 2) seconds.")
			.icon(app.assets.tileRoad)
			.skill(powerHit)
			.levelCap(4)
		.build();
		
		final Charge charge = new Charge();
		
		final SkillInfo chargeInfo = infoBuilder
				.name("Charge")
				.descrption("Charge in your mouse direction. If you collide with an enemy, it takes"
						+ "(125 / 135 / 150) % of your damage. If not - cooldown is reduced by 50%"
						+ "further ranks reduce cooldown to (16 / 12) sec.")
				.icon(app.assets.tileRoad)
				.skill(charge)
				.levelCap(3)
			.build();
		
		final HeadSlam headSlam = new HeadSlam();
		
		final SkillInfo headSlamInfo = infoBuilder
				.name("Head Slam")
				.descrption("Slams your opponent's head and stuns him for (2 / 3 / 5) seconds.")
				.icon(app.assets.tileRoad)
				.skill(headSlam)
				.levelCap(3)
			.build();
		
		final BattleFocus focus = new BattleFocus();
		
		final SkillInfo swordFocusInfo = infoBuilder
				.name("Battle Focus")
				.descrption("Warrior gains battle focus which deflects (3 / 6 / 10) incomming attacks "
						+ "and increases attack rating by (10 / 20 / 30) % "
						+ "while not all attacks are deflected.")
				.icon(app.assets.tileRoad)
				.skill(focus)
				.levelCap(3)
			.build();

		final SkillState skillRow1Column1 = new SkillState(powerHitInfo);
		final SkillState skillRow2Column3 = new SkillState(chargeInfo);
		final SkillState skillRow2Column1 = new SkillState(headSlamInfo);
		final SkillState skillRow2Column2 = new SkillState(swordFocusInfo);
		final SkillState skillRow3Column1 = new SkillState(powerHitInfo);
		final SkillState skillRow3Column2 = new SkillState(powerHitInfo);
		final SkillState skillRow3Column3 = new SkillState(powerHitInfo);

		skillRow1Column1.addChild(skillRow2Column1);
		skillRow1Column1.addChild(skillRow2Column2);
		skillRow1Column1.addChild(skillRow2Column3);
		
		
		skillRow2Column1.addChild(skillRow3Column1);
		
		skillRow2Column2.addChild(skillRow3Column2);
		skillRow2Column2.addChild(skillRow3Column3);
		
		states.add(skillRow1Column1);
		//states.add(skillRow1Column2);
		
		return new SkillTree("Warrior Skill Tree", this, states);
	}

}
