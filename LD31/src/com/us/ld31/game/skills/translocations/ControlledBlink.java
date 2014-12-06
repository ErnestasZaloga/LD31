package com.us.ld31.game.skills.translocations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.skills.Skill;
import com.us.ld31.utils.tiles.Tile;
import com.us.ld31.utils.tiles.WorldMap;

public class ControlledBlink implements Skill {

	private float cooldown = 35;
	
	private int rangeInTiles = 5;
	
	@Override
	public float activate(Actor user, GameWorld gameWorld, int skillLevel) {
//		float dx = 0;
//		float dy = 0;
//		
//		//Check if the target tile is free to land on
		boolean canLand = false;
//		int dir = MathUtils.random(360);
//		int range = MathUtils.random(1, rangeInTiles)*skillLevel;
		
//		dx = range*MathUtils.cosDeg(dir)*gameWorld.getWorldMap().getTileSize();
//		dy = range*MathUtils.sinDeg(dir)*gameWorld.getWorldMap().getTileSize();
//		int t = gameWorld.getWorldMap().getTile(gameWorld.getCharacter().getX()+dx, gameWorld.getCharacter().getY()+dy);
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight()-Gdx.input.getY();
		int t = gameWorld.getWorldMap().getTile(mouseX, mouseY);
		int indexX = (int) Math.floor(mouseX / gameWorld.getWorldMap().getTileSize());
		int indexY = (int) Math.floor(mouseY / gameWorld.getWorldMap().getTileSize());
		System.out.println(mouseX + " " + mouseY);
		System.out.println("t: " + t);
		
		if(gameWorld.getWorldMap().isWalkable(indexX, indexY)) {
			if(t < gameWorld.getWorldMap().getChildren().size && t >= 0) {
				if(((Tile)gameWorld.getWorldMap().getChildren().get(t)).isWalkable()) {
					canLand = true;
				}
			}
		}
		
		if(canLand) {
			gameWorld.getCharacter().setX(mouseX);
			gameWorld.getCharacter().setY(mouseY);
		} else {
			//TODO: Print some kind of warning, make some warning sound
		}
		
		return cooldown;
	}

}
