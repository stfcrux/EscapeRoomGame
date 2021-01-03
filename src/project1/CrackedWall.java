package project1;

import org.newdawn.slick.Graphics;

public class CrackedWall extends Sprite {
	/** Cracked wall is active in the beginning */
	private boolean isActive = true;
	
	public CrackedWall(float x, float y) {
		super("res/cracked_wall.png", x, y);
	}
	
	/** Render the CrackedWall sprite in the case if it is active,
	 * 	if not, it means that the TNT block is pushed to this position
	 * 	and thus the explosionSprite will trigger/be active
	 */
	public void render(Graphics g) {
		if (isActive) {
		getImage().drawCentered(getX(), getY());
		}
		else if (isActive==false) {
			for (int i =0; i<World.getSprites().size();i++) {
				Sprite tempsprite =  World.getSprites().get(i) ;
				if (tempsprite instanceof Explosion) {
					Explosion explosionsprite = (Explosion)tempsprite;
					explosionsprite.isActive = true;
				}
		}
		}
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
