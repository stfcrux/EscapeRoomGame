package project1;

import org.newdawn.slick.Graphics;

public class Door extends Sprite {
	/**data used to store the initial values of the Door's coordinates */
	private final float[] data = {getX(),getY()} ;
	private boolean closed = true;
	public Door(float x, float y) {
		super("res/door.png", x, y);
	}
	
	/**render if door is closed */
	public void render(Graphics g) {
		if (closed == true) {
			getImage().drawCentered(getX(), getY());
		}
		
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	public float[] getData() {
		return data;
	}
	
}