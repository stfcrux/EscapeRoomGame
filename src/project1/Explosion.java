package project1;

import org.newdawn.slick.Graphics;

public class Explosion extends Sprite {
	/** Explosion sprite initially not active */
	public boolean isActive=false;
	/** delta sum used to time the length of explosion */
	private int deltasum=0;
	
	public Explosion(float x, float y) {
		super("res/explosion.png", x, y);
	}
	
	/** using deltasum to keep track of time, 0.4 seconds of explosion time
	 *  in the case when the Explosion sprite is active
	 */
	public void update(int delta) {	
		if(isActive == true) {
			deltasum+=delta;
			if (deltasum>App.EXPLOSION_DISPLAY_TIME) {
				setX(App.OUT_OF_BOUNDS);
				setY(App.OUT_OF_BOUNDS);
			}
			
		}
		
	}
	@Override
	/** Rendering the Explosion in the case it is active, 
	 *  if not we will not render it
	 */
	public void render(Graphics g) {
		if (isActive ==true) {
		getImage().drawCentered(getX(), getY());
	}
}

}