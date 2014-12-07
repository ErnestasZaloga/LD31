package com.us.ld31.game.skills.translocations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.pawn.Foe;
import com.us.ld31.game.pawn.Pawn;
import com.us.ld31.game.skills.Skill;
import com.us.ld31.utils.tiles.Tile;

public class BlinkOtherCloser implements Skill {
	
	private float cooldown = 20;
	private int rangeInTiles = 4;
	
	@Override
	public float activate(Pawn user, GameWorld gameWorld, int skillLevel) {
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight()-Gdx.input.getY();
		Foe targetActor = gameWorld.getFoeManager().getFoeByCoords(mouseX, mouseY);
		if(targetActor != null) {
			Array<Tile> tiles = targetActor.getTilesInRange(rangeInTiles);
			for(Tile t : tiles) {
				if(!t.isWalkable()) {
					tiles.removeValue(t, false);
				}
			}
			//Remove all the tiles which would bring target further away from the caster
			Array<Tile> availableTargetTiles = new Array<Tile>();
			if(user.getX() <= targetActor.getX()) {  //If caster is to the left to the target
				for(Tile t : tiles) {
					if(t.getX() <= targetActor.getX()) {
						if(user.getY() <= targetActor.getY()) {
							if(t.getY() <= targetActor.getY()) {
								availableTargetTiles.add(t);
							}
						} else {
							if(t.getY() > targetActor.getY()) {
								availableTargetTiles.add(t);
							}
						}
					}
				}
			} else {  //If caster is to the right...
				for(Tile t : tiles) {
					if(t.getX() > targetActor.getX()) {
						if(user.getY() <= targetActor.getY()) {
							if(t.getY() <= targetActor.getY()) {
								availableTargetTiles.add(t);
							}
						} else {
							if(t.getY() > targetActor.getY()) {
								availableTargetTiles.add(t);
							}
						}
					}
				}
			}
			int t = MathUtils.random(availableTargetTiles.size-1);
			System.out.println(t);
			System.out.println(availableTargetTiles.get(t).getX() + " " + availableTargetTiles.get(t).getY());
			targetActor.translocate(availableTargetTiles.get(t).getX(), availableTargetTiles.get(t).getY());
		} else {
			gameWorld.getGameUi().getMessages().showWarning("Invalid target!");
		}
		
		return cooldown;
	}
}
