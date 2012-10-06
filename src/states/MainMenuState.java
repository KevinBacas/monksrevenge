package states;


import monksrevenge.MonksRevengeGame;

import objects.GamePackage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MainMenuState extends BasicGameState implements ComponentListener {
	
	private int stateId;
	
	private MouseOverArea singlePlayerString;
	private MouseOverArea multiPlayerString;
	private MouseOverArea exit;
	
	private StateBasedGame sbg;
	private GameContainer container;
	
	public MainMenuState(int id){
		this.stateId = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		int posX = container.getWidth() / 3;
		int posY = container.getHeight() / 2;
		
		Image temp = null;//Image de sauvegarde temporaire pour les instanciations
		
		exit = new MouseOverArea(container, temp = new Image("img/exit.png"), 9, 25);
		exit.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		exit.setMouseOverColor(new Color(1f,1f,1f,1f));
		exit.addListener(this);
		
		singlePlayerString = new MouseOverArea(container, temp = GamePackage.getInstance().getResourceLoader().getImage("img/Singleplayer.png"), (posX - temp.getWidth()/2), posY);
		singlePlayerString.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		singlePlayerString.setMouseOverColor(new Color(1f,1f,1f,1f));
		singlePlayerString.addListener(this);

		multiPlayerString = new MouseOverArea(container,temp = GamePackage.getInstance().getResourceLoader().getImage("img/Multiplayer.png"), (posX*2 - temp.getWidth()/2), posY);
		multiPlayerString.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		multiPlayerString.setMouseOverColor(new Color(1f,1f,1f,1f));
		multiPlayerString.addListener(this);
		
		this.container = container;
		this.sbg = sbg;
		
	}

	public void render(GameContainer container, StateBasedGame sgb, Graphics g)
			throws SlickException {
		exit.render(container, g);
		singlePlayerString.render(container, g);
		multiPlayerString.render(container, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		//Nothing to do here...
	}
	
	/**
	 * Méthode appelée lorsque l'on clique sur un élément
	 */
	@Override
	public void componentActivated(AbstractComponent source) {
		if(source != null){
			if (source.equals(exit)) {
				container.exit();
			}
			if (source.equals(singlePlayerString)) {
				((MonksRevengeGame) sbg).initState(container, MonksRevengeGame.SINGLEPLAYERMENUSTATE);
				sbg.enterState(MonksRevengeGame.SINGLEPLAYERMENUSTATE);
			}
			if (source.equals(multiPlayerString)) {
				//sbg.enterState(MonksRevengeGame.MULTIPLAYERMENUSTATE);
			}
		}
	}

	@Override
	public int getID() {
		return this.stateId;
	}

}
