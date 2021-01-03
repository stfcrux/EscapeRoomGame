package project1;

public class Mage extends Movable {
	
	public Mage(float x, float y) {
		super("res/mage.png", x, y);
	}
	
	/** in the case where the player makes a move, the mage will move */
	public void update(int delta) {
		Player player = (Player)World.getSpriteOfType("res/player_left.png");
		if (player.getDir() == DIR_LEFT||player.getDir() == DIR_RIGHT
			||player.getDir() == DIR_UP||player.getDir() == DIR_DOWN) {
			onMove(getX(),getY());
		}
	}
	
	/** Mage Movement algorithm as given from the spec
	 *  Only way to solve is if u stay at the top two rows and then push the 
	 *  stone once near the switch.
	 *  The checking if player is on coordinate of mage as well
	 */
	public void onMove(float testX, float testY) {

		if (World.getSpriteOfType("res/player_left.png") != null) {
				Player player = (Player)World.getSpriteOfType("res/player_left.png");
				float distX = player.getX()-testX;
				float distY = player.getY()-testY;
			
				if ( Math.abs(distX)>Math.abs(distY)) {
					if (distX<0) {
						moveToDest(DIR_LEFT);
					}
					else if(distX>0) {
						moveToDest(DIR_RIGHT);
					}			
				}
				if ( Math.abs(distX)<Math.abs(distY) ) {
					if (distY<0) {
						moveToDest(DIR_UP);
					}
					else if(distY>0) {
						moveToDest(DIR_DOWN);
					}
				}
			
			}
		// In the case where the players coords is on the mage, the player
		// gets killed and game is restarted
		if (World.getSpriteOfType("res/player_left.png", getX(), getY()) != null) {		
				World.setKilled(true);		
		}
		}

}
