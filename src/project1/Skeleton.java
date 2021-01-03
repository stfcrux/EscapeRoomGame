package project1;

import org.newdawn.slick.Input;

public class Skeleton extends Movable {
	/** sum delta used to Keep track of time*/
	private int sumdelta=0;
	/** Skeleton initial direction is up*/
	private int dir=DIR_UP;
	
	public Skeleton(float x, float y) {
		super("res/skull.png", x, y);
	}
	
	/** Changing skeleton's direction once it collides with anything*/
	public void update(int delta) {
		
		sumdelta+=delta;
		
		// If skeleton collides with something while moving up 
		if(Loader.SkellisCollided(getX(),getY()-App.TILE_SIZE)) {		
			dir=DIR_DOWN;
		}
		// If skeleton collides with something while moving down 
		if(Loader.SkellisCollided(getX(),getY()+App.TILE_SIZE)){ 
			dir=DIR_UP;
		}
		
		// If 1 second passes and skeleton is not blocked, move to destination 
		if(sumdelta>App.SKELETON_TILE_SPEED) {
			if(!Loader.isBlocked(getX(),getY())){
				if (dir==DIR_UP) {
					moveToDest(DIR_UP);
					onMove(getX(),getY());
				}	
				else {
					moveToDest(DIR_DOWN);
					onMove(getX(),getY());
				}	
			}
		sumdelta =0;
		}
	}
	
	/** If skeleton collides with player, kill player */
	public void onMove(float testX, float testY) {
		if (World.getSpriteOfType("res/player_left.png", testX, testY) != null) {
				World.setKilled(true);	
		}
		
	}
	
	
}
