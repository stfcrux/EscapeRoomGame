package project1;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class World {
	private static ArrayList<Sprite> sprites;
	 /** initial Level of Game */
	private static int level = 0;
	 /** Player is initially not killed */
	private static boolean killed=false;	
	
	public World() {
		sprites = Loader.loadSprites("res/levels/"+level+".lvl");
	}
	
	 /** update all sprites, restart level if Key R is pressed,
	  *  check if the level is completed, restart level if player
	  *  is killed
	  */
	public void update(Input input, int delta) {
		
		for (Sprite sprite : sprites) {
			if (sprite != null) {
				if (sprite instanceof Player) {
					Player player = (Player)sprite;
					player.update(input, delta);	
				}				
				else {
				sprite.update(delta);
				}
			}
		}
		
		if (input.isKeyPressed(Input.KEY_R)||killed == true) {
			restartLevel();
		}
		
		// load next level on completion 
		if (isCompleted()) {
			for (int j= sprites.size()-1;j>=0;j--) {
				sprites.remove(j);
			}
			if (level<5) {
			level++;
			}
			sprites = Loader.loadSprites("res/levels/"+level+".lvl");	
		}
		
	}
	
	public void render(Graphics g) {
		for (Sprite sprite : sprites) {
			if (sprite != null) {
				sprite.render(g);
			}
		}	
	}
	
	/** Reload the current level and make player = not killed */
	public static void restartLevel() {
		for (int j= sprites.size()-1;j>=0;j--) {
			sprites.remove(j);
		}
		
		sprites = Loader.loadSprites("res/levels/"+level+".lvl");
		killed = false;
	}
	
	/** Checking if all targets are occupied by a block */
	public  boolean isCompleted() {
		int numoftargets=0;
		int completed=0;
		// counting number of targets 
		for (int z =0;z<sprites.size();z++) {
			if (sprites.get(z) instanceof Target){
				numoftargets++;
			}
		}		
		for (int i =0;i<sprites.size();i++) {
			Sprite stones = sprites.get(i) ;
			if (stones instanceof Stone||stones instanceof Ice) {
				for (int j =0;j<sprites.size();j++) {
					Sprite targets = sprites.get(j);
					if (targets instanceof Target) {
						if (stones.getX()==targets.getX() && stones.getY()==targets.getY()) {
							completed++;
						}
					}
				}
			}			
			if (completed == numoftargets) {
				return true;			
			}
		}
		
		return false;
	
	}
	
	/** getting the sprite from array list
	 * at a particular position
	 * @param tag the image source string
     * @param x x coordinate of particular position
     * @param y y coordinate of particular position
	 *  */
	public static Sprite getSpriteOfType(String tag,float x, float y) {
		for (int i =0;i<sprites.size();i++) {
			Sprite tempSprite = sprites.get(i) ;
			if (tempSprite.getImagestf().equals(tag)&&tempSprite.getX()==x&& tempSprite.getY()==y) {
				return tempSprite;	
			}	
		}
		return null;	
	}
	/** getting the sprite if present in
	 * the array list
	 * @param tag the image source string
	 */
	public static Sprite getSpriteOfType(String tag) {
		for (int i =0;i<sprites.size();i++) {
			Sprite tempSprite = sprites.get(i) ;
			if (tempSprite.getImagestf().equals(tag)) {
				return tempSprite;			
			}	
		}
		return null;	
	}

	public static ArrayList<Sprite> getSprites() {
		return sprites;
	}

	public static void setSprites(ArrayList<Sprite> sprites) {
		World.sprites = sprites;
	}

	public static boolean isKilled() {
		return killed;
	}

	public static void setKilled(boolean killed) {
		World.killed = killed;
	}
	
	
}