package states;

import monksrevenge.MonksRevengeGame;

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

public class SinglePlayerMenuState extends BasicGameState implements ComponentListener{

	private int stateId;
	
	private MouseOverArea levelOne;
	private MouseOverArea levelTwo;
	private MouseOverArea levelThree;
	private MouseOverArea levelSurv;
	private MouseOverArea back;
	
	private StateBasedGame sbg;
	private GameContainer container;
	
	public SinglePlayerMenuState(int id){
		this.stateId = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		
		int posX = container.getWidth() / 4;
		int posY = container.getHeight() / 3;
		
		Image temp = null;//Image de sauvegarde temporaire pour les instanciations
		
		back = new MouseOverArea(container, temp = new Image("img/back.png"), 9, 25);
		back.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		back.setMouseOverColor(new Color(1f,1f,1f,1f));
		back.addListener(this);
		
		levelOne = new MouseOverArea(container,temp = new Image("img/Singleplayer.png"), (posX - temp.getWidth()/2), posY);
		levelOne.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		levelOne.setMouseOverColor(new Color(1f,1f,1f,1f));
		levelOne.addListener(this);

		levelTwo = new MouseOverArea(container,temp = new Image("img/Multiplayer.png"),(posX*2 - temp.getWidth()/2), posY);
		levelTwo.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		levelTwo.setMouseOverColor(new Color(1f,1f,1f,1f));
		levelTwo.addListener(this);
		
		levelThree = new MouseOverArea(container,temp = new Image("img/Settings.png"), (posX*3 - temp.getWidth()/2), posY);
		levelThree.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		levelThree.setMouseOverColor(new Color(1f,1f,1f,1f));
		levelThree.addListener(this);
		
		levelSurv = new MouseOverArea(container,temp = new Image("img/Settings.png"), (posX*2 - temp.getWidth()/2), posY*2);
		levelSurv.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		levelSurv.setMouseOverColor(new Color(1f,1f,1f,1f));
		levelSurv.addListener(this);
		
		this.container = container;
		this.sbg = sbg;
		
	}

	public void render(GameContainer container, StateBasedGame sgb, Graphics g)
			throws SlickException {
		back.render(container, g);
		levelOne.render(container, g);
		levelTwo.render(container, g);
		levelThree.render(container, g);
		levelSurv.render(container, g);
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
			if (source.equals(back)) {
				((MonksRevengeGame) sbg).initState(container, MonksRevengeGame.MAINMENUSTATE);
				this.sbg.enterState(MonksRevengeGame.MAINMENUSTATE);
			}
			if (source.equals(levelOne)) {
				((MonksRevengeGame) sbg).initState(container, MonksRevengeGame.CAMPAIN1STATE);
				this.sbg.enterState(MonksRevengeGame.CAMPAIN1STATE);
			}
			if (source.equals(levelTwo)) {
				//In case of fire, Break the glass.
			}
			if(source.equals(levelThree)){
				//In case of fire, Break the glass.
			}
			if(source.equals(levelSurv)){
				((MonksRevengeGame) sbg).initState(container, MonksRevengeGame.SURVIVALMODSTATE);
				this.sbg.enterState(MonksRevengeGame.SURVIVALMODSTATE);
			}
		}
	}
	@Override
	public int getID() {
		return this.stateId;
	}

}
