package com.us.ld31.game.skills.translocations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.skills.Skill;
import com.us.ld31.utils.tiles.Tile;

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
		
		float dx = mouseX - gameWorld.getCharacter().getX();
		float dy = mouseY - gameWorld.getCharacter().getY();
		//Check if teleport coordinates are within range or not
		if(Math.sqrt(dx*dx+dy*dy) <= rangeInTiles*gameWorld.getWorldMap().getTileSize()) {
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
		} else {
			float dir = (new Vector2(mouseX-gameWorld.getCharacter().getX(), mouseY-gameWorld.getCharacter().getY())).angle();
			float range = rangeInTiles*gameWorld.getWorldMap().getTileSize();
			
			float targetX = gameWorld.getCharacter().getX() + range*MathUtils.cosDeg(dir);
			float targetY = gameWorld.getCharacter().getY() + range*MathUtils.sinDeg(dir);
			int t = gameWorld.getWorldMap().getTile(targetX, targetY);
			int indexX = (int) Math.floor(targetX / gameWorld.getWorldMap().getTileSize());
			int indexY = (int) Math.floor(targetY / gameWorld.getWorldMap().getTileSize());
			if(gameWorld.getWorldMap().isWalkable(indexX, indexY)) {
				if(t < gameWorld.getWorldMap().getChildren().size && t >= 0) {
					if(((Tile)gameWorld.getWorldMap().getChildren().get(t)).isWalkable()) {
						canLand = true;
						mouseX = targetX;
						mouseY = targetY;
					}
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
