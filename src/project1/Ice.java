package project1;

import org.newdawn.slick.Input;

public class Ice extends Pushable{
	private int dir;
	/** sumdelta used to keep track of time*/
	private int sumdelta=0;
	/** lastX and lastY are used to keep track of the 
	 *  position of when it was last pushed,
	 */
	private float lastX = getX();
	private float lastY= getY();
	
	public boolean isSliding=false;
	
	public Ice(float x, float y) {
		super("res/ice.png", x, y);
	}
	
	/** using sumdelta to keep track of time, moving one tile
	 *  every 0.250seconds.
	 *  We are also checking if it is still sliding/it has not hit a wall
	 *  for each case, thus moving to destination first, then check if 
	 *  it is different from the X or Y before the move
	 */
	public void update(int delta) {	
			sumdelta+=delta;
			if (isSliding && sumdelta >App.ICE_TILE_SPEED_TIME) {
				// define x and y to store the previous x and y 
				// coordinate before the push
				float x = getX();
				float y = getY();
					if (dir== DIR_NONE) {
						isSliding = false;					
					}
				
					if (dir== DIR_LEFT) {					
						moveToDest(dir);
						if (x-getX()==0){
						isSliding = false;
					}
					}
					if (dir== DIR_RIGHT) {
						moveToDest(dir);
						if (x-getX()==0){
						isSliding = false;
					}
					}
					if (dir== DIR_UP) {
						moveToDest(dir);
						if (y-getY()==0){
						isSliding = false;
					}
					}
					if (dir== DIR_DOWN) {
						moveToDest(dir);
						if (y-getY()==0){
						isSliding = false;
					}				
				}		
			sumdelta=0;		
			}					
	}
	
	/** When pushed, we get the ice coordinates and save it to lastX and Y
	 *  we also add to the stack for the ice when it is pushed
	 */
	public void Push(int dir) {
		lastX = getX();
		lastY = getY();
		addToHistory();
		isSliding=true;
		setDir(dir);
	}
	
	private void setDir(int dir) {
			this.dir = dir;			
		}
	
	public void addToHistory() {
		Pair pair = new Pair(lastX,lastY);
		stack.push(pair);
			
	}
}
