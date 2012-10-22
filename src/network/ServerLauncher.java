package network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import monksrevenge.AI;
import monksrevenge.FakeGameContainer;
import objects.Bonus;
import objects.Bullet;
import objects.Enemy;
import objects.GamePackage;
import objects.Player;
import objects.Bonus.bonusTypes;

import org.newdawn.slick.SlickException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class ServerLauncher extends Thread{

	private static volatile ServerLauncher instance = null;

	private GamePackage gp = GamePackage.getInstance();
	private AI ia;
	private long delta;
	private Server server;

	private int level = 1;

	private int spawnCounter;


	public static int nbplayer;
	public static String[] players = new String[2];

	private ServerLauncher(){
		this.server = new Server(16384,16384);
		this.server.start();
		Kryo kryo = this.server.getKryo();
		kryo.register(newPlayerConnectionRequest.class);
		kryo.register(newPlayerConnectionResponse.class);
		kryo.register(gameStart.class);
		kryo.register(String[].class);
		kryo.register(RenderContainer.class);
		kryo.register(PlayerInfo.class);
		kryo.register(LinkedList.class);
		kryo.register(RenderPackage.class);
		Log.set(Log.LEVEL_DEBUG);

		try {
			this.server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.server.addListener(new Listener() {
			public void received (Connection connection, Object object){

				if (object instanceof newPlayerConnectionRequest) {

					newPlayerConnectionRequest request = (newPlayerConnectionRequest)object;
					
					newPlayerConnectionResponse response = new newPlayerConnectionResponse();
					response.ok = (ServerLauncher.nbplayer <= 1);
					ServerLauncher.nbplayer+=1;
					if (response.ok){
						response.msg = ServerLauncher.nbplayer;
						ServerLauncher.players[ServerLauncher.nbplayer - 1] = request.username;
						response.indexp = ServerLauncher.nbplayer-1;
					}
					else {
						response.msg = 0;
					}
					connection.sendTCP(response);
					if (!response.ok)
						connection.close();

					if (ServerLauncher.nbplayer == 2){
						try {
							ServerLauncher sl = ServerLauncher.getInstance();
							gameStart gs= new gameStart();
							sl.server.sendToAllTCP(gs);
							sl.init();
							//Ajout du joueur
							sl.gp.addPlayer(new Player(50, FakeGameContainer.getInstance().getHeight()/3, 250, 250, 100, 100, 300, 300, "img/Hammerheadblue.png", true));
							sl.gp.getPlayerArray().get(0).setLife(10);
							sl.gp.addPlayer(new Player(50, (FakeGameContainer.getInstance().getHeight()/3)*2, 250, 250, 100, 100, 300, 300, "img/Hammerheadred.png", true));
							sl.gp.getPlayerArray().get(1).setLife(10);
							sl.start();

						} catch (IOException e) {
							e.printStackTrace(); // Explose le pc du client !! Not Bad !!
						}
					}
				}
			}
		});
		
		this.server.addListener(new Listener() {
			public void received (Connection connection, Object object){
				if (object instanceof PlayerInfo){
					synchronized (ServerLauncher.class) {
						ServerLauncher sl;
						try {
							sl = ServerLauncher.getInstance();
							PlayerInfo pi = (PlayerInfo)object;
							synchronized (sl.gp) {
								sl.gp.getPlayerArray().get(pi.id).setXPos(pi.x);
								sl.gp.getPlayerArray().get(pi.id).setYPos(pi.y);
								sl.gp.getPlayerArray().get(pi.id).getShape().setLocation(pi.x, pi.y);
								if (pi.fire){
									sl.gp.getPlayerArray().get(pi.id).fire();
								}
							}

						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
	}

	private void init(){
		this.gp.init(true);
		try {
			this.ia = new AI(3);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public final static ServerLauncher getInstance() throws IOException {
		if (ServerLauncher.instance == null) {
			synchronized(ServerLauncher.class) {
				if (ServerLauncher.instance == null) {
					ServerLauncher.instance = new ServerLauncher();
				}
			}
		}
		return ServerLauncher.instance;
	}


	public void run(){
		while(!(this.gp.isPlayerDead(0) && this.gp.isPlayerDead(1))){
			long elapsedTime = System.currentTimeMillis() + 60;

			this.sendInfossurlesjoueurspourlesjoueursparlesjoueursmaispaspourGiltaire();

			while(!(System.currentTimeMillis() >= elapsedTime));

		}

	}


	private void sendInfossurlesjoueurspourlesjoueursparlesjoueursmaispaspourGiltaire() {

		try {
			this.gp.update(60);
			this.gp.setSpawnTimer(this.gp.getSpawnTimer() - 60);
			if (this.gp.getElapsedTime() / (this.level*5000) > 1.0){
				this.level = (this.level < 51) ? this.level+1 : 50;
				this.gp.addBonus(new Bonus(bonusTypes.LIFEUP));
				this.gp.addBonus(new Bonus(bonusTypes.POWERUP));
			}
			if(this.gp.getSpawnTimer()<0){
				this.spawnCounter++;
				int typeOfWave = (this.spawnCounter % 10 == 0) ? 3 : ((this.spawnCounter % 3 == 0) ? 2 : 1);
				this.ia.getEnemy(typeOfWave);
				this.gp.setSpawnTimer(5000/this.level);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}

		
		RenderPackage rp = new RenderPackage();

		for(int i = 0; i < this.gp.getPlayerArray().size() ; i++){
			Player p1 = this.gp.getPlayerArray().get(i);
			RenderContainer player = new RenderContainer();
			player.x = p1.getXPos();
			player.y = p1.getYPos();
			player.imageres = p1.getImageName();
			rp.renderList.add(player);
		}


		for(Enemy e : this.gp.getEnemyArray()){
			RenderContainer enemyRender = new RenderContainer();
			enemyRender.x = e.getXPos();
			enemyRender.y = e.getYPos();
			enemyRender.imageres = e.getImageName();
			rp.renderList.add(enemyRender);
		}
		
		
		for(Bullet e : this.gp.getBulletArray()){
			RenderContainer bulletRender = new RenderContainer();
			bulletRender.x = e.getXPos();
			bulletRender.y = e.getYPos();
			bulletRender.imageres = e.getImageName();
			rp.renderList.add(bulletRender);
		}

		/*for(Bonus e : this.gp.getBonusLifeArray()){
			RenderContainer bonusRender = new RenderContainer();
			bonusRender.x = e.getXPos();
			bonusRender.y = e.getYPos();
			bonusRender.imageres = e.getImageName();
			rp.renderList.add(bonusRender);
		}*/
		
		this.server.sendToAllTCP(rp);
		
	}


	public static void main(String[] args){
		try {
			ServerLauncher sl = ServerLauncher.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



}
