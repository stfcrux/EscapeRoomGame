package project1;

import org.newdawn.slick.Input;

public abstract class Pushable extends Movable {

	public Pushable(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	/** pushing the block, moving them to destination given the direction */
	public void Push(int dir) {		
		moveToDest(dir);
	}
	
	@Override
	public void moveToDest(int dir) {
		float speed = App.TILE_SIZE;
		// Translate the direction to an x and y displacement
		float delta_x = 0,
			  delta_y = 0;
		switch (dir) {
			case DIR_LEFT:
				delta_x = -speed;
				break;
			case DIR_RIGHT:
				delta_x = speed;
				break;
			case DIR_UP:
				delta_y = -speed;
				break;
			case DIR_DOWN:
				delta_y = speed;
				break;
		}
		// Special case for tnt movement since it has an interaction with cracked walls 
		if (World.getSpriteOfType("res/tnt.png", getX(), getY()) != null) {
			Tnt tntsprite = (Tnt)World.getSpriteOfType("res/tnt.png", getX(), getY());
			tntsprite.onMove(dir,getX() + delta_x, getY() + delta_y);
		}
		
		// make sure the position isn't occupied and out of bounds
		if ((!Loader.isBlocked(getX() + delta_x, getY() + delta_y)) 
			&&(!Loader.isCollided(getX() + delta_x, getY() + delta_y))) {
			
			setX(getX() + delta_x);
			setY(getY() + delta_y);
		}

	}
	
	
	
	
}