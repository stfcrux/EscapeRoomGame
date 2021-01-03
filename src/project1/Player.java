package project1;

import java.util.Stack;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Player extends Movable {
	private int dir;
	/** initial number of moves */
	private int movecount=0;
	/** initial used to add the positions of sprites at start of level */
	private int initial=0;
	
	public Player(float x, float y) {
		super("res/player_left.png", x, y);
	}
	
	/** Movement of player
	 * In the case of Z being pressed, the game will undo the moves of the player
	 *  */
	public void update(Input input, int delta) {
		dir = DIR_NONE;
		//Setting the initial position for the stone,ice and tnt stack
		if (initial ==0) {
			addToHistory();
			for (int i =0; i<World.getSprites().size();i++) {
				Sprite tempsprite =  World.getSprites().get(i) ;
				if (tempsprite instanceof Stone) {
					Stone stonesprite = (Stone)tempsprite;
					stonesprite.addToHistory();
				}
				if (tempsprite instanceof Tnt) {
					Tnt tntsprite = (Tnt)tempsprite;
					tntsprite.addToHistory();
				}
				if (tempsprite instanceof Ice) {
					Ice icesprite = (Ice)tempsprite;
					icesprite.addToHistory();
				}
			}
			initial=1;
		}
		
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			dir = DIR_LEFT;
			onMove(dir);
			}
		
		else if (input.isKeyPressed(Input.KEY_RIGHT)) {
			dir = DIR_RIGHT;
			onMove(dir);	
		}
		else if (input.isKeyPressed(Input.KEY_UP)) {
			dir = DIR_UP;
			onMove(dir);
		}
		else if (input.isKeyPressed(Input.KEY_DOWN)) {
			dir = DIR_DOWN;
			onMove(dir);
			
		}
		// in the case of Z getting pressed, for the three case of stone ice and 
		// Tnt they will be undone
		else if (input.isKeyPressed(Input.KEY_Z)) {
			if (movecount>0) {
				movecount--;
				for (int j =0; j<World.getSprites().size();j++) {
					Sprite tempsprite2 =  World.getSprites().get(j) ;
					if (tempsprite2 instanceof Stone) {
						Stone stonesprite = (Stone)tempsprite2;
						stonesprite.undo(stonesprite.stack);
					}
					if (tempsprite2 instanceof Ice) {
						Ice icesprite = (Ice)tempsprite2;
						icesprite.isSliding = false;
						icesprite.undo(icesprite.stack);
					}
					if (tempsprite2 instanceof Tnt) {
						Tnt tntsprite = (Tnt)tempsprite2;
						tntsprite.undo(tntsprite.stack);			
					}
							
				}
				// undo player's move
				undo(stack);
			}
		}		
		
	}
		
	
	/** rendering of the player's move count on the top left
	 *  and the rendering of player
	 */
	public void render(Graphics g) {
		g.drawString("Moves:"+movecount, 0, 0);
		getImage().drawCentered(getX(), getY());
		
	}
	
	/** On movement of player, adding to pushing to stack the ice and stone
	 * sprite x and y coordinates
	 *  We then sense for special cases of blocks where the player will
	 *  push in the case of sharing a coordinate with them
	 */
	public void onMove(int dir) {
		movecount++;
		//adding players recent move to history
		addToHistory();
		//moving the player to destination
		moveToDest(dir);
		
		// adding each and every stone,tnt and ice into history
		// if they are present in array list
		for (int i =0; i<World.getSprites().size();i++) {
			Sprite tempsprite =  World.getSprites().get(i) ;
			if (tempsprite instanceof Stone) {
				Stone stonesprite = (Stone)tempsprite;
				stonesprite.addToHistory();
			}
			if (tempsprite instanceof Tnt) {
				Tnt tntsprite = (Tnt)tempsprite;
				tntsprite.addToHistory();
			}
			if (tempsprite instanceof Ice) {
				Ice icesprite = (Ice)tempsprite;
				icesprite.addToHistory();
			}
		}
		
		//Player movement and how it pushes certain blocks
		for (int i =0; i<World.getSprites().size();i++) {
			Sprite tempsprite =  World.getSprites().get(i) ;
			if (tempsprite.getX() == getX() && tempsprite.getY() ==getY()) {
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
				// if player touches skeleton or mage it dies
				// (this makes it such that on level three
				// the rogue can switch position with you
				// which is apparently allowed to clear the level)
				if  ((tempsprite instanceof Skeleton
					||tempsprite instanceof Mage)) {
						World.setKilled(true);
				}
			}	
		}									
}
	

	
	
	public float PlayergetX() {
		return getX();
	}
	public float PlayergetY() {
		return getY();
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}


}
