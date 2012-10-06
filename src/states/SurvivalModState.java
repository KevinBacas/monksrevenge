package states;

import monksrevenge.AI;
import monksrevenge.MonksRevengeGame;
import objects.Background;
import objects.Bonus;
import objects.Bonus.bonusTypes;
import objects.GamePackage;
import objects.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class SurvivalModState extends BasicGameState{

	private int stateId;
	private Background bg;
	private GamePackage gp;
	private AI ia;
	private int level;
	private int spawnCounter=0;
	
	private Sound music;
	
	public SurvivalModState(int id){
		this.stateId = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		this.gp = GamePackage.getInstance();
		this.bg = new Background(this.gp.getResourceLoader().getImage("img/bg.jpg"));
		this.ia = new AI(3);
		this.level = 1;
		
		//Init du GamePackage
		this.gp.init();
		
		//On charge la musique
		this.music = GamePackage.getInstance().getResourceLoader().getSound("sound/Boss1.ogg");
		
		//Ajout du joueur
		this.gp.addPlayer(new Player(50, MonksRevengeGame.appInstance.getHeight()/2, 250, 250, 100, 100, 300, 300, this.gp.getResourceLoader().getImage("img/Hammerheadred.png"), true));
	}

	public void render(GameContainer container, StateBasedGame sgb, Graphics g)
			throws SlickException {
		this.bg.render(container, sgb, g);
		this.gp.render(container, sgb, g);
		g.drawString("Score : " + this.gp.getPlayerArray().get(0).getScore(), 9, 25);
		g.drawString("Life : " + this.gp.getPlayerArray().get(0).getLife(), 9 , 40);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		if(!this.music.playing()){
			this.music.play();
		}
		//Update Background
		this.bg.update(container, sbg, delta);
		
		//Update GamePackage
		this.gp.update(container, sbg, delta);
		this.gp.setSpawnTimer(this.gp.getSpawnTimer() - delta);
		if (this.gp.getElapsedTime() / (this.level*5000) > 1.0){
			this.level += 1;
			this.gp.addBonus(new Bonus(bonusTypes.LIFEUP));
			this.gp.addBonus(new Bonus(bonusTypes.POWERUP));
		}
		if(this.gp.getSpawnTimer()<0){
			this.spawnCounter++;
			int typeOfWave = (this.spawnCounter % 10 == 0) ? 3 : ((this.spawnCounter % 3 == 0) ? 2 : 1);
			this.ia.getEnemy(typeOfWave);
			this.gp.setSpawnTimer(5000/this.level);
		}
		if(this.gp.isPlayerDead(0)){
			((FinishedGameState) sbg.getState(MonksRevengeGame.FINISHEDGAMEMODSTATE)).setScore(GamePackage.getInstance().getPlayerArray().get(0).getScore());
			this.music.stop();
			sbg.enterState(MonksRevengeGame.FINISHEDGAMEMODSTATE);
		}
	}

	@Override
	public int getID() {
		return this.stateId;
	}
	
}
