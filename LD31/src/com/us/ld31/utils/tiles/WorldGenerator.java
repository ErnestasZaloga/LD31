package com.us.ld31.utils.tiles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.us.ld31.LD31;

public class WorldGenerator {
	private static int radius = 8;
	
	private static char grass = '.';
	private static char rock = 'O';
	private static char road = '=';
	private static char house = '#';
	private static char shop = '$';
//	private static char grass = '.';
	
	public static WorldMap generateWorld(LD31 app, WorldMap worldMap) {
		float tileWH = worldMap.getTileSize();
		TileFactory tileFactory = new TileFactory(app, tileWH);
		char[][] map = new char[worldMap.getTilesX()][worldMap.getTilesY()];
		//Fill map with grass
		for(int x=0; x<map.length; x++) {
			for(int y=0; y<map[0].length; y++) {
				map[x][y] = grass;
			}
		}
		//Scatter some rocks around
		for(int i=0; i<40; i++) {
			int randX = MathUtils.random(0, map.length-1);
			int randY = MathUtils.random(0, map[0].length-1);
			map[randX][randY] = rock;
		}
		//TODO scatter some trees
		//...
		
		//Add roads
		int cityX = (int) Math.ceil(worldMap.getTilesX()/2);
		int cityY = (int) Math.ceil(worldMap.getTilesY()/2);
		drawRoad(map, cityX, cityY);
		
		//Convert chars to actual actors
		for(int x=0; x<map.length; x++) {
			for(int y=0; y<map[0].length; y++) {
				if(map[x][y] == grass) {
					worldMap.addActor(tileFactory.createGrassTile(x*tileWH, y*tileWH));
				} else if(map[x][y] == rock) {
					worldMap.addActor(tileFactory.createRockTile(x*tileWH, y*tileWH));
				} else if(map[x][y] == road) {
					worldMap.addActor(tileFactory.createRoadTile(x*tileWH, y*tileWH));
				}
			}
		}
		
		//Then generate buildings
		drawBuildings(map, cityX, cityY);
		
		for(int x=0; x<map.length; x++) {
			for(int y=0; y<map[0].length; y++) {
				if(map[x][y] == shop) {
					Tile t = tileFactory.createShopTile(x*tileWH, y*tileWH);
					int width = (int) (t.getWidth()/tileWH);
					int height = (int) (t.getHeight()/tileWH);
					for(int dx=0; dx<width; dx++) {
						for(int dy=0; dy<height; dy++) {
							worldMap.occupiedIndexes.add(new Vector2(x+dx, y+dy));
						}
					}
					worldMap.addActor(t);
				} else if(map[x][y] == house) {
					Tile t = tileFactory.createHouseTile(x*tileWH, y*tileWH);
					int width = (int) (t.getWidth()/tileWH);
					int height = (int) (t.getHeight()/tileWH);
					for(int dx=0; dx<width; dx++) {
						for(int dy=0; dy<height; dy++) {
							worldMap.occupiedIndexes.add(new Vector2(x+dx, y+dy));
						}
					}
					worldMap.addActor(t);
				}
			}
		}
		
		//Convert tiles that should be roads to small houses
		for(int x=0; x<map.length; x++) {
			for(int y=0; y<map[0].length; y++) {
				if(map[x][y] == '=') {
				//((Tile)getChildren().get(indexX * tilesY + indexY)).isWalkable()
					((Tile)worldMap.getChildren().get(x * worldMap.getTilesY() + y)).setWalkable(true);
					((Tile)worldMap.getChildren().get(x * worldMap.getTilesY() + y)).setRegion(app.assets.tileRoad);
					((Tile)worldMap.getChildren().get(x * worldMap.getTilesY() + y)).setSize(tileWH, tileWH);
				}
			}
		}
		
		return worldMap;
	}
	
	private static void drawBuildings(char[][] map, int cityX, int cityY) {
		//Place shops
		map[cityX+radius][cityY+2] = shop;
		map[cityX-radius][cityY+2] = shop;
		map[cityX+2][cityY+radius] = shop;
		map[cityX+2][cityY-radius] = shop;
		//Place houses
		int houseCount = 0;
		while(houseCount < 10) {
			int randX = MathUtils.random(1, map.length-2);
			int randY = MathUtils.random(1, map[0].length-2);
			if(map[randX][randY] != road && map[randX][randY] != shop) {
				houseCount++;
				map[randX][randY] = house;
				/* Determine in which quandrant of the map the house is:
				 * 1|2
				 * -O-
				 * 4|3
				 */
				if(randX < cityX && randY > cityY) {
					if(MathUtils.randomBoolean()) {
						map[randX][randY-1] = 'V';
					} else {
						map[randX][randY-1] = '>';
					}
				} else if(randX > cityX && randY > cityY) {
					if(MathUtils.randomBoolean()) {
						map[randX][randY-1] = '<';
					} else {
						map[randX][randY-1] = 'V';
					}
				} else if(randX > cityX && randY < cityY) {
					if(MathUtils.randomBoolean()) {
						map[randX-1][randY] = '^';
					} else {
						map[randX][randY-1] = '<';
					}
				} else if(randX < cityX && randY < cityY) {
					if(MathUtils.randomBoolean()) {
						map[randX][randY-1] = '>';
					} else {
						map[randX-1][randY] = '^';
					}
				}
			}
		}
		for(int x=0; x<map.length; x++) {
			for(int y=0; y<map[0].length; y++) {
				if(map[x][y] == '>') {
					char target = '>';
					int currentPos = x;
					while(target != '=') {
						map[currentPos][y] = '=';
						currentPos++;
						target = map[currentPos][y];
					}
				} else if(map[x][y] == 'V') {
					char target = 'V';
					int currentPos = y;
					while(target != '=') {
						map[x][currentPos] = '=';
						currentPos--;
						target = map[x][currentPos];
					}
				} else if(map[x][y] == '<') {
					char target = '<';
					int currentPos = x;
					while(target != '=') {
						map[currentPos][y] = '=';
						currentPos--;
						target = map[currentPos][y];
					}
				} else if(map[x][y] == '^') {
					char target = '^';
					int currentPos = y;
					while(target != '=') {
						map[x][currentPos] = '=';
						currentPos++;
						target = map[x][currentPos];
					}
				}
			}
		}
	}
	
	private static void drawRoad(char[][] map, int cityX, int cityY) {
		map[cityX][cityY] = road;
		//left
		for(int i=cityX; i>=0; i--) {
			map[i][cityY-1] = road;
			map[i][cityY] = road;
			map[i][cityY+1] = road;
		}
		//right
		for(int i=cityX; i<map.length; i++) {
			map[i][cityY-1] = road;
			map[i][cityY] = road;
			map[i][cityY+1] = road;
		}
		//up
		for(int i=cityY; i<map[0].length; i++) {
			map[cityX-1][i] = road;
			map[cityX][i] = road;
			map[cityX+1][i] = road;
		}
		//down
		for(int i=cityY; i>=0; i--) {
			map[cityX-1][i] = road;
			map[cityX][i] = road;
			map[cityX+1][i] = road;
		}
		//center
		for(int x=0; x<map.length; x++) {
			for(int y=0; y<map[0].length; y++) {
				int dX = cityX - x;
				int dY = cityY - y;
				if(Math.sqrt(dX*dX+dY*dY) <= radius) {
					map[x][y] = road;
				}
			}
		}
	}
	
}
