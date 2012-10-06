package objects;

import monksrevenge.MonksRevengeGame;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;

/* Classe représentant un bonus de vie */
public class Bonus extends GameObject {
	
    public static enum bonusTypes {
        LIFEUP, POWERUP, ARMAGGEDON, SHIELD, SPEEDUP
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
				this.setImage(GamePackage.getInstance().getResourceLoader().getImage("img/LifeUp.png"));
				break;
			case POWERUP:
				this.setImage(GamePackage.getInstance().getResourceLoader().getImage("img/PowerUp.png"));
				break;
			case ARMAGGEDON:
				this.setImage(GamePackage.getInstance().getResourceLoader().getImage("img/Armaggedon.png"));
				break;
			case SHIELD:
				this.setImage(GamePackage.getInstance().getResourceLoader().getImage("img/Shield.png"));
				break;
			case SPEEDUP:
				this.setImage(GamePackage.getInstance().getResourceLoader().getImage("img/SpeedUp.png"));
				break;
			default:
				this.setImage(GamePackage.getInstance().getResourceLoader().getImage("img/LifeUp.png"));
				break;
		}
		
		this.setXPos((int) (MonksRevengeGame.appInstance.getWidth()/2 + (MonksRevengeGame.appInstance.getWidth()/2 * Math.random())));
		this.setYPos((int) (MonksRevengeGame.appInstance.getHeight() * Math.random() - (this.getImage().getHeight()*MonksRevengeGame.scale)));
		
		this.setShape(new Circle(this.getXPos(), this.getYPos(), this.getImage().getWidth()*MonksRevengeGame.scale/2));
	}
	
	@Override
	public void onCollision(GameObject o) throws SlickException {	}

	@Override
	public void update(GameContainer container, int delta)
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

}
