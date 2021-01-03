package project1;

import java.util.Collections;

import org.newdawn.slick.Graphics;

public class Tnt extends Pushable {
	 /** Tnt sprite is initially active */
	private boolean isActive = true;
	
	public Tnt(float x, float y) {
		super("res/tnt.png", x, y);
	}
	
	 /** On tnt's move, it checks if its coords will equal to cracked wall's 
	  * coords, if it is true, it will set cracked wall to be inactive
	  * and also setting each other's x and y coords to be out of bounds
	  * sorry for using a hack
	  *  */
	public void onMove(int dir, float testX, float testY) {
		
		if (World.getSpriteOfType("res/cracked_wall.png", testX, testY) != null) {
				CrackedWall crackedwall = (CrackedWall)World.getSpriteOfType("res/cracked_wall.png", testX, testY);
				crackedwall.setActive(false);
				isActive = false;
				setX(App.OUT_OF_BOUNDS);
				setY(App.OUT_OF_BOUNDS);
				crackedwall.setX(App.OUT_OF_BOUNDS);
				crackedwall.setY(App.OUT_OF_BOUNDS);
			}
	}

	 /** Do not render if it is not active */
	public void render(Graphics g) {
		if (isActive) {
		getImage().drawCentered(getX(), getY());
	}
	}
}
