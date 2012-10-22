package states;

import monksrevenge.FakeGameContainer;
import monksrevenge.MonksRevengeGame;

import objects.GamePackage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class SinglePlayerMenuState extends BasicGameState implements ComponentListener{

	private int stateId;
	private GamePackage gp = GamePackage.getInstance();
	
	private MouseOverArea levelOne;
	private MouseOverArea levelTwo;
	private MouseOverArea levelThree;
	private MouseOverArea levelSurv;
	private MouseOverArea back;
	
	private StateBasedGame sbg;
	private GameContainer container;
	private Image fond;
	
	public SinglePlayerMenuState(int id){
		this.stateId = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		
		int posX = container.getWidth() / 3;
		int posY = container.getHeight() / 3;
		
		Image temp = null;//Image de sauvegarde temporaire pour les instanciations
		
		this.fond = this.gp.getResourceLoader().getImage("img/fond menu.jpg");
		
		
		back = new MouseOverArea(container, temp = new Image("img/back.png"), 9, 25);
		back.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		back.setMouseOverColor(new Color(1f,1f,1f,1f));
		back.addListener(this);
		
		levelOne = new MouseOverArea(container,temp = new Image("img/campagne1.png"), (posX - temp.getWidth()/2), posY);
		levelOne.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		levelOne.setMouseOverColor(new Color(1f,1f,1f,1f));
		levelOne.addListener(this);

		levelTwo = new MouseOverArea(container,temp = new Image("img/campagne2.png"),(posX*2 - temp.getWidth()/2), posY);
		levelTwo.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		levelTwo.setMouseOverColor(new Color(1f,1f,1f,1f));
		levelTwo.addListener(this);
		
		levelThree = new MouseOverArea(container,temp = new Image("img/campagne3.png"), (posX - temp.getWidth()/2), posY*2);
		levelThree.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		levelThree.setMouseOverColor(new Color(1f,1f,1f,1f));
		levelThree.addListener(this);
		
		levelSurv = new MouseOverArea(container,temp = new Image("img/survival.png"), (posX*2 - temp.getWidth()/2), posY*2);
		levelSurv.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		levelSurv.setMouseOverColor(new Color(1f,1f,1f,1f));
		levelSurv.addListener(this);
		
		this.container = container;
		this.sbg = sbg;
		
	}

	public void render(GameContainer container, StateBasedGame sgb, Graphics g)
			throws SlickException {
		float scale = (float)FakeGameContainer.getInstance().getHeight() / (float)this.fond.getHeight();
		this.fond.draw((FakeGameContainer.getInstance().getWidth() /2) - ((this.fond.getWidth()*scale) / 2) , 0, scale);
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
				((MonksRevengeGame) sbg).initState(container, MonksRevengeGame.CAMPAIN2STATE);
				this.sbg.enterState(MonksRevengeGame.CAMPAIN2STATE);
			}
			if(source.equals(levelThree)){
				((MonksRevengeGame) sbg).initState(container, MonksRevengeGame.CAMPAIN3STATE);
				this.sbg.enterState(MonksRevengeGame.CAMPAIN3STATE);
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
