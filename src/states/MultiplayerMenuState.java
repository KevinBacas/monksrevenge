package states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MultiplayerMenuState extends BasicGameState{

	private int stateId;
	
	public MultiplayerMenuState(int id){
		this.stateId = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
	}

	public void render(GameContainer container, StateBasedGame sgb, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return this.stateId;
	}

}
