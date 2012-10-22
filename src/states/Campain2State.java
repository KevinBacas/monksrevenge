package states;

import java.util.ArrayList;

import monksrevenge.FakeGameContainer;
import monksrevenge.MonksRevengeGame;
import objects.Background;
import objects.Bonus;
import objects.Enemy;
import objects.EnemyIA;
import objects.EnemyIA.types;
import objects.GamePackage;
import objects.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Campain2State extends BasicGameState {

	private int stateId;
	private Background bg;
	private GamePackage gp;
	private int spawnCounter=0;
	private boolean onBoss=false;
	
	//Array des Enemy
	private ArrayList<Enemy> enemyListArray = new ArrayList<Enemy>();
	//Array en parallèle qui stock le temps entre 2 spawn pour chaque Enemy
	private ArrayList<Integer> integerListArray = new ArrayList<Integer>();
	
	private Sound music;
	private EnemyIA boss;
	
	public Campain2State(int id){
		this.stateId = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		this.spawnCounter = 0;
		this.boss = null;
		this.onBoss = false;
		
		this.enemyListArray = new ArrayList<Enemy>();
		this.integerListArray = new ArrayList<Integer>();
		
		this.gp = GamePackage.getInstance();
		this.bg = new Background(this.gp.getResourceLoader().getImage("img/bg.jpg"));
		
		//Init du GamePackage
		this.gp.init(false);
		
		//On charge la musique
		this.music = GamePackage.getInstance().getResourceLoader().getSound("sound/Niveau1.ogg");
		
		//Ajout du joueur
		this.gp.addGameObject(new Player(50, FakeGameContainer.getInstance().getHeight()/2, 250, 250, 100, 100, 300, 300, "img/Hammerheadred.png", true));

		String image = "img/vaisseau2.png";
		Enemy temp = null;
		
		//On construit la partie
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), FakeGameContainer.getInstance().getHeight()/2, -220, 0, 100, 100, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 1500);
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), 0, -220, 220/2, 100, 100, 300, 300, image, false, 1, false, 1000); 
		this.addEnemyTimer(temp, 0);
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), FakeGameContainer.getInstance().getHeight(), 220, -220/2, 100, 100, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 1500);
		temp = new EnemyIA(types.RAFALEVISEE);
		this.addEnemyTimer(temp, 1500);
		temp = new Enemy(0, 0, 220, 220, 100, 105, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 100);
		temp = new Enemy(0, FakeGameContainer.getInstance().getHeight(), 220, -220, 100, 105, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 1500);
		temp = new EnemyIA(types.KAMIKAZE);
		this.addEnemyTimer(temp, 500);
		temp = new EnemyIA(types.KAMIKAZE);
		this.addEnemyTimer(temp, 1500);
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), (int)((Math.random() * FakeGameContainer.getInstance().getHeight())/2), -220, 0, 100, 100, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 0);
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), FakeGameContainer.getInstance().getHeight()/2, -220, 0, 100, 100, 300, 300, image, false, 1, false, 1000); 
		this.addEnemyTimer(temp, 1500);
		
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), FakeGameContainer.getInstance().getHeight()/2, -220, 0, 100, 100, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 1500);
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), 0, -220, 220/2, 100, 100, 300, 300, image, false, 1, false, 1000); 
		this.addEnemyTimer(temp, 0);
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), FakeGameContainer.getInstance().getHeight(), 220, -220/2, 100, 100, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 1500);
		temp = new EnemyIA(types.RAFALEVISEE);
		this.addEnemyTimer(temp, 1500);
		temp = new Enemy(0, 0, 220, 220, 100, 105, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 100);
		temp = new Enemy(0, FakeGameContainer.getInstance().getHeight(), 220, -220, 100, 105, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 1500);
		temp = new EnemyIA(types.KAMIKAZE);
		this.addEnemyTimer(temp, 500);
		temp = new EnemyIA(types.KAMIKAZE);
		this.addEnemyTimer(temp, 1500);
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), (int)((Math.random() * FakeGameContainer.getInstance().getHeight())/2), -220, 0, 100, 100, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 0);
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), FakeGameContainer.getInstance().getHeight()/2, -220, 0, 100, 100, 300, 300, image, false, 1, false, 1000); 
		this.addEnemyTimer(temp, 1500);
		
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), FakeGameContainer.getInstance().getHeight()/2, -220, 0, 100, 100, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 1500);
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), 0, -220, 220/2, 100, 100, 300, 300, image, false, 1, false, 1000); 
		this.addEnemyTimer(temp, 0);
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), FakeGameContainer.getInstance().getHeight(), 220, -220/2, 100, 100, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 1500);
		temp = new EnemyIA(types.RAFALEVISEE);
		this.addEnemyTimer(temp, 1500);
		temp = new Enemy(0, 0, 220, 220, 100, 105, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 100);
		temp = new Enemy(0, FakeGameContainer.getInstance().getHeight(), 220, -220, 100, 105, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 1500);
		temp = new EnemyIA(types.KAMIKAZE);
		this.addEnemyTimer(temp, 500);
		temp = new EnemyIA(types.KAMIKAZE);
		this.addEnemyTimer(temp, 1500);
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), (int)((Math.random() * FakeGameContainer.getInstance().getHeight())/2), -220, 0, 100, 100, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 0);
		temp = new Enemy(FakeGameContainer.getInstance().getWidth(), FakeGameContainer.getInstance().getHeight()/2, -220, 0, 100, 100, 300, 300, image, false, 1, false, 1000); 
		this.addEnemyTimer(temp, 1500);
		
		temp = new EnemyIA(types.RAFALEVISEE);
		this.addEnemyTimer(temp, 0);
		temp = new EnemyIA(types.RAFALEVISEE);
		this.addEnemyTimer(temp, 7000);
		
		temp = new EnemyIA(types.BOSS1);
		this.addEnemyTimer(temp, 1500);
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		this.bg.render(container, sbg, g);
		this.gp.render(container, sbg, g);
		g.drawString("Score : " + this.gp.getPlayerArray().get(0).getScore(), 9, 25);
		g.drawString("Life : " + this.gp.getPlayerArray().get(0).getLife(), 9 , 40);
		if(this.onBoss){
			int numberOfLines = this.boss.getLife() * 5;
			this.gp.getResourceLoader().getImage("img/cadre.png").draw(FakeGameContainer.getInstance().getWidth() /2, 50);
			int i = 2;
			Image line = this.gp.getResourceLoader().getImage("img/barre.png");
			while(i <= numberOfLines){
				line.draw(FakeGameContainer.getInstance().getWidth() /2 + 2 + i, 52);
				i++;
			}
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		if(!this.music.playing()){
			this.music.play();
		}
		this.gp.setSpawnTimer(this.gp.getSpawnTimer() - delta);
		if(this.gp.getSpawnTimer() < 0 && this.spawnCounter < this.enemyListArray.size()){
			if(this.enemyListArray.get(this.spawnCounter) instanceof EnemyIA){
				if(this.enemyListArray.get(this.spawnCounter).isBoss()){
					this.onBoss = true;
					this.boss = (EnemyIA) this.enemyListArray.get(this.spawnCounter);
				}
				this.gp.addGameObject((EnemyIA)this.enemyListArray.get(this.spawnCounter));
				if(this.spawnCounter % 20 == 0){
					this.gp.generateRandomBonus();
				}
			}else{
				this.gp.addGameObject(new Enemy((Enemy)this.enemyListArray.get(this.spawnCounter)));
			}
			this.gp.setSpawnTimer(this.integerListArray.get(this.spawnCounter));
			this.spawnCounter++;
		}
		//Update Background
		this.bg.update(container, sbg, delta);
		
		//Update Player
	   	 for(Player player : GamePackage.getInstance().getPlayerArray()){
			 player.update(container, delta);
			 for(Bonus bonus : GamePackage.getInstance().getBonusLifeArray()){
				 player.collide(bonus);
			 }
		 }
		
		//Update GamePackage
		this.gp.update(delta);
		
		if(this.isGameFinished()){
			((FinishedGameState) sbg.getState(MonksRevengeGame.FINISHEDGAMEMODSTATE)).setScore((int) GamePackage.getInstance().getPlayerArray().get(0).getScore());
			this.music.stop();
			sbg.enterState(MonksRevengeGame.FINISHEDGAMEMODSTATE);
		}
	}

	@Override
	public int getID() {
		return this.stateId;
	}
	
	private void addEnemyTimer(Enemy en, Integer timer){
		if(en instanceof EnemyIA){
			this.enemyListArray.add(new EnemyIA((EnemyIA)en));
		}else{
			this.enemyListArray.add(new Enemy(en));
		}
		this.integerListArray.add(timer);
	}
	
	private boolean isGameFinished(){
		System.out.println("mort : " + this.gp.isPlayerDead(0) + " " + this.gp.getPlayerArray().get(0).getLife());
		return ((this.spawnCounter == this.enemyListArray.size() && this.gp.getEnemyArray().size() == 0) || this.gp.isPlayerDead(0));
	}

}
