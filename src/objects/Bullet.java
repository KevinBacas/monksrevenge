package objects;

import monksrevenge.MonksRevengeGame;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;

/** Classe représentant les balles */

public class Bullet extends GameObject {
	
	/** -1 si ennemi, l'index du Player dans l'ArrayList du Client/Serveur */
	private int ownerID;
	
	/** Default : 1; */
	private int damage;
	
	/** Constructeur par défaut */
	public Bullet() {
		this(0,0,0,0,0,0,0,0,false,-1,1);
	}

	/** Constructeur avec arguments */
	public Bullet(int xPos, int yPos, int xSpeed, int ySpeed, int xAcceleration, int yAcceleration, int xSpeedMax, int ySpeedMax, boolean friendly, int ownerID, int damage) {
		this.setXPos(xPos);
		this.setYPos(yPos);
		this.setXSpeed(xSpeed);
		this.setYSpeed(ySpeed);
		this.setXAcceleration(xAcceleration);
		this.setYAcceleration(yAcceleration);
		this.setXSpeedMax(xSpeedMax);
		this.setYSpeedMax(ySpeedMax);
		
		this.setImage(GamePackage.getInstance().getResourceLoader().getImage("img/bulletTrollface.png"));
		
		this.setFriendly(friendly);
		this.setOwnerID(ownerID);
		this.setDamage(damage);
		
		this.setShape(new Circle(this.getXPos(), this.getYPos(), this.getImage().getWidth()*MonksRevengeGame.scale/2));
	}
	
	public Bullet(Bullet bullet) {
		this(bullet.getXPos(), 
				bullet.getYPos(), 
				bullet.getXSpeed(), 
				bullet.getYSpeed(), 
				bullet.getXAcceleration(), 
				bullet.getYAcceleration(), 
				bullet.getXSpeedMax(), 
				bullet.getYSpeedMax(), 
				bullet.getFriendly(),
				bullet.getOwnerID(),
				bullet.getDamage());
	}

	@Override
	public void onCollision(GameObject o) throws SlickException {
		
		// Collision avec un ennemi
		if(o instanceof Enemy){
			if (this.getFriendly()){
				((Enemy) o).setLife(((Enemy) o).getLife()-this.damage);
				if(((Enemy) o).getLife() <= 0){
					Player p = GamePackage.getInstance().getPlayerArray().get(this.ownerID);
					p.setScore(p.getScore()+50);
					GamePackage.getInstance().addRemove((Enemy) o);
				}
				GamePackage.getInstance().addRemove(this);
			}
		}
		
		// Collision avec un joueur
		if(o instanceof Player){
			if (!this.getFriendly()){
				((Player) o).setLife(((Player) o).getLife() - this.getDamage());
				if(((Player) o).getLife() <= 0){//Cas ou le player Meurt
					//GamePackage.getInstance().addRemove((Player) o);
				}
				GamePackage.getInstance().addRemove(this);
			}
		}
	}


	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		this.updateLocation(delta);
		this.getShape().setLocation(this.getXPos(), this.getYPos());
		//S'il sort de l'écran on le delete
		if(this.isOutOfRender()){
			GamePackage.getInstance().addRemove(this);
		}
	}

	public int getOwnerID() {
		return this.ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public int getDamage() {
		return this.damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}