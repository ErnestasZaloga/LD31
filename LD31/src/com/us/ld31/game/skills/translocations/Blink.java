package com.us.ld31.game.skills.translocations;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.skills.Skill;
import com.us.ld31.utils.tiles.Tile;

public class Blink implements Skill {
	
	private float cooldown = 25f;
	
	private int rangeInTiles = 7;

	@Override
	public float activate(Actor user, GameWorld gameWorld, int skillLevel) {
		//Choose random direction
		int randomDir = MathUtils.random(360);
		//Choose random range
		int randomRange = MathUtils.random(1, rangeInTiles)*skillLevel;
		//Set target tile
		int t = gameWorld.getWorldMap().getTile(gameWorld.getCharacter().getX()+60, gameWorld.getCharacter().getY()+50);
		int targetIndexX = ((Tile)gameWorld.getWorldMap().getChildren().get(t)).getIndexX();
		int targetIndexY = ((Tile)gameWorld.getWorldMap().getChildren().get(t)).getIndexY();
		
		//Check if the target tile is free to land on
		boolean canLand = true;
		
		
		if(canLand) {
			gameWorld.getCharacter().setX(targetIndexX*gameWorld.getWorldMap().getTileSize());
			gameWorld.getCharacter().setY(targetIndexY*gameWorld.getWorldMap().getTileSize());
		} else {
			//TODO: Print some kind of warning, make some warning sound
		}
		
		return cooldown;
	}

}
