package objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

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
		
		private ResourceLoader resourceLoader = new ResourceLoader();
		
		//Timers
		private int elapsedTime;
		private int spawnTimer;
		private int bonusTimer;
		
		
		
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
	     public void init(){
	    	 this.playerArray = new ArrayList<Player>();
	    	 this.bulletArray = new ArrayList<Bullet>();
	    	 this.enemyArray = new ArrayList<Enemy>();
	    	 this.bonusArray = new ArrayList<Bonus>();
	    	 this.removeArray = new LinkedList<GameObject>();
	    	 this.elapsedTime = 0;
	    	 this.spawnTimer = 0;
	    	 this.bonusTimer = 0;
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
	 	public void update(GameContainer container, StateBasedGame sbg, int delta)
				throws SlickException {
	 		
	 		 this.addElapsedTime(delta);
	 		
	    	 for (Bonus bonus : this.bonusArray){
	    		 bonus.update(container, delta);
	    	 }
	 		
	    	 for(Player player : this.playerArray){
	    		 player.update(container, delta);
	    		 for(Bonus bonus : this.bonusArray){
	    			 player.collide(bonus);
	    		 }
	    	 }
	    	 
	    	 for(Enemy enemy : this.enemyArray){
	    		 if(enemy instanceof EnemyIA){
	    			 ((EnemyIA) enemy).update(container, delta);
	    		 }else{
	    			 enemy.update(container, delta);
	    		 }
	    		 for(Player player : this.playerArray){
	    			 enemy.collide(player);
	    		 }
	    	 }
	    	 
	    	 for(Bullet bullet : this.bulletArray){
	    		bullet.update(container, delta);
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
}
