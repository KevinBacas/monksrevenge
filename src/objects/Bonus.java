package objects;

import monksrevenge.FakeGameContainer;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;

/* Classe représentant un bonus de vie */
public class Bonus extends GameObject {
	
    public static enum bonusTypes {
        LIFEUP, POWERUP, ARMAGGEDON, SPEEDUP
    }
    
    private bonusTypes currentType = null;
	
	public Bonus(bonusTypes t) {
		this.setCurrentType(t);
		this.setXSpeed(-100);
		this.setYSpeed(0);
		this.setXAcceleration(101);
		this.setYAcceleration(101);
		this.setXSpeedMax(200);
		this.setYSpeedMax(200);
		
		switch(t){
			case LIFEUP:
				this.setImage("img/LifeUp.png");
				break;
			case POWERUP:
				this.setImage("img/PowerUp.png");
				break;
			case ARMAGGEDON:
				this.setImage("img/Armaggedon.png");
				break;
			case SPEEDUP:
				this.setImage("img/speedUp.png");
				break;
			default:
				this.setImage("img/LifeUp.png");
				break;
		}
		
		this.setXPos((int) (FakeGameContainer.getInstance().getWidth()/2 + (FakeGameContainer.getInstance().getWidth()/2 * Math.random())));
		this.setYPos((int) (this.getHeight()*2 + (FakeGameContainer.getInstance().getHeight() - (this.getHeight()*2)) * Math.random()));
		
		this.setShape(new Circle(this.getXPos(), this.getYPos(), this.getWidth()/2));
	}
	
	@Override
	public void onCollision(GameObject o) throws SlickException {	}

	@Override
	public void update(int delta)
			throws SlickException {
		this.updateLocation(delta);
		this.getShape().setLocation(this.getXPos(), this.getYPos());
		if(this.isOutOfRender()){
			GamePackage.getInstance().addRemove(this);
		}
	}

	public bonusTypes getCurrentType() {
		return currentType;
	}

	public void setCurrentType(bonusTypes currentType) {
		this.currentType = currentType;
	}
	
	@Override
	public int getHeight(){
		return (int) (256*FakeGameContainer.getInstance().getScale());
		
	}
	
	@Override
	public int getWidth(){
		return (int) (256*FakeGameContainer.getInstance().getScale());
		
	}

}
