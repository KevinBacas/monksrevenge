package states;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import monksrevenge.MonksRevengeGame;
import network.Listening;
import network.RenderContainer;
import network.ScoreContainer;
import objects.ResourceLoader;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import TestKryoNet.SomeRequest;
import TestKryoNet.SomeResponse;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class MultiplayerGameState extends BasicGameState {
	
	private int stateId;
	private boolean connected;
	private Client client;
	
	private ResourceLoader rl = new ResourceLoader();
	
	private LinkedList<RenderContainer> renderList = new LinkedList<RenderContainer>();
	
	public MultiplayerGameState(int id, String host){
		this.stateId = id;
		client =  new Client();
		client.start();
		
		Kryo kryo = client.getKryo();
		kryo.register(Listening.class);
		kryo.register(RenderContainer.class);
		kryo.register(ScoreContainer.class);
		
		try {
			client.connect(5000, host , 54555, 54777);
			client.sendTCP("Username");
		} catch (IOException e) {
			// TODO : Come back but this will be done later cause Kevin is a crappy guy
		}


	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		//Nothing to do here...
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		Iterator<RenderContainer> it = this.renderList.iterator();
		while(it.hasNext()){
			RenderContainer objcrt = it.next();
			rl.getImage(objcrt.imageres).draw(objcrt.x, objcrt.y, MonksRevengeGame.scale);
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		client.addListener(new Listener() {
			   public void received (Connection connection, Object object) {
			      if (object instanceof SomeResponse) {
			         SomeResponse response = (SomeResponse)object;
			         System.out.println(response.text);
			      }
			   }
			});  
	}

	@Override
	public int getID() {
		return this.stateId;
	}

}
