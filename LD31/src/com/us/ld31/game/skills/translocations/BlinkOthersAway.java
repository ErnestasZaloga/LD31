package com.us.ld31.game.skills.translocations;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.pawn.Foe;
import com.us.ld31.game.pawn.Pawn;
import com.us.ld31.game.pawn.PlayerCharacter;
import com.us.ld31.game.skills.Skill;
import com.us.ld31.utils.tiles.Tile;

public class BlinkOthersAway implements Skill {

	private float cooldown = 45;
	private int rangeInTiles = 6;
	private float range;
	
	@Override
	public float activate(Pawn user, GameWorld gameWorld, int skillLevel) {
		range = rangeInTiles * gameWorld.getWorldMap().getTileSize();
		PlayerCharacter caster = (PlayerCharacter) user;
		for(Foe f : caster.getFoesInRange(rangeInTiles)) {
			float mouseX = f.getX();
			float mouseY = f.getY();
				Array<Tile> tiles = f.getTilesInRange(rangeInTiles);
				for(Tile t : tiles) {
					if(!t.isWalkable()) {
						tiles.removeValue(t, false);
					}
				}
				//Remove all the tiles which would bring target closer to the caster
				Array<Tile> availableTargetTiles = new Array<Tile>();
				if(user.getX() <= f.getX()) {  //If caster is to the left to the target
					for(Tile t : tiles) {
						if(t.getX() >= f.getX()) {
							if(user.getY() <= f.getY()) {
								if(t.getY() >= f.getY()) {
									availableTargetTiles.add(t);
								}
							} else {
								if(t.getY() < f.getY()) {
									availableTargetTiles.add(t);
								}
							}
						}
					}
				} else {  //If caster is to the right...
					for(Tile t : tiles) {
						if(t.getX() < f.getX()) {
							if(user.getY() <= f.getY()) {
								if(t.getY() >= f.getY()) {
									availableTargetTiles.add(t);
								}
							} else {
								if(t.getY() < f.getY()) {
									availableTargetTiles.add(t);
								}
							}
						}
					}
				}
				int t = MathUtils.random(availableTargetTiles.size-1);
				f.translocate(availableTargetTiles.get(t).getX(), availableTargetTiles.get(t).getY());
			}			
		return cooldown;
	}

}
