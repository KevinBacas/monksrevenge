package objects;

import java.util.Random;

import monksrevenge.MonksRevengeGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class EnemyIA extends Enemy {
	
    private types currentType = null;
    private boolean phase = false;
    private int time;
	
	public EnemyIA(types currentType){
		this.setXPos(MonksRevengeGame.appInstance.getWidth());
		this.setXSpeed(-200);
		this.setYSpeed(0);
		this.setXAcceleration(100);
		this.setYAcceleration(100);
		this.setXSpeedMax(300);
		this.setYSpeedMax(300);
		this.time=0;
		this.setFriendly(false);
		this.setLife(1);
		this.setBoss(false);
		this.setShootTimer(2000);
		this.setMaxShootTimer(2000);
		this.currentType = currentType;

		switch(currentType){
			case RAFALEVISEE:
				this.setImage(GamePackage.getInstance().getResourceLoader().getImage("img/vaisseau2.png"));
				break;
			case VISEJOUEUR:
				this.setLife(2);
				this.setImage(GamePackage.getInstance().getResourceLoader().getImage("img/vaisseau2.png"));
				break;
			case KAMIKAZE:
				this.setImage(GamePackage.getInstance().getResourceLoader().getImage("img/vaisseau2.png"));
				break;
			case BOSS1:
				this.setXPos(900);
				this.setXSpeed(0);
				this.setBoss(true);
				this.setLife(50);
				this.setImage(GamePackage.getInstance().getResourceLoader().getImage("img/vaisseau2.png"));
				break;
			default:
				this.setImage(GamePackage.getInstance().getResourceLoader().getImage("img/vaisseau2.png"));
				break;
				
		}
		this.setYPos((int)(Math.random() * (MonksRevengeGame.appInstance.getHeight()-(this.getHeigth()))));
		this.setShape(this.getShapeByImage(this.getImage()));
		this.getShape().setLocation(this.getXPos(), this.getYPos());

		
	}
	
	public EnemyIA(EnemyIA e){
		this(e.getCurrentType());
	}

    public static enum types {
        RAFALEVISEE, VISEJOUEUR, KAMIKAZE , BOSS1
    }
 


	public types getCurrentType() {
		return currentType;
	}

	public void setCurrentType(types currentType) {
		this.currentType = currentType;
	}
	
	public int getTime(){
		return this.time;
	}
	
	public void setTime(int time){
		this.time = time;
	}
	
	public boolean getPhase(){
		return this.phase;
	}
	
	public void setPhase(boolean phase){
		this.phase= phase;
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		Random rand = new Random();
		int numPlayer = rand.nextInt(GamePackage.getInstance().getPlayerArray().size());
		int playerX = GamePackage.getInstance().getPlayerArray().get(numPlayer).getXPos();
		int playerY = GamePackage.getInstance().getPlayerArray().get(numPlayer).getYPos();
		int distanceX = (playerX - this.getXPos());
		int distanceY = (playerY - this.getYPos());
		
		// a verifier
		double angle=90;
		if (distanceX==0){
			if(distanceY >0)
				angle = -90;
		}
		else if(distanceY == 0){
				if (distanceX>0)
					angle = 180;
				else angle = 0;
			 }
		else angle = Math.toDegrees((Math.atan(distanceY/distanceX)));
		
		switch(this.getCurrentType()){
			
			case RAFALEVISEE:
				System.out.println(angle);
				this.updateShootTimer(delta);
				if (this.getShootTimer()==0){
					this.resetShootTimer();
					this.fireToAngle((int)angle-1);
					this.fireToAngle((int)angle);
					this.fireToAngle((int)angle+1);
				}
				break;
			case VISEJOUEUR:
				this.updateShootTimer(delta);
				if (this.getShootTimer()==0){
					this.resetShootTimer();
					this.fireToAngle((int)angle);
				}
				
				break;
			case KAMIKAZE:
				int xspeed=-200;
				int yspeed = 0;
				if (distanceX == 0){
					if (distanceY <0)
						yspeed = -this.getYSpeedMax();
					else yspeed = this.getYSpeedMax();
				}
				else
					yspeed=distanceY*xspeed/distanceX;
				this.setXSpeed(xspeed<-this.getXSpeedMax()|| xspeed>this.getXSpeedMax() ? xspeed : -this.getXSpeedMax());
				this.setYSpeed(yspeed);
				break;
			case BOSS1:
				time =this.time+delta;
				// mouvement en Y
				if (this.getYPos()>MonksRevengeGame.appInstance.getScreenHeight()-200)
					this.setYSpeed(-200);
				else if (this.getYPos()<100)
					this.setYSpeed(200);
				
				if (time <50000){
					// phase 1: 3 tirs visés + 4 tirs en diagonale
					System.out.println(time);
					System.out.println(phase);
					if (this.phase== false){
						this.setMaxShootTimer(1500);
						this.updateShootTimer(delta);
						if (this.getShootTimer()==0){
							this.resetShootTimer();
							this.fireToAngle((int)angle-5);
							this.fireToAngle((int)angle);
							this.fireToAngle((int)angle+5);
							//GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos()+50, -250, -100,100,101,500,500, false, -1, 1));
							//GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos()-50, -250, 100,100,101,500,500, false, -1, 1));
							//GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos()+50, -250, -150,100,101,500,500, false, -1, 1));
							//GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos()-50, -250, 150,100,101,500,500, false, -1, 1));
						}
						//if (this.time== 5000 % 10000)
							//this.phase = true;
						
					}
					// phase 2: 5 tirs larges et plus rapides
					else{
						this.setMaxShootTimer(1000);
						this.updateShootTimer(delta);
						if (this.getShootTimer()==0){
							this.resetShootTimer();
							GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, -10,100,100,500,500, false, -1, 1));
							GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, -5,100,100,500,500, false, -1, 1));
							GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, 0,100,100,500,500, false, -1, 1));
							GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, 5,100,100,500,500, false, -1, 1));
							GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, 10,100,100,500,500, false, -1, 1));
						}
						if (this.time == 10000)
							this.phase = false;
					}
				}
				else{
					this.setMaxShootTimer(700);
					this.updateShootTimer(delta);
					if (this.getShootTimer()==0){
						this.resetShootTimer();
						this.fireToAngle((int)angle-5);
						this.fireToAngle((int)angle);
						this.fireToAngle((int)angle+5);
						GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, -150,100,100,500,500, false, -1, 1));
						GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, -100,100,100,500,500, false, -1, 1));
						GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, 0,100,100,500,500, false, -1, 1));
						GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, 5,100,100,500,500, false, -1, 1));
						GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, 10,100,100,500,500, false, -1, 1));
						GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, -10,100,100,500,500, false, -1, 1));
						GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, -5,100,100,500,500, false, -1, 1));
						GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, 0,100,100,500,500, false, -1, 1));
						GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, 100,100,100,500,500, false, -1, 1));
						GamePackage.getInstance().addBullet(new Bullet(this.getXPos(), this.getYPos(), -250, 150,100,100,500,500, false, -1, 1));
					}
				}
				
				break;
		}
		this.updateLocation(delta);
		this.getShape().setLocation(this.getXPos(), this.getYPos());
		//S'il sort de l'écran on le delete
		if(this.isOutOfRender()){
			GamePackage.getInstance().addRemove(this);
		}
	}
	
}
