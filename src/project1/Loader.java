package project1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Loader {
	private static String[][] types;
	
	private static int world_width;
	private static int world_height;
	private static int offset_x;
	private static int offset_y;
	
	/**
	 * Create the appropriate sprite given a name and location.
	 * @param name	the name of the sprite
	 * @param x		the x position
	 * @param y		the y position
	 * @return		the sprite object
	 */
	private static Sprite createSprite(String name, float x, float y) {
		switch (name) {
			case "wall":
				return new Wall(x, y);
			case "floor":
				return new Floor(x, y);
			case "stone":
				return new Stone(x, y);
			case "target":
				return new Target(x, y);
			case "player":
				return new Player(x, y);
			case "cracked":
				return new CrackedWall(x, y);
			case "tnt":
				return new Tnt(x, y);
			case "switch":
				return new Switch(x, y);
			case "mage":
				return new Mage(x, y);
			case "skeleton":
				return new Skeleton(x, y);
			case "ice":
				return new Ice(x, y);
			case "explosion":
				return new Explosion(x, y);
			case "rogue":
				return new Rogue(x, y);
			case "door":
				return new Door(x, y);	

		}
		return null;
	}
	
	/** Converts a world coordinate to a tile coordinate,	
	 *  and returns if that location is a blocked tile
	 */
	public static boolean isBlocked(float x, float y) {
		boolean bool = false;
		x -= offset_x;
		x /= App.TILE_SIZE;
		y -= offset_y;
		y /= App.TILE_SIZE;
		
		// Rounding is important here
		x = Math.round(x);
		y = Math.round(y);
		
		// Do bounds checking!
		if (x >= 0 && x < world_width && y >= 0 && y < world_height) {
			if (types[(int)x][(int)y].equals("wall")) {			
				bool = true;
			}			
		}
		// check if location is blocked tile
		for (int i =0; i<World.getSprites().size();i++) {
			Sprite tempsprite =  World.getSprites().get(i) ;
			if  ((tempsprite instanceof CrackedWall
				||tempsprite instanceof Door)
				&& tempsprite.getX()== (offset_x+ x * App.TILE_SIZE )
				&& 	tempsprite.getY()== (offset_y + y * App.TILE_SIZE )){
				bool = true;
			}
		}	
		return bool;
	}
	
	/** isCollided is used to check if a pushable block is 
	 * able to move and it is not getting blocked 
	 * @param x, x is the x coordinate of where the
	 * pushable block is pushed to
	 * @param y, y is the y coordinate of where the
	 * pushable block is pushed to
	 * @return return true if can't occupy the coordinate it
	 * wants to go to
	 */
	public static boolean isCollided(float x, float y) {
		boolean bool = false;
		for (int i =0; i<World.getSprites().size();i++) {
			Sprite tempsprite =  World.getSprites().get(i) ;
			if ( (tempsprite instanceof Stone
					||tempsprite instanceof Ice
					||tempsprite instanceof Tnt
					|| tempsprite instanceof Door
					||tempsprite instanceof CrackedWall
					||tempsprite instanceof Mage
					||tempsprite instanceof Skeleton
					||tempsprite instanceof Player
					||tempsprite instanceof Rogue)				
					&& tempsprite.getX()== x 
					&& 	tempsprite.getY()== y) {
				bool = true;
			}
		}
		// Default to blocked
		return bool;
	}
	
	/** This method is used to check if the player/character can move to that
	 * destination and not overwrite blocks.
	 * It checks if the block the player/charcter wants to
	 * push is able to be pushed and not that it is blocked by a wall or
	 * another stone or any other unmovable object
	 * @param x1, x coordinate of the block that is about to be pushed
	 * @param y1, y coordinate of the block that is about to be pushed
	 * @param x2, x coordinate of the block in direction pushed of the x1,y1 block
	 * @param y2, y coordinate of the block in direction pushed of the x1,y1 block
	 * @return true if there exists a block blocking the x1y1 block
	 */
	public static boolean isItemBlocked(float x1, float y1,float x2, float y2) {
		
		
		for (int i =0; i<World.getSprites().size();i++) {
			Sprite tempsprite1 =  World.getSprites().get(i) ;
			
			// Special case for tnt and cracked wall where tnt can be pushed into cracked
			// wall, thus return false even if tnt is blocked by cracked wall
			if ( tempsprite1 instanceof Tnt && tempsprite1.getX()== x1	&& 	tempsprite1.getY()== y1) {
					for (int j =0; j<World.getSprites().size();j++) {
						Sprite tempsprite2 =  World.getSprites().get(j) ;
							if ( tempsprite2.getX()== x2&&tempsprite2.getY()== y2 && tempsprite2 instanceof CrackedWall){
								return false;
							}
					}	
			}
			// The standard case for all movable blocks and skeleton
			// if what the character is trying to move is stone,tnt,ice or skeleton
			// skeleton(for level three where stone must be blocked by skeleton when
			// rogue is pushing it)
			if ( tempsprite1 instanceof Stone|| tempsprite1 instanceof Ice
				||tempsprite1 instanceof Tnt||tempsprite1 instanceof Skeleton) {
					if( tempsprite1.getX()== x1	&&tempsprite1.getY()== y1) {
						
						//if the block getting pushed is blocked by these,
						// return true, they should not be able to be pushed
						for (int j =0; j<World.getSprites().size();j++) {
							Sprite tempsprite2 =  World.getSprites().get(j) ;
							if (tempsprite2 instanceof Stone
								||tempsprite2 instanceof Wall
								||tempsprite2 instanceof Ice
								||tempsprite2 instanceof Tnt
								|| tempsprite2 instanceof Door
								||tempsprite2 instanceof CrackedWall
								|| tempsprite2 instanceof Mage
								||tempsprite2 instanceof Skeleton
								||tempsprite2 instanceof Rogue) {		
									if ( tempsprite2.getX()== x2&&tempsprite2.getY()== y2 ){
										return true;
									}	
								}	
						}
					}
		}
	}

		return false;
		
	}
	/** Special Collision detection just for skeleton
	 * at a given x and y coordinate  
	 * @param x, x coordinate at particular position
	 * @param y, y coordinate at particular position
	 * @return true if collided else false
	 */
	public static boolean SkellisCollided(float x, float y) {
		for (int i =0; i<World.getSprites().size();i++) {
			Sprite tempsprite =  World.getSprites().get(i) ;
			if (( (tempsprite instanceof CrackedWall)
					||tempsprite instanceof Door
					||tempsprite instanceof Stone
					||tempsprite instanceof Wall)
					&& tempsprite.getX()== (x)
					&& 	tempsprite.getY()== (y)) {
				return true;
				
			}					
		}
		return false;
	}

		
	/**
	 * Loads the sprites from a given file.
	 * @param filename
	 * @return
	 */
	public static ArrayList<Sprite> loadSprites(String filename) {
		ArrayList<Sprite> list = new ArrayList<>();
		
		// Open the file
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			
			// Find the world size
			line = reader.readLine();
			String[] parts = line.split(",");
			world_width = Integer.parseInt(parts[0]);
			world_height = Integer.parseInt(parts[1]);
			
			// Create the array of object types
			types = new String[world_width][world_height];
			
			// Calculate the top left of the tiles so that the level is
			// centred
			offset_x = (App.SCREEN_WIDTH - world_width * App.TILE_SIZE) / 2;
			offset_y = (App.SCREEN_HEIGHT - world_height * App.TILE_SIZE) / 2;

			// Loop over every line of the file
			while ((line = reader.readLine()) != null) {
				String name;
				float x, y;
				
				// Split the line into parts
				parts = line.split(",");
				name = parts[0];
				x = Integer.parseInt(parts[1]);
				y = Integer.parseInt(parts[2]);
				types[(int)x][(int)y] = name;
				
				// Adjust for the grid
				x = offset_x + x * App.TILE_SIZE;
				y = offset_y + y * App.TILE_SIZE;
				
				// Create the sprite
				list.add(createSprite(name, x, y));
				// if cracked wall present, add explosion sprite into list
				if (name.equals("cracked")){
					list.add(createSprite("explosion",x,y));
				}
			
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
