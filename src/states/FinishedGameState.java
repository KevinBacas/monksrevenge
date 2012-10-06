package states;

import network.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

public class FinishedGameState extends BasicGameState implements ComponentListener{
	
	private long score=0;
	
	private MouseOverArea upload;
	private MouseOverArea back;
	
	private GameContainer container;
	private StateBasedGame sbg;

	private int stateId;
	
	public FinishedGameState(int id){
		this.stateId = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		
		int posX = container.getWidth() / 3;
		int posY = container.getHeight() / 3;
		
		Image _upload = GamePackage.getInstance().getResourceLoader().getImage("img/UploadScore.png");
		Image _back = GamePackage.getInstance().getResourceLoader().getImage("img/ReturnMainMenu.png");
		
		this.upload = new MouseOverArea(container, _upload, (posX - _upload.getWidth()/2), posY*2);
		this.upload.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		this.upload.setMouseOverColor(new Color(1f,1f,1f,1f));
		this.upload.addListener(this);

		this.back = new MouseOverArea(container, _back, (posX*2 - _back.getWidth()/2), posY*2);
		this.back.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		this.back.setMouseOverColor(new Color(1f,1f,1f,1f));
		
		this.container = container;
		this.sbg = sbg;
		
	}

	public void render(GameContainer container, StateBasedGame sgb, Graphics g)
			throws SlickException {
		this.upload.render(container, g);
		this.back.render(container, g);
		g.drawString("Score : " + this.score, container.getWidth() / 2, container.getHeight()/3);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = this.container.getInput();
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			MouseOverArea source = null;
			if(this.upload.isMouseOver()){
				source = this.upload;
			}
			if(this.back.isMouseOver()){
				source = this.back;
			}
			this.componentActivated(source);
		}
	}
	
	/**
	 * Méthode appelée lorsque l'on clique sur un élément
	 */
	@Override
	public void componentActivated(AbstractComponent source) {
		if(source != null){
			if (source.equals(this.upload)) {
				try {
					
					
				    String OS = System.getProperty("os.name").toUpperCase();
				    if (OS.contains("WIN"))
				        OS = System.getenv("APPDATA")+"\\MonksRevenge";
				    else if (OS.contains("NUX") || OS.contains("MAC"))
				        OS = System.getProperty("user.home")+"\\MonksRevenge";
				    
				    File file = new File(OS);
				    if( !file.exists() ) {
				    	file.mkdir();
				    }
			    	File file1 = new File(OS+"\\scorefile");
			    	if (!file1.exists()){
			    		file1.createNewFile();
			    	}
			    	FileInputStream in = new FileInputStream(file1);
			    	FileOutputStream out = new FileOutputStream(file1);
					out.write(Crypter.encrypte(this.score+"\n").getBytes());
					out.flush();
					out.close();



					
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				sbg.enterState(MonksRevengeGame.MAINMENUSTATE);
			}
			if (source.equals(this.back)) {
				try {
					sbg.initStatesList(this.container);
				} catch (SlickException e) {
					e.printStackTrace();
				}
				sbg.enterState(MonksRevengeGame.MAINMENUSTATE);
			}
		}
	}

	@Override
	public int getID() {
		return this.stateId;
	}
	
	public void setScore(long l){
		this.score = l;
	}

}

