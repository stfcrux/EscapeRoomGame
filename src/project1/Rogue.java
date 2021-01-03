package project1;

import org.newdawn.slick.Input;

public class Rogue extends Movable {
	/** Initial direction of rogue is to the left */
	private int dir=DIR_LEFT;
	
	public Rogue(float x, float y) {
		super("res/rogue.png", x, y);
	}
	
	/** Checking if rogue is blocked by anything other than stone and if it is,
	 *  it will change direction.
	 *  If the player makes a move, it will also trigger the rogue to move 
	 */
	public void update(int delta) {
		
		//checking if its left side is blocked 
		if(Loader.isBlocked(getX()-App.TILE_SIZE,getY())
		   ||Loader.isItemBlocked(getX() -App.TILE_SIZE, getY(),getX() -App.TILE_SIZE*2, getY())){
			dir=DIR_RIGHT;
		}
		// Checking if its right side is blocked 
		if(Loader.isBlocked(getX()+App.TILE_SIZE,getY())
		   ||Loader.isItemBlocked(getX() +App.TILE_SIZE, getY(),getX() + App.TILE_SIZE*2, getY())){ 
			dir=DIR_LEFT;
		}
		
		Player player = (Player)World.getSpriteOfType("res/player_left.png");
		if (player.getDir() == DIR_LEFT||player.getDir() == DIR_RIGHT
			||player.getDir() == DIR_UP||player.getDir() == DIR_DOWN) {
			onMove(dir);
		}
		
	}
	
	/** if rogue's x and y coords are on stone, it will push it,
	 * if it is on player, player = killed
	 */
	public void onMove(int dir) {
		moveToDest(dir);
		for (int i =0; i<World.getSprites().size();i++) {
			Sprite tempsprite =  World.getSprites().get(i) ;
			if (tempsprite.getX() == getX() && tempsprite.getY() == getY() 
				&&tempsprite instanceof Stone) {
				Stone stonesprite = (Stone)tempsprite;
				stonesprite.Push(dir);
			}
			if  (tempsprite instanceof Player && tempsprite.getX()== (getX())
				&& tempsprite.getY()== (getY())){
				World.setKilled(true);
		}
		}
}
}