package com.us.ld31.game.stats;

import com.us.ld31.game.pawn.Pawn;
import com.us.ld31.game.pawn.PlayerCharacter;
import com.us.ld31.game.ui.GameUi;

public class StatsResult {
	
	private boolean wasCritical;
	private boolean wasDodged;
	private int damage;
	
	public StatsResult(final boolean wasCritical, 
						final boolean wasDodged, 
						final int damage) {
		
		this.wasCritical = wasCritical;
		this.wasDodged = wasDodged;
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public boolean wasCritical() {
		return wasCritical;
	}
	
	public boolean wasDodged() {
		return wasDodged;
	}

	public void uploadHealingResult(final GameUi gameUi, 
								    final Pawn source) {
		
		gameUi.getMessages().showHealing(source, damage, wasCritical);
	}
	
	public void uploadDamageResult(final GameUi gameUi, 
						   	 	   final Pawn source, 
						   	 	   final Pawn target) {
		
		target.getStats().setHitPoints(target.getStats().getHitPoints() - damage);
		
		if(target instanceof PlayerCharacter) {
			if(wasDodged) {
				String dodgeName = "Dodge";
				final Stats targetStats = target.getStats();
				final int str = targetStats.getStrength();
				final int dex = targetStats.getDextirity();
				final int intel = targetStats.getIntelligence();
				
				if(str > dex && str > intel) {
					dodgeName = "Parry";
				}
				else if(intel > dex && intel > str) {
					dodgeName = "Evaded";
				}
				
				gameUi.getMessages().showPlayerDodged(dodgeName, target);
			}
			else {
				gameUi.getMessages().showDamageToPlayer(target, damage, wasCritical);
			}
			
			gameUi.getHealthBar().setPercent(target.getStats().getHitPoints() / (float)target.getStats().getMaxHitPoints(), true);
		}
		else {
			if(wasDodged) {
				gameUi.getMessages().showEnemyDodged(target);
			}
			else {
				gameUi.getMessages().showDamageToEnemy(target, damage, wasCritical);
			}
			
			if(target.getStats().isDead()) {
				final CharacterStats stats = (CharacterStats) source.getStats();
				if(stats.addExperience(((EnemyStats) target.getStats()).getExperience())) {
					gameUi.getTopBar().getLvlWidget().setValue(stats.getLevel());
					gameUi.getTopBar().getStrWidget().setEditable(true);
					gameUi.getTopBar().getDexWidget().setEditable(true);
					gameUi.getTopBar().getIntWidget().setEditable(true);
					gameUi.getExpBar().setPercent(stats.getExperience() / (float)(stats.getExperienceLeft() + stats.getExperience()), false);
				}
				else {
					gameUi.getExpBar().setPercent(stats.getExperience() / (float)(stats.getExperienceLeft() + stats.getExperience()), true);
				}
				
			}
		}
	}
}