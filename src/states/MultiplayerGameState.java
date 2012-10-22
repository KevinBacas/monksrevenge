package states;

import java.io.IOException;
import java.util.LinkedList;

import monksrevenge.FakeGameContainer;
import monksrevenge.MonksRevengeGame;
import network.PlayerInfo;
import network.RenderContainer;
import network.RenderPackage;
import network.gameStart;
import network.newPlayerConnectionRequest;
import network.newPlayerConnectionResponse;
import objects.Background;
import objects.GamePackage;
import objects.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class MultiplayerGameState extends BasicGameState {
	
	private int stateId;
	private boolean isFirstInit = true;
	private Client client;
	private GamePackage gp = GamePackage.getInstance();
	private String username = "AzaGhaL";
	
	private Background bg;
	public static StateBasedGame sbg;
	private Player player;

	private int timerDelta;
	
	public static LinkedList<RenderContainer> renderList = new LinkedList<RenderContainer>();
	public static LinkedList<RenderContainer> receivedRenderList = new LinkedList<RenderContainer>(renderList);
	public static boolean updatePlayer = false;
	public static int indexp;
	
	
	public MultiplayerGameState(int id, String host){
		this.stateId = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		if(!isFirstInit){
			this.client = new Client(250000,250000);
			this.client.start();
			
			Kryo kryo = this.client.getKryo();
			kryo.register(newPlayerConnectionRequest.class);
			kryo.register(newPlayerConnectionResponse.class);
			kryo.register(gameStart.class);
			kryo.register(String[].class);
			kryo.register(RenderContainer.class);
			kryo.register(PlayerInfo.class);
			kryo.register(LinkedList.class);
			kryo.register(RenderPackage.class);
			Log.set(Log.LEVEL_DEBUG);
			this.connect();
			MultiplayerGameState.sbg = sbg;
		}
		this.bg = new Background(this.gp.getResourceLoader().getImage("img/bg.jpg"));
		this.isFirstInit = false;
		this.gp.init(false);
		this.player = new Player(50, FakeGameContainer.getInstance().getHeight()/3, 250, 250, 100, 100, 300, 300, "img/Hammerheadblue.png", true);
		this.timerDelta = 0;
		MultiplayerGameState.updatePlayer = false;
		MultiplayerGameState.indexp = 0;
	}
	
	public void connect(){
		try {
			this.client.connect(5000, MonksRevengeGame.ipmulti , 54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

		newPlayerConnectionRequest request = new newPlayerConnectionRequest();
		request.username = this.username;
		this.client.sendTCP(request);
		
		this.client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				
					if (object instanceof newPlayerConnectionResponse) {
						newPlayerConnectionResponse response = (newPlayerConnectionResponse)object;
						System.out.println(response.ok + " " + response.msg);
						MultiplayerGameState.indexp = response.indexp;
						if(!response.ok){
							MultiplayerGameState.renderList.clear();
							MultiplayerGameState.sbg.enterState(MonksRevengeGame.MAINMENUSTATE);
						}
					}
					
					if (object instanceof RenderPackage) {
						MultiplayerGameState.updatePlayer = true;
						synchronized (MultiplayerGameState.receivedRenderList) {
							RenderPackage rp = (RenderPackage)object;
							MultiplayerGameState.receivedRenderList = rp.renderList;
						}
					}
			}
		});

	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		
			this.bg.render(container, sbg, g);
		
			for (RenderContainer rc : MultiplayerGameState.getRenderList()){
				gp.getResourceLoader().getImage(rc.imageres).draw(rc.x, rc.y, FakeGameContainer.getInstance().getScale());
			}
			

			
			
	}



	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		
		this.timerDelta += delta;
		
		if (!MultiplayerGameState.renderList.equals(MultiplayerGameState.receivedRenderList)){
			synchronized (MultiplayerGameState.renderList){
				synchronized(MultiplayerGameState.receivedRenderList) {
					MultiplayerGameState.renderList = MultiplayerGameState.receivedRenderList;
				}
			}
			
		}
		
		this.bg.update(container, sbg, delta);
		
		if (MultiplayerGameState.updatePlayer && this.timerDelta >= 50){
			synchronized (this.player) {
				this.player.updateMulti(client, container, 50, MultiplayerGameState.indexp);
			}
			this.timerDelta = 0;
		}
	}

	@Override
	public int getID() {
		return this.stateId;
	}
	
	
	@SuppressWarnings("unchecked")
	private static LinkedList<RenderContainer> getRenderList() {
		
		return (LinkedList<RenderContainer>) MultiplayerGameState.renderList.clone();
	}

}
