package project1;

import java.util.Arrays;
import java.util.Stack;

public abstract class Movable extends Sprite {
	/** initialise the stack */
	Stack<Pair> stack = new Stack<Pair>();
	
	public Movable(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	
	public void moveToDest(int dir) {
		float speed = 32;
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
		
		// Make sure the position isn't occupied!
		// and make sure that the position 2 tiles away isn't blocked as well
		// if the block 1 tile away is pushable
		if (!Loader.isBlocked(getX() + delta_x, getY() + delta_y)
			&& !Loader.isItemBlocked(getX() + delta_x, getY() + delta_y,getX() + 2*delta_x, getY() + 2*delta_y) ) {
			setX(getX() + delta_x);
			setY(getY() + delta_y);	
		}
		

	}
	/** on move we push certain blocks if it shares coordinates */
	public void onMove(int dir, float testX, float testY) {
		
		for (int i =0; i<World.getSprites().size();i++) {
			Sprite tempsprite =  World.getSprites().get(i) ;
			if (tempsprite.getX() == testX && tempsprite.getY() == testY) {
				if (tempsprite instanceof Stone) {
					Stone stonesprite = (Stone)tempsprite;
					stonesprite.Push(dir);
				}
				if (tempsprite instanceof Tnt) {
					Tnt tntsprite = (Tnt)tempsprite;
					tntsprite.Push(dir);
				}
				if (tempsprite instanceof Ice) {
					Ice icesprite = (Ice)tempsprite;
					icesprite.Push(dir);
				}				
			}	
		}	
	}
	
	/** popping the stack */
	public void undo(Stack<Pair> stack) {
		Pair tempPair = stack.pop();
		setX(tempPair.x);
		setY(tempPair.y);	
	}
	/** pushing the stack */
	public void addToHistory() {
		Pair pair = new Pair(getX(),getY());
		stack.push(pair);		
	}	
	
}
