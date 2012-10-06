package objects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Star extends GameObject{
	
	public Star(int x, int y) throws SlickException{
		this.setXPos(x);
		this.setYPos(y);
		this.setXSpeed(-100);
		this.setYSpeed(0);
		this.setXAcceleration(100);
		this.setYAcceleration(100);
		this.setXSpeedMax(150);
		this.setYSpeedMax(150);
		this.setImage(GamePackage.getInstance().getResourceLoader().getImage("img/star.png"));
		this.setFriendly(true);
	}

	@Override
	public void onCollision(GameObject o) throws SlickException {
		//Fus ro dah
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		this.getImage().draw(this.getXPos(), this.getYPos());
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		this.updateLocation(delta);
	}
	
}
