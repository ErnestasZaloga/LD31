package com.us.ld31.game.skills.translocations;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.us.ld31.game.Character;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.foestuff.Foe;
import com.us.ld31.game.skills.Skill;
import com.us.ld31.utils.tiles.Tile;

public class Blink implements Skill {
	
	private float cooldown = 25f;
	
	private int rangeInTiles = 7;

	@Override
	public float activate(Actor user, GameWorld gameWorld, int skillLevel) {
		Array<Tile> tiles;
		if(user instanceof Character) {
			Character u = (Character) user;
			tiles = u.getTilesInRange(rangeInTiles*skillLevel);
		} else {
			Foe u = (Foe) user;
			tiles = u.getTilesInRange(rangeInTiles*skillLevel);
		}
		for(Tile t : tiles) {
			if(!t.isWalkable()) {
				tiles.removeValue(t, false);
			}
		}
		
		int t = MathUtils.random(tiles.size-1);
		
		if(user instanceof Foe) {
			((Foe) user).translocate(tiles.get(t).getX(), tiles.get(t).getY());
			return cooldown;
		}
		user.setX(tiles.get(t).getX());
		user.setY(tiles.get(t).getY());
		
		return cooldown;
	}
	
	/*@Override
	public float activate(Actor user, GameWorld gameWorld, int skillLevel) {
		float dx = 0;
		float dy = 0;
		
		//Check if the target tile is free to land on
		boolean canLand = false;
		while(!canLand) {
			int randomDir = MathUtils.random(360);
			int randomRange = MathUtils.random(1, rangeInTiles)*skillLevel;
			dx = randomRange*MathUtils.cosDeg(randomDir)*gameWorld.getWorldMap().getTileSize();
			dy = randomRange*MathUtils.sinDeg(randomDir)*gameWorld.getWorldMap().getTileSize();
//			int t = gameWorld.getWorldMap().getTile(gameWorld.getCharacter().getX()+dx, gameWorld.getCharacter().getY()+dy);
			int t = gameWorld.getWorldMap().getTile(user.getX()+dx, user.getY()+dy);
			if(t < 0 || t >= gameWorld.getWorldMap().getChildren().size) {
				continue;
			}
			
			int indexX = ((Tile)gameWorld.getWorldMap().getChildren().get(t)).getIndexX();
			int indexY = ((Tile)gameWorld.getWorldMap().getChildren().get(t)).getIndexY();
			if(t < gameWorld.getWorldMap().getChildren().size && t >= 0) {
			if(gameWorld.getWorldMap().isWalkable(indexX, indexY)) {
				if(t < gameWorld.getWorldMap().getChildren().size && t >= 0) {
					if(((Tile)gameWorld.getWorldMap().getChildren().get(t)).isWalkable()) {
						canLand = true;
					}
				}
			}
			}
		}
		
		if(canLand) {
			user.setX(gameWorld.getCharacter().getX()+dx);
			user.setY(gameWorld.getCharacter().getY()+dy);
		} else {
			//TODO: Print some kind of warning, make some warning sound
		}
		
		return cooldown;
	}*/

}
