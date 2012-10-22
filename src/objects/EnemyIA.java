package objects;

import java.util.Random;

import monksrevenge.FakeGameContainer;
import org.newdawn.slick.SlickException;

public class EnemyIA extends Enemy {
	
    private types currentType = null;
    private int phase = 0;
    private int time;
	
	public EnemyIA(types currentType){
		this.setXPos(FakeGameContainer.getInstance().getWidth()-50);
		this.setXSpeed(-250);
		this.setYSpeed(0);
		this.setXAcceleration(100);
		this.setYAcceleration(100);
		this.setXSpeedMax(250);
		this.setYSpeedMax(250);
		this.time=0;
		this.setFriendly(false);
		this.setLife(1);
		this.setBoss(false);
		this.setShootTimer(3000);
		this.setMaxShootTimer(3000);
		this.currentType = currentType;

		switch(this.getCurrentType()){
			case RAFALEVISEE:
				this.setImage("img/vaisseau1.png");
				break;
			case VISEJOUEUR:
				this.setXSpeed(-250);
				this.setLife(2);
				this.setImage("img/vaisseau5.png");
				break;
			case KAMIKAZE:
				this.setImage("img/vaisseau8.png");
				break;
			case BOSS1:
				this.setXPos((int) (FakeGameContainer.getInstance().getWidth() * 0.80));
				this.setXSpeed(0);
				this.setYSpeed(250);
				this.setBoss(true);
				this.setLife(40);
				this.setImage("img/Boss1.png");
				break;
			case BOSS2:
				this.setXPos((int) (FakeGameContainer.getInstance().getWidth() * 0.80));
				this.setXSpeed(0);
				this.setYSpeed(250);
				this.setBoss(true);
				this.setLife(40);
				this.setImage("img/Boss2.png");
				break;
			case Coreen :
				this.setXPos((int) (FakeGameContainer.getInstance().getWidth() * 0.80));
				this.setXSpeed(0);
				this.setYSpeed(250);
				this.setBoss(true);
				this.setLife(80);
				this.setImage("img/boss3.png");

				break;
			default:
				this.setImage("img/vaisseau2.png");
				break;
		}
		this.setYPos((int)(Math.random() * (FakeGameContainer.getInstance().getHeight()-(50))));
		this.setShape(this.getShapeByImage(this.getImageName()));
		this.getShape().setLocation(this.getXPos(), this.getYPos());
	}
	
	@Override
	public int getHeight(){
		if(this.getCurrentType() == types.BOSS1){
			return (int) ((1610) * FakeGameContainer.getInstance().getScale());

		}
		else if(this.getCurrentType() == types.BOSS2){
			return (int) ((1258) * FakeGameContainer.getInstance().getScale());
		}
		else{
			return (int) ((1024) * FakeGameContainer.getInstance().getScale());
		}
	}
	
	@Override
	public int getWidth(){
		if(this.getCurrentType()  == types.BOSS1){
			return (int) ((1141) * FakeGameContainer.getInstance().getScale());

		}
		else if(this.getCurrentType() == types.BOSS2){
			return (int) ((1096) * FakeGameContainer.getInstance().getScale());
		}
		else{
			return (int) ((1024) * FakeGameContainer.getInstance().getScale());
		}
	}
	
	public EnemyIA(EnemyIA e){
		this(e.getCurrentType());
	}

    public static enum types {
        RAFALEVISEE, VISEJOUEUR, KAMIKAZE , BOSS1, BOSS2, Coreen
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
	
	public int getPhase(){
		return this.phase;
	}
	
	public void setPhase(int phase){
		this.phase= phase;
	}
	
	@Override
	public void update(int delta) throws SlickException {
		
		time=this.time+delta;
		Random rand = new Random();
		int numPlayer = rand.nextInt(GamePackage.getInstance().getPlayerArray().size());
		int playerX = GamePackage.getInstance().getPlayerArray().get(numPlayer).getXPos();
		int playerY = GamePackage.getInstance().getPlayerArray().get(numPlayer).getYPos();
		int distanceX = (playerX - this.getXPos());
		int distanceY = (playerY - this.getYPos());
		
		// a verifier
		double angle=0;
		if (distanceX==0){
			if(distanceY >0)
				angle = 90;
			else angle = -90;
		}
		else if(distanceY == 0){
				if (distanceX<0)
					angle = 0;
				else angle = 180;
			 }
		else if(distanceY<0)
			angle = Math.toDegrees(Math.acos(Math.abs(distanceX)/Math.sqrt(distanceY*distanceY+distanceX*distanceX)));
		else angle = Math.toDegrees(Math.acos(distanceX/Math.sqrt(distanceY*distanceY+distanceX*distanceX)))+180;
		
		switch(this.getCurrentType()){
			
			case RAFALEVISEE:
				this.updateShootTimer(delta);
				if (this.getShootTimer()==0){
					this.resetShootTimer();
					this.fireToAngle(angle+5);
					this.fireToAngle(angle);
					this.fireToAngle(angle-5);
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
				int xspeed=-250;
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
				// mouvement en Y
				if (this.getYPos()>FakeGameContainer.getInstance().getHeight()-200)
					this.setYSpeed(-250);
				else if (this.getYPos()<100)
					this.setYSpeed(250);
				
				
				//gestion des phases
				if (time <60000){
					if (this.phase== 0){
						this.setMaxShootTimer(1500);
						this.updateShootTimer(delta);
						if (this.getShootTimer()==0){
							this.resetShootTimer();
							GamePackage.getInstance().addGameObject(new Bullet(this.getXPos(), this.getYPos()+50, -250, -100,100,101,500,500, false, -1, 1));
							GamePackage.getInstance().addGameObject(new Bullet(this.getXPos(), this.getYPos()-50, -250, 100,100,101,500,500, false, -1, 1));
							GamePackage.getInstance().addGameObject(new Bullet(this.getXPos(), this.getYPos()+50, -250, -150,100,101,500,500, false, -1, 1));
							GamePackage.getInstance().addGameObject(new Bullet(this.getXPos(), this.getYPos()-50, -250, 150,100,101,500,500, false, -1, 1));
						}
						if (this.time== 10000 || this.time == 30000 || this.time== 50000)
							this.phase = 1;
						
					}
					// phase 2: 5 tirs larges et plus rapides
					else if(this.phase==1){ 
						this.setMaxShootTimer(1000);
						this.updateShootTimer(delta);
						if (this.getShootTimer()==0){
							this.resetShootTimer();
							this.fireToAngle(15);
							this.fireToAngle(0);
							this.fireToAngle(-15);
						}
						if (this.time == 20000 || this.time == 40000)
							this.phase = 0;
					}
				}
				else{
					this.setMaxShootTimer(700);
					this.updateShootTimer(delta);
					if (this.getShootTimer()==0){
						this.resetShootTimer();
						this.fireToAngle(angle+5);
						this.fireToAngle(angle);
						this.fireToAngle(angle-5);
						GamePackage.getInstance().addGameObject(new Bullet(this.getXPos(), this.getYPos()+50, -250, -100,100,101,500,500, false, -1, 1));
						GamePackage.getInstance().addGameObject(new Bullet(this.getXPos(), this.getYPos()-50, -250, 100,100,101,500,500, false, -1, 1));
						this.fireToAngle(15);
						this.fireToAngle(0);
						this.fireToAngle(-15);
						GamePackage.getInstance().addGameObject(new Bullet(this.getXPos(), this.getYPos()+50, -250, -150,100,101,500,500, false, -1, 1));
						GamePackage.getInstance().addGameObject(new Bullet(this.getXPos(), this.getYPos()-50, -250, 150,100,101,500,500, false, -1, 1));
					
					}
				}
				
				break;
				
			case BOSS2:
				
				//gestion du mouvement
				if (this.getYPos()>FakeGameContainer.getInstance().getHeight()-200)
					this.setYSpeed(-250);
				else if (this.getYPos()<100)
					this.setYSpeed(250);
				
				if (this.phase == 0){
					this.setMaxShootTimer(3000);
					this.updateShootTimer(delta);
					if (this.getShootTimer()==0){
						this.resetShootTimer();
						GamePackage.getInstance().addGameObject(new EnemyIA(types.KAMIKAZE));
					}
					if (this.time==10000 || this.time == 30000 || this.time == 50000 || this.time == 70000)
						this.phase = 1;
					
				}
				
				if (this.phase == 1){
					this.setMaxShootTimer(3000);
					this.updateShootTimer(delta);
					if (this.getShootTimer() == 0){
						this.resetShootTimer();
						this.fireToAngle(15);
						this.fireToAngle(0);
						this.fireToAngle(-15);
						
					}
					if (this.time == 20000 || this.time == 40000 || this.time == 60000 || this.time == 80000)
						this.phase= 0;
				}
				
				
				
				break;
				
			case Coreen:
			
					//phase 1
					if (this.phase== 0){
						
						//mouvement
						if (this.getYPos()>FakeGameContainer.getInstance().getHeight()-200)
							this.setYSpeed(-300);
						else if (this.getYPos()<100)
							this.setYSpeed(300);
						
						
						this.setMaxShootTimer(1500);
						this.updateShootTimer(delta);
						if (this.getShootTimer()==0){
							this.resetShootTimer();
							this.fireToAngle(angle+10);
							this.fireToAngle(angle);
							this.fireToAngle(angle-10);
							GamePackage.getInstance().addGameObject(new Bullet(this.getXPos(), this.getYPos()+50, -250, -100,100,101,500,500, false, -1, 1));
							GamePackage.getInstance().addGameObject(new Bullet(this.getXPos(), this.getYPos()-50, -250, 100,100,101,500,500, false, -1, 1));
							GamePackage.getInstance().addGameObject(new Bullet(this.getXPos(), this.getYPos()+50, -250, -150,100,101,500,500, false, -1, 1));
							GamePackage.getInstance().addGameObject(new Bullet(this.getXPos(), this.getYPos()-50, -250, 150,100,101,500,500, false, -1, 1));
						}
						if (this.getLife() <=60)
							this.phase = 1;
					}
						
						//phase 2
						else if(this.phase==1){ 
							
							// mouvement
							if (this.getYPos()<FakeGameContainer.getInstance().getHeight()/3)
								this.setYSpeed(200);
							else if (this.getYPos()>FakeGameContainer.getInstance().getHeight()-FakeGameContainer.getInstance().getHeight()/3)
								this.setYSpeed(-200);
							
								this.fireToAngle(30);
								this.fireToAngle(0);

								this.fireToAngle(-30);
							
							if (this.getLife() <=40)
								this.phase = 2;
						}
						
						//phase 3
						else if (this.phase == 2){
							
							//mouvement en X si au milieu en Y
							if (this.getYPos()>FakeGameContainer.getInstance().getHeight()/2-100 && this.getYPos()<FakeGameContainer.getInstance().getHeight()/2+100 ){
								if(this.getXPos()> FakeGameContainer.getInstance().getWidth()-FakeGameContainer.getInstance().getWidth()/3)	{
									this.setYSpeed(0);
									this.setXSpeed(-250);
									
								}else if (this.getXPos()< FakeGameContainer.getInstance().getWidth()/10){
									this.setYSpeed(0);
									this.setXSpeed(250);
								}
								else if ((this.getXPos()>(FakeGameContainer.getInstance().getWidth()-FakeGameContainer.getInstance().getWidth()/2)) && this.getXSpeed() ==250){
									this.phase=3;
								}
								this.fireToAngle(90);
								this.fireToAngle(0);
								this.fireToAngle(-90);
								this.setShootTimer(500);
								this.updateShootTimer(delta);
								if (this.getShootTimer()==0){
									this.resetShootTimer();
									this.fireToAngle(angle);
								}
							}else{
								// mouvement en Y
								if (this.getYPos()>FakeGameContainer.getInstance().getHeight()-100){
									this.setYSpeed(-250);
								}
								else if (this.getYPos()<100){
									this.setYSpeed(250);
								}
							
								
							}	
								
							
						}
						
						
						//phase 4
						else if (this.phase == 3){
							if (this.getYSpeed()==0)
								this.setYSpeed(200);
							if (this.getXPos() < FakeGameContainer.getInstance().getWidth()-FakeGameContainer.getInstance().getWidth()/4)
								this.setXSpeed(200);
							else if (this.getXPos()>= FakeGameContainer.getInstance().getWidth()-FakeGameContainer.getInstance().getWidth()/4)
								this.setXSpeed(0);
							
							if (this.getYPos()<FakeGameContainer.getInstance().getHeight()/3)
								this.setYSpeed(200);
							else if (this.getYPos()>FakeGameContainer.getInstance().getHeight()-FakeGameContainer.getInstance().getHeight()/3)
								this.setYSpeed(-200);
							
							this.fireToAngle(35);
							this.fireToAngle(20);
							this.fireToAngle(9);
							this.fireToAngle(-9);
							this.fireToAngle(-20);
							this.fireToAngle(-35);
			
							
						}
					if (this.getLife() == 35 || this.getLife() == 25 || this.getLife() == 15 || this.getLife()== 5){
						this.phase =2;
						this.setLife(this.getLife()-1);
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
