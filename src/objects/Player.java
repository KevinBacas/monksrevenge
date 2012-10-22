package objects;

import network.PlayerInfo;
import objects.Bonus.bonusTypes;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import com.esotericsoftware.kryonet.Client;

/** Classe représentant une instance de joueur */
public class Player extends GameObject {
	
	/** Nombre de balles tirées pour chaque tir*/
	private int bulletNumber=1;

	private int timerBulletNumber;
	
	/** Buff de speed */
	private int speedTimer=0;
	
	private boolean hasSpeed;
	
	/** Vies du joueur */
	private int life;

	/** Vies maximum du joueur */
	private int maxLife;
	
	/** Score du joueur */
	private long score = 0;
	
	/** Temps en ms entre 2 tir */
	private int fireDelta = 500;

	/** Constructeur par défaut */
	public Player(){
		this(0,0,0,0,0,0,0,0,null,false);
	}

	/** Constructeur avec arguments */
	public Player(int xPos, int yPos, int xSpeed, int ySpeed, int xAcceleration, int yAcceleration, int xSpeedMax, int ySpeedMax, String image, boolean friendly) {
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
		this.setLife(3);
		this.setMaxLife(10);
		
		
		Shape test = this.getShapeByImage(this.getImageName());
		this.setShape(test);
		this.getShape().setLocation(this.getXPos(), this.getYPos());
	}

	/** Getter life */
	public int getLife() {
		return this.life;
	}

	public int getMaxLife(){
		return this.maxLife;
	}

	/** Implémentation de onCollision */
	public void onCollision(GameObject o) throws SlickException {
		if(o instanceof Bonus){
			Bonus b = ((Bonus) o);
			if(b.getCurrentType() == bonusTypes.ARMAGGEDON){
				for(Double i = 0.0 ; i <= 360 ; i+=0.5){
					this.fireToAngle(i);
				}
			}
			if(b.getCurrentType() == bonusTypes.SPEEDUP){
				this.speedTimer = 5000;
			}
			if(b.getCurrentType() == bonusTypes.LIFEUP)
				this.setLife((this.getLife()+1 <= this.getMaxLife()) ? (this.getLife()+1) : this.getMaxLife());
			if(b.getCurrentType() == bonusTypes.POWERUP){
				this.bulletNumber = (this.bulletNumber + 1 > 3) ? 3 : (this.bulletNumber + 1);
				this.setTimerBulletNumber(45000);
			}
			GamePackage.getInstance().addRemove(o);
		}
	}

	/** Setter life */
	public void setLife(int life) {
		this.life = life;
	}

	public void setMaxLife(int life){
		this.maxLife = life;
	}
	
	/** retourne vrai si le Player peut tirer */
	public boolean canFire(){
		return (fireDelta <= 0);
	}
	
	/** Reset de Timer de tir */
	public void resetFireTimer(){
		this.fireDelta = 550 - (100 * this.bulletNumber);
	}
	
	/** Retire Delta a l'attribut fireDelta */
	public void updateFireDelta(int delta){
		this.fireDelta -= delta;
	}
	
	@Override
	public void update(int delta){
		//EU !!!!!!
	}

	/** Implémentation de update */
	public void update(GameContainer container, int delta) throws SlickException {
		Input input = container.getInput();
		int direction = -5;
		//On va déterminer la direction souhaitée par le joueur
		if(input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_Z))
			direction =  0;
		if(input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S))
			direction = (direction == -5 ) ? 4 : -5;
		if(input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))
			direction = (direction == -5) ? 2 : ((direction == 0) ? 1 : 3) ;
		if(input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_Q))
			direction = (direction == -5) ? 6 : ((direction == 2) ? -5 : ((direction == 0 || direction == 1) ? ((direction - 1 + 8) % 8) : (direction+1  % 8)));
		this.moveByDirection(direction, delta);
		if(this.isOutOfScreen()){
			moveByDirection((direction+4) % 8, delta);
		}
		
				
		this.getShape().setLocation(this.getXPos(), this.getYPos());
		
		this.timerBulletNumber -= delta;
		this.speedTimer -= delta;
		
		if (this.bulletNumber > 1 && this.timerBulletNumber <= 0){
			this.bulletNumber = 1;
		}

		if (this.speedTimer > 0 && !this.hasSpeed){
			this.setXSpeed((int) (this.getXSpeed() * 1.45));
			this.setYSpeed((int) (this.getYSpeed() * 1.45));
			this.hasSpeed = true;
		}else if(this.speedTimer < 0 && this.hasSpeed){
			this.setXSpeed(this.getXSpeed()*100/145);
			this.setYSpeed(this.getYSpeed()*100/145);
			this.hasSpeed = false;
		}
		
		this.updateFireDelta(delta);
		if((input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) || input.isKeyDown(Input.KEY_SPACE)) && this.canFire()){
			this.fire();
		}
		
	}
	
	public void fire(){
		int xSpawn = (int) (this.getXPos() + (this.getWidth() - (this.getWidth()) / 4));
		int ySpawn = (int) (this.getYPos() + (this.getHeight() - (this.getHeight()) / 4) /2 );		
		int id = GamePackage.getInstance().getPlayerArray().indexOf(this);
		GamePackage gp = GamePackage.getInstance();
		switch(this.bulletNumber){
		case 1:
			gp.addGameObject(new Bullet(xSpawn, ySpawn, this.getXSpeed() + 300, 0, this.getXAcceleration(), this.getYAcceleration(), this.getXSpeedMax(), this.getXSpeedMax(), true, id, 1));
			gp.getPlayerArray().get(id).resetFireTimer();
			break;
		case 2:
			gp.addGameObject(new Bullet(xSpawn, ySpawn, this.getXSpeed() + 300, 75, this.getXAcceleration(), this.getYAcceleration(), this.getXSpeedMax(), this.getXSpeedMax(), true, id, 1));
			gp.addGameObject(new Bullet(xSpawn, ySpawn, this.getXSpeed() + 300, -75, this.getXAcceleration(), this.getYAcceleration(), this.getXSpeedMax(), this.getXSpeedMax(), true, id, 1));
			gp.getPlayerArray().get(id).resetFireTimer();
			break;
		case 3:
			gp.addGameObject(new Bullet(xSpawn, ySpawn, this.getXSpeed() + 300, 0, this.getXAcceleration(), this.getYAcceleration(), this.getXSpeedMax(), this.getXSpeedMax(), true, id, 1));
			gp.addGameObject(new Bullet(xSpawn, ySpawn, this.getXSpeed() + 300, 75, this.getXAcceleration(), this.getYAcceleration(), this.getXSpeedMax(), this.getXSpeedMax(), true, id, 1));
			gp.addGameObject(new Bullet(xSpawn, ySpawn, this.getXSpeed() + 300, -75, this.getXAcceleration(), this.getYAcceleration(), this.getXSpeedMax(), this.getXSpeedMax(), true, id, 1));
			gp.getPlayerArray().get(id).resetFireTimer();
			break;
		}
	}
	

	/** Setter score */
	public long getScore() {
		return this.score;
	}
	
	/** Getter score */
	public void setScore(long score) {
		this.score = score;
	}

	public int getTimerBulletNumber() {
		return timerBulletNumber;
	}

	public void setTimerBulletNumber(int timerBulletNumber) {
		this.timerBulletNumber = timerBulletNumber;
	}
	
	@Override
	public String toString(){
		String res = new String();
		res += "Life : " + this.getLife() + "\n";
		res += "xPos : " + this.getXPos() + "\n";
		res += "yPos : " + this.getYPos() + "\n";
		return res;
	}
	
	/** Methode d'update pour le multijoueur */
	public void updateMulti(Client client, GameContainer container, int delta, int indexp) {
		Input input = container.getInput();
		int direction = -5;
		//On va déterminer la direction souhaitée par le joueur
		if(input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_Z))
			direction =  0;
		if(input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S))
			direction = (direction == -5 ) ? 4 : -5;
		if(input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))
			direction = (direction == -5) ? 2 : ((direction == 0) ? 1 : 3) ;
		if(input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_Q))
			direction = (direction == -5) ? 6 : ((direction == 2) ? -5 : ((direction == 0 || direction == 1) ? ((direction - 1 + 8) % 8) : (direction+1  % 8)));
		this.moveByDirection(direction, delta);
		if(this.isOutOfScreen()){
			moveByDirection((direction+4) % 8, delta);
		}
		
				
		this.getShape().setLocation(this.getXPos(), this.getYPos());
		
		this.timerBulletNumber -= delta;
		
		if (this.bulletNumber > 1 && this.timerBulletNumber <= 0){
			this.bulletNumber = 1;
		}
		
		boolean fire = false;
		this.updateFireDelta(delta);
		if((input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) || input.isKeyDown(Input.KEY_SPACE)) && this.canFire()){
			fire = true;
			this.resetFireTimer();
		}
		
		PlayerInfo pi = new PlayerInfo();
		pi.id = indexp;
		pi.fire = fire;
		pi.x = (int) (this.getXPos());
		pi.y = (int) (this.getYPos());
		client.sendTCP(pi);
	}
	
	/** Fonction qui permet de tirer dans un angle */
	public void fireToAngle(double angle){
		angle = angle %360;
		float xVect = (float) Math.cos(Math.toRadians(angle));
		float yVect = (float) Math.sin(Math.toRadians(angle));	
		int id = GamePackage.getInstance().getPlayerArray().indexOf(this);
		GamePackage gp = GamePackage.getInstance();
		int xSpawn =(int) (this.getXPos() + (this.getWidth() - this.getWidth() / 4) / 2);
		int ySpawn =(int) (this.getYPos() + (this.getHeight() - this.getHeight() / 4) /2 );
		double yspeed = 0;
		double xspeed = 0;
			yspeed = -175*yVect*2;
			xspeed = -175*xVect*2;
		gp.addGameObject(new Bullet(xSpawn, ySpawn, (int)xspeed, (int)yspeed, 100,100,300,300, true, id, 1));
	}
}
