package objects;

import monksrevenge.MonksRevengeGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Classe représentant les pas beaux */
public class Enemy extends GameObject {

	/** Nombre de toucher pour tuer */
	private int life;

	private boolean isBoss;
	
	private int maxTimer;
	private int Giltimer; // Frequence des tirs pour Etienne 
	
	private boolean isHit = false;
	
	/** Constructeur par défaut */
	public Enemy() {
		this(0,0,0,0,0,0,0,0,null,false,1,false,0);
	}

	/** Constructeur avec arguments  */
	public Enemy(int xPos, int yPos, int xSpeed, int ySpeed, int xAcceleration, int yAcceleration, int xSpeedMax, int ySpeedMax, Image image, boolean friendly, int life, boolean isBoss, int ShootTimer) {
		this.setXPos(xPos);
		this.setYPos(yPos);
		this.setXSpeed(xSpeed);
		this.setYSpeed(ySpeed);
		this.setXAcceleration(xAcceleration);
		this.setYAcceleration(yAcceleration);
		this.setXSpeedMax(xSpeedMax);
		this.setYSpeedMax(ySpeedMax);
		this.setImage(image);
		this.setFriendly(friendly);
		this.setLife(life);
		this.setBoss(isBoss);
		this.maxTimer = ShootTimer;
		this.Giltimer = ShootTimer;
		
		//Hadoken
		this.setShape(this.getShapeByImage(this.getImage()));
		this.getShape().setLocation(this.getXPos(), this.getYPos());
	}

	public Enemy(Enemy enemy) {
		this(enemy.getXPos(), 
				enemy.getYPos(), 
				enemy.getXSpeed(), 
				enemy.getYSpeed(), 
				enemy.getXAcceleration(), 
				enemy.getYAcceleration(), 
				enemy.getXSpeedMax(), 
				enemy.getYSpeedMax(), 
				enemy.getImage(), 
				enemy.getFriendly(), 
				enemy.getLife(), 
				enemy.isBoss(), 
				enemy.getMaxShootTimer());
	}

	/** Getter life */
	public int getLife() {
		return this.life; 
	}

	/** Getter isBoss */
	public boolean isBoss() {
		return this.isBoss;
	}

	/** Implémentation de onCollision */
	public void onCollision(GameObject o) throws SlickException {
		
		// Collision avec un Player
		if (o instanceof Player){
			((Player) o).setLife(((Player) o).getLife() - 1);
			if(((Player) o).getLife() <= 0){//Cas ou le player Meurt
				//GamePackage.getInstance().addRemove((Player) o);
			}
			GamePackage.getInstance().addRemove(this);
		}
	}

	/** Setter isBoss */
	public void setBoss(boolean bool) {
		this.isBoss = bool;
	}

	/** Setter life */
	public void setLife(int life) {
		this.life = life;
	}

	/** Implémentation de update  */
	public void update(GameContainer container, int delta) throws SlickException {
		this.updateLocation(delta);
		this.updateShootTimer(delta);
		if(this.getShootTimer() < 0){
			this.fireToAngle(0);
			this.resetShootTimer();
		}
		this.getShape().setLocation(this.getXPos(), this.getYPos());
		//S'il sort de l'écran on le delete
		if(this.isOutOfRender()){
			GamePackage.getInstance().addRemove(this);
		}
	}
	
	/** Fonction qui permet de tirer dans un angle */
	public void fireToAngle(int angle){
		int coefdir = (int)(Math.tan(angle));
		GamePackage gp = GamePackage.getInstance();
		int xSpawn = (int) (this.getXPos() + (this.getImage().getWidth() * MonksRevengeGame.scale - (this.getImage().getWidth() * MonksRevengeGame.scale) / 4) / 2);
		int ySpawn = (int) (this.getYPos() + (this.getImage().getHeight() * MonksRevengeGame.scale - (this.getImage().getHeight() * MonksRevengeGame.scale) / 4) /2 );
		int yspeed = 250*coefdir > this.getYSpeedMax() ? this.getYSpeedMax() : 250*coefdir;
		int xspeed = yspeed == this.getYSpeedMax() ? (int)yspeed/coefdir : 250;
		gp.addBullet(new Bullet(xSpawn, ySpawn, xspeed, yspeed, 100,100,500,500, false, -1, 1));
	}

	/** Getter du timer de tir */
	public int getShootTimer() {
		return this.Giltimer;
	}

	/** Setter du timer de shoot */
	public void resetShootTimer(){
		this.Giltimer = this.maxTimer;
	}
	
	/** Methode qui permet d'augmenter le timer de tir */
	public void updateShootTimer(int giltimer) {
		this.Giltimer -= giltimer;
	}
	
	public boolean isHit() {
		return this.isHit;
	}

	public void setIsHit(boolean isHit) {
		this.isHit = isHit;
	}
	
	public int getMaxShootTimer(){
		return this.maxTimer;
	}
	
	public void setShootTimer(int timer){
		this.Giltimer = timer;
	}
	
	public void setMaxShootTimer( int timer){
		this.maxTimer = timer;
	}
}