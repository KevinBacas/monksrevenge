package states;

import java.util.ArrayList;

import monksrevenge.MonksRevengeGame;
import objects.Background;
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

public class Campain1State extends BasicGameState {

	private int stateId;
	private Background bg;
	private GamePackage gp;
	private int spawnCounter=0;
	
	//Array des Enemy
	private ArrayList<Enemy> enemyListArray = new ArrayList<Enemy>();
	//Array en parallèle qui stock le temps entre 2 spawn pour chaque Enemy
	private ArrayList<Integer> integerListArray = new ArrayList<Integer>();
	
	private Sound music;
	
	public Campain1State(int id){
		this.stateId = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		
		this.gp = GamePackage.getInstance();
		this.bg = new Background(this.gp.getResourceLoader().getImage("img/bg.jpg"));
		
		//Init du GamePackage
		this.gp.init();
		
		//On charge la musique
		this.music = GamePackage.getInstance().getResourceLoader().getSound("sound/Niveau1.ogg");
		
		//Ajout du joueur
		this.gp.addPlayer(new Player(50, MonksRevengeGame.appInstance.getHeight()/2, 250, 250, 100, 100, 300, 300, this.gp.getResourceLoader().getImage("img/Hammerheadred.png"), true));

		Image image = this.gp.getResourceLoader().getImage("img/vaisseau2.png");
		Enemy temp = null;
		
		//On construit la partie
		temp = new Enemy(MonksRevengeGame.appInstance.getWidth(), MonksRevengeGame.appInstance.getHeight(), (int)-(150+20), (int)-(150+20), 100, 105, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 800);
		temp = new Enemy(MonksRevengeGame.appInstance.getWidth(), 0, (int)-(150+20), (int)(150+20), 100, 105, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 800);
		temp = new Enemy(MonksRevengeGame.appInstance.getWidth(), MonksRevengeGame.appInstance.getHeight(), (int)-(150+20), (int)-(150+20), 100, 105, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 800);
		temp = new Enemy(MonksRevengeGame.appInstance.getWidth(), 0, (int)-(150+20), (int)(150+20), 100, 105, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 800);
		temp = new Enemy(MonksRevengeGame.appInstance.getWidth(), MonksRevengeGame.appInstance.getHeight(), (int)-(150+20), (int)-(150+20), 100, 105, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 800);
		temp = new Enemy(MonksRevengeGame.appInstance.getWidth(), 0, (int)-(150+20), (int)(150+20), 100, 105, 300, 300, image, false, 1, false, 1000);
		this.addEnemyTimer(temp, 800);
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		this.bg.render(container, sbg, g);
		this.gp.render(container, sbg, g);
		g.drawString("Score : " + this.gp.getPlayerArray().get(0).getScore(), 9, 25);
		g.drawString("Life : " + this.gp.getPlayerArray().get(0).getLife(), 9 , 40);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		if(!this.music.playing()){
			this.music.play();
		}
		this.gp.setSpawnTimer(this.gp.getSpawnTimer() - delta);
		if(this.gp.getSpawnTimer() < 0 && this.spawnCounter < this.enemyListArray.size()){
			this.gp.addEnemy(this.enemyListArray.get(this.spawnCounter));
			this.gp.setSpawnTimer(this.integerListArray.get(this.spawnCounter));
			this.spawnCounter++;
		}
		//Update Background
		this.bg.update(container, sbg, delta);
		
		//Update GamePackage
		this.gp.update(container, sbg, delta);
		
		if(this.isGameFinished()){
			((FinishedGameState) sbg.getState(MonksRevengeGame.FINISHEDGAMEMODSTATE)).setScore(GamePackage.getInstance().getPlayerArray().get(0).getScore());
			this.music.stop();
			sbg.enterState(MonksRevengeGame.FINISHEDGAMEMODSTATE);
		}
	}

	@Override
	public int getID() {
		return this.stateId;
	}
	
	private void addEnemyTimer(Enemy en, Integer timer){
		this.enemyListArray.add(en);
		this.integerListArray.add(timer);
	}
	
	private boolean isGameFinished(){
		return ((this.spawnCounter == this.enemyListArray.size() && this.gp.getEnemyArray().size() == 0) || this.gp.isPlayerDead(0));
	}

}
