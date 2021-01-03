package project1;

public class Switch extends Sprite {
	public Switch(float x, float y) {
		super("res/switch.png", x, y);
	}
	
	
	public void update(int delta) {
		
		//if the door is closed, render the image and place the sprite back to 
		// its original position.
		// If the door is not closed render the image elsewhere, out of bounds
		// of the game (sorry for using a hack)
		for (int i =0; i<World.getSprites().size();i++) {
			Sprite tempsprite =  World.getSprites().get(i) ;
			
			// setting the door to be opened, when stone or ice is on the Switch coordinate 
			if (tempsprite.getX() == getX() && tempsprite.getY() == getY() && (tempsprite instanceof Stone
					|| (tempsprite instanceof Ice))) {
				Door door = (Door)World.getSpriteOfType("res/door.png");
				door.setX((App.OUT_OF_BOUNDS));
				door.setY((App.OUT_OF_BOUNDS));
				door.setClosed(false);
				}
			
			// setting the door to be closed, when stone or ice is on the Switch coordinate
			// door.getData 0 and 1 is the initial x and y coordinate of the door respectively
			else if (tempsprite.getX() == getX() && tempsprite.getY() == getY() && !(tempsprite instanceof Stone
					|| (tempsprite instanceof Ice))) {
				Door door = (Door)World.getSpriteOfType("res/door.png");
				door.setX(door.getData()[0]);
				door.setY(door.getData()[1]);
				door.setClosed(true);
				
			}
			
			
		}
		
	}
}