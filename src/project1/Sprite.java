package project1;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;

public abstract class Sprite {
	// Used to decide what direction an object is moving
	// Look up enums to find a more elegant solution!
	public static final int DIR_NONE = 0;
	public static final int DIR_LEFT = 1;
	public static final int DIR_RIGHT = 2;
	public static final int DIR_UP = 3;
	public static final int DIR_DOWN = 4;
	
	private Image image = null;
	private float x;
	private float y;
	/** image string used to compare and find classes in the array list */
	private String imagestr = null;
	
	
	public Sprite(String image_src, float x, float y) {
		try {
			image = new Image(image_src);
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.imagestr = image_src;
		this.x = x;
		this.y = y;
	}
	
	public void update(int delta) {
		
	}
	
	public void render(Graphics g) {
		image.drawCentered(x, y);
	}
	

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	public String getImagestf() {
		return imagestr;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	

}
