package objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import objects.Bonus.bonusTypes;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public final class GamePackage {
		
		//Tableaux
		private ArrayList<Player> playerArray;
		private ArrayList<Bullet> bulletArray;
		private ArrayList<Enemy> enemyArray;
		private ArrayList<Bonus> bonusArray;
		private LinkedList<GameObject> removeArray;
		private LinkedList<GameObject> addArray;
		
		private ResourceLoader resourceLoader = null;
		
		//Timers
		private int elapsedTime;
		private int spawnTimer;
		private int bonusTimer;
		
		private boolean online;
		
		
		
		//Tres zimportant le volatile, on le dira a l'albinos. EU! 
	    private static volatile GamePackage instance = null;
	 
	     /**
	      * Constructeur de l'objet.
	      */
	     private GamePackage() {
	    	 super();
	     }
	 
	     public final static GamePackage getInstance() {

	         if (GamePackage.instance == null) {
	            synchronized(GamePackage.class) {
	              if (GamePackage.instance == null) {
	                GamePackage.instance = new GamePackage();
	              }
	            }
	         }
	         return GamePackage.instance;
	     }
	     
	     /* 
	      * Méthode qui initialize les tableaux
	      */
	     public void init(boolean online){
	    	 this.playerArray = new ArrayList<Player>();
	    	 this.bulletArray = new ArrayList<Bullet>();
	    	 this.enemyArray = new ArrayList<Enemy>();
	    	 this.bonusArray = new ArrayList<Bonus>();
	    	 this.removeArray = new LinkedList<GameObject>();
	    	 this.addArray = new LinkedList<GameObject>();
	    	 this.elapsedTime = 0;
	    	 this.spawnTimer = 0;
	    	 this.bonusTimer = 0;
	    	 this.online = online;
	    	 if(!this.online){
	    		 this.resourceLoader = new ResourceLoader();
	    	 }
	     }
	     
	     /*
	      * Permet de lancer un rendu sur tout les éléments de tout les tableaux
	      */
	     public void render(GameContainer container, StateBasedGame sgb, Graphics g)
	 			throws SlickException {

	    	 for(int i = 0 ; i<this.playerArray.size() ; i++){
	    		 this.playerArray.get(i).render(container, g);
	    	 }
	    	 for(int i = 0 ; i<this.bulletArray.size() ; i++){
	    		 this.bulletArray.get(i).render(container, g);
	    	 }
	    	 for(int i = 0 ; i<this.enemyArray.size() ; i++){
	    		 this.enemyArray.get(i).render(container, g);
	    	 }
	    	 for(int i = 0 ; i<this.bonusArray.size() ; i++){
	    		 this.bonusArray.get(i).render(container, g);
	    	 }
	 	}
	     
	    /*
	     * Update de tout les éléments de tout les tableau
	     */
	 	public void update(int delta)
				throws SlickException {
	 		
	 		 this.addElapsedTime(delta);
	 		
	    	 for (Bonus bonus : this.bonusArray){
	    		 bonus.update(delta);
	    	 }
	    	 
	    	 for(Enemy enemy : this.enemyArray){
	    		 if(enemy instanceof EnemyIA){
	    			 ((EnemyIA) enemy).update(delta);
	    		 }else{
	    			 enemy.update(delta);
	    		 }
	    		 for(Player player : this.playerArray){
	    			 enemy.collide(player);
	    		 }
	    	 }
	    	 
	    	 for(Bullet bullet : this.bulletArray){
	    		bullet.update(delta);
	    		if(bullet.getFriendly()){
		    		for(Enemy enemy : this.enemyArray){
		    			bullet.collide(enemy);
		    		}
	    		}else{
		    		for(Player player : this.playerArray){
		    			bullet.collide(player);
		    		}
	    		}
	    	 }
	    	 
	    	 if(this.removeArray.size()>0){
	    		 Iterator<GameObject> it = this.removeArray.iterator();
	    		 while(it.hasNext()){
	    			 GameObject go = it.next();
	    			 if(go instanceof Enemy)
	    				 GamePackage.getInstance().removeEnemy((Enemy) go);
	    			 if(go instanceof Player)
	    				 GamePackage.getInstance().removePlayer((Player) go);
	    			 if(go instanceof Bonus)
	    				 GamePackage.getInstance().removeBonus((Bonus) go);
	    			 if(go instanceof Bullet)
	    				 GamePackage.getInstance().removeBullet((Bullet) go);
	    		 }
	    		 this.removeArray.clear();
	    	 }
	    	 
	    	 if(this.addArray.size()>0){
	    		 Iterator<GameObject> it = this.addArray.iterator();
	    		 while(it.hasNext()){
	    			 GameObject go = it.next();
	    			 if(go instanceof EnemyIA)
	    				 GamePackage.getInstance().addEnemy((EnemyIA) go);
	    			 else if(go instanceof Enemy)
	    				 GamePackage.getInstance().addEnemy((Enemy) go);
	    			 if(go instanceof Player)
	    				 GamePackage.getInstance().addPlayer((Player) go);
	    			 if(go instanceof Bonus)
	    				 GamePackage.getInstance().addBonus((Bonus) go);
	    			 if(go instanceof Bullet)
	    				 GamePackage.getInstance().addBullet((Bullet) go);
	    		 }
	    		 this.addArray.clear();
	    	 }
	    	 
	 	}
	 	
		public boolean isPlayerDead(int i){
	 		return this.getPlayerArray().get(i).getLife() <= 0;
	 	}

		public ArrayList<Bonus> getBonusLifeArray() {
			return bonusArray;
		}

		public void addBonus(Bonus bonus) {
			this.bonusArray.add(bonus);
		}
		
		public void removeBonus(Bonus bonusLife){
			this.bonusArray.remove(bonusLife);
		}

		public ArrayList<Enemy> getEnemyArray() {
			return enemyArray;
		}

		public void addEnemy(Enemy enemy) {
			this.enemyArray.add(enemy);
		}
		
		public void removeEnemy(Enemy enemy){
			this.enemyArray.remove(enemy);
		}

		public ArrayList<Bullet> getBulletArray() {
			return bulletArray;
		}

		public void addBullet(Bullet bullet) {
			this.bulletArray.add(bullet);
		}
		
		public void removeBullet(Bullet bullet) {
			this.bulletArray.remove(bullet);
		}

		public ArrayList<Player> getPlayerArray() {
			return playerArray;
		}

		public void addPlayer(Player player) {
			this.playerArray.add(player);
		}
		
		public void removePlayer(Player player) {
			this.playerArray.remove(player);
		}

		public int getElapsedTime() {
			return elapsedTime;
		}

		public void addElapsedTime(int elapsedTime) {
			this.elapsedTime += elapsedTime;
		}

		public int getSpawnTimer() {
			return spawnTimer;
		}

		public void setSpawnTimer(int spawnTimer) {
			this.spawnTimer = spawnTimer;
		}

		public int getBonusTimer() {
			return bonusTimer;
		}

		public void setBonusTimer(int bonusTimer) {
			this.bonusTimer = bonusTimer;
		}
		
		public void addRemove(GameObject go){
			this.removeArray.add(go);
		}
		
		public ResourceLoader getResourceLoader(){
			return this.resourceLoader;
		}
		
		public void addGameObject(GameObject go){
			this.addArray.add(go);
		}
		
		public void generateRandomBonus(){
			Double d = Math.random();
			if(d < 0.20){
				this.addBonus(new Bonus(bonusTypes.ARMAGGEDON));
			}
			if(d >= 0.20 && d < 0.40){
				this.addBonus(new Bonus(bonusTypes.SPEEDUP));
			}
			if(d >= 0.40 && d < 0.65){
				this.addBonus(new Bonus(bonusTypes.POWERUP));
			}
			if(d >= 0.65){
				this.addBonus(new Bonus(bonusTypes.LIFEUP));
			}
		}
}
