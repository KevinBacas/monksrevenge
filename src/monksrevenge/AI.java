package monksrevenge;

import java.util.ArrayList;
import java.util.Random;

import objects.Enemy;
import objects.EnemyIA;
import objects.EnemyIA.types;
import objects.GamePackage;

import org.newdawn.slick.SlickException;

public class AI {
	
	/*
	 * Difficultés : 1 easy, 2 medium, 3 Hardcore EU, 10 : Armageddon Doomsday
	 */
	
	private GamePackage gp;
	
	private ArrayList<Enemy> enemyArray = new ArrayList<Enemy>() ;
	
	public AI (int difficulte) throws SlickException{	
		
		this.gp = GamePackage.getInstance();
		
		String image = "img/vaisseau2.png";	
		// n°0 ligne verticale a 1/4 des x
		this.enemyArray.add(new Enemy(FakeGameContainer.getInstance().getWidth(), (int)((Math.random() * FakeGameContainer.getInstance().getHeight())/2), (int)-(180+20*difficulte), 0, 100, 100, 300, 300, image, false, 1, false, 1000)); 
//		// N°1 ligne verticale a 1/2 des x
		this.enemyArray.add(new Enemy(FakeGameContainer.getInstance().getWidth(), FakeGameContainer.getInstance().getHeight()/2, (int)-(180+20*difficulte), 0, 100, 100, 300, 300, image, false, 1, false, 1000)); 
		
		image = "img/vaisseau3.png";	
//		// n°2 ligne verticale a 3/4 des x
		this.enemyArray.add(new Enemy(FakeGameContainer.getInstance().getWidth(), FakeGameContainer.getInstance().getHeight()*3/4, (int)-(180+20*difficulte), 0, 100, 100, 300, 300, image, false, 1, false, 1000)); 
//		// n°3 diagonale de (0,0) a 1/2 des x
		this.enemyArray.add(new Enemy(FakeGameContainer.getInstance().getWidth(), 0, (int)-(180+20*difficulte), (int)(180+20*difficulte)/2, 100, 100, 300, 300, image, false, 1, false, 1000)); 
		image = "img/vaisseau4.png";	
//		// n°4 diagonale de (max x, 0) a 1/2 des x
		this.enemyArray.add(new Enemy(FakeGameContainer.getInstance().getWidth(), FakeGameContainer.getInstance().getHeight(), (int)-(180+20*difficulte), (int)-(180+20*difficulte)/2, 100, 100, 300, 300, image, false, 1, false, 1000));
//		// n°5 courbe en partant de (0,0)
		this.enemyArray.add(new Enemy(FakeGameContainer.getInstance().getWidth(), 0, (int)-(180+20*difficulte), (int)(180+20*difficulte), 100, 105, 300, 300, image, false, 1, false, 1000));
		image = "img/vaisseau6.png";	
//		// n°6 courbe en partant de (max x, 0)
		this.enemyArray.add(new Enemy(FakeGameContainer.getInstance().getWidth(), FakeGameContainer.getInstance().getHeight(), (int)-(180+20*difficulte), (int)-(180+20*difficulte), 100, 105, 300, 300, image, false, 1, false, 1000)); 
//		// n°7 arrive derriere le joueur a (0, max y)
		this.enemyArray.add(new Enemy(0, 0, (int)(180+20*difficulte), (int)(180+20*difficulte), 100, 105, 300, 300, image, false, 1, false, 1000));
		
		image = "img/vaisseau7.png";	
//		// n°8 arrive derriere le joueur a (max x, max y)
		this.enemyArray.add(new Enemy(0, FakeGameContainer.getInstance().getHeight(), (int)(180+20*difficulte), (int)-(180+20*difficulte), 100, 105, 300, 300, image, false, 1, false, 1000));
		// n°9 EnemyIA
		this.enemyArray.add(new EnemyIA(types.RAFALEVISEE));
		// n°10 EnemyIA
		this.enemyArray.add(new EnemyIA(types.KAMIKAZE));
		// n°11 EnemyIA
		this.enemyArray.add(new EnemyIA(types.VISEJOUEUR));
	}
	
	public void getEnemy(int level){
		//  level = 1: 1 seul enemy, 2: 2 enemy, 3: 5 enemy

		
		Random rand = new Random();
		int i=0;
		
		switch(level){
		
			case 1:
				i = rand.nextInt(this.enemyArray.size());
				if (i==9 || i==10 || i==11){
					this.gp.addGameObject(new EnemyIA((EnemyIA)this.enemyArray.get(i)));
				}
				else{
				this.gp.addGameObject(new Enemy(this.enemyArray.get(i)));
				}
			break;
		
			case 2:
				i=rand.nextInt(7);
				switch (i){
				case 0: 
					this.gp.addGameObject(new Enemy(this.enemyArray.get(0)));
					this.gp.addGameObject(new Enemy(this.enemyArray.get(2)));
					break;
				case 1: 
					this.gp.addGameObject(new Enemy(this.enemyArray.get(3)));
					this.gp.addGameObject(new Enemy(this.enemyArray.get(4)));
					 
					break;
				case 2: 
					this.gp.addGameObject(new Enemy(this.enemyArray.get(5)));
					this.gp.addGameObject(new Enemy(this.enemyArray.get(6)));
					break;
				case 3: 
					this.gp.addGameObject(new Enemy(this.enemyArray.get(7)));
					this.gp.addGameObject(new Enemy(this.enemyArray.get(8)));
					break;
				case 4:
					this.gp.addGameObject(new EnemyIA((EnemyIA)this.enemyArray.get(9)));
					this.gp.addGameObject(new EnemyIA((EnemyIA)this.enemyArray.get(10)));
					break;
				case 5:
					this.gp.addGameObject(new EnemyIA((EnemyIA)this.enemyArray.get(10)));
					this.gp.addGameObject(new EnemyIA((EnemyIA)this.enemyArray.get(11)));
					break;
				case 6:
					this.gp.addGameObject(new EnemyIA((EnemyIA)this.enemyArray.get(9)));
					this.gp.addGameObject(new EnemyIA((EnemyIA)this.enemyArray.get(11)));
					
					break;
				}
			break;
			
			case 3:
				i= rand.nextInt(2);
				switch(i){
					case 0:
						this.gp.addGameObject(new Enemy(this.enemyArray.get(0)));
						this.gp.addGameObject(new Enemy(this.enemyArray.get(1)));
						this.gp.addGameObject(new Enemy(this.enemyArray.get(2)));
						this.gp.addGameObject(new Enemy(this.enemyArray.get(3)));
						this.gp.addGameObject(new Enemy(this.enemyArray.get(4)));
					break;
					case 1:
						this.gp.addGameObject(new Enemy(this.enemyArray.get(0)));
						this.gp.addGameObject(new Enemy(this.enemyArray.get(1)));
						this.gp.addGameObject(new Enemy(this.enemyArray.get(2)));
						this.gp.addGameObject(new Enemy(this.enemyArray.get(7)));
						this.gp.addGameObject(new Enemy(this.enemyArray.get(8)));
					break;
					case 2:
					break;
				}
			break;
		}
	}
}
