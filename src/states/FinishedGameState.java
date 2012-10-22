package states;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import monksrevenge.FakeGameContainer;
import monksrevenge.MonksRevengeGame;
import network.Crypter;
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
	
	private int score=0;
	
	private MouseOverArea back;
	
	private GameContainer container;
	private StateBasedGame sbg;

	private int stateId;

	private Image fond;
	
	public FinishedGameState(int id){
		this.stateId = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		
		int posX = container.getWidth() / 3;
		int posY = container.getHeight() / 3;
		
		this.fond = GamePackage.getInstance().getResourceLoader().getImage("img/fond menu.jpg");
		
		Image _back = GamePackage.getInstance().getResourceLoader().getImage("img/main menu.png");
		
		this.back = new MouseOverArea(container, _back, (posX*2 - _back.getWidth()), posY*2);
		this.back.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		this.back.setMouseOverColor(new Color(1f,1f,1f,1f));
		
		this.container = container;
		this.sbg = sbg;
		
	}

	public void render(GameContainer container, StateBasedGame sgb, Graphics g)
			throws SlickException {
		float scale = (float)FakeGameContainer.getInstance().getHeight() / (float)this.fond.getHeight();
		this.fond.draw((FakeGameContainer.getInstance().getWidth() /2) - ((this.fond.getWidth()*scale) / 2) , 0, scale);
		this.back.render(container, g);
		g.drawString("Score : " + this.score, container.getWidth() / 2, container.getHeight()/3);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = this.container.getInput();
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			MouseOverArea source = null;
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
			if (source.equals(this.back)) {
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
			    	else{
			    		file1.delete();
			    		file1.createNewFile();
			    	}
			    	
			    	FileReader fr = new FileReader(file1);
			    	BufferedReader br = new BufferedReader(fr);
			    	
			    	String a;
			    	
			    	String score = ( ( a = br.readLine()) == null ? null : Crypter.decrypte( a ));
			    	int i = (score == null ? 0 : Integer.valueOf(score));
			    	
			    	if ( i < this.score){ 
			    		FileOutputStream out = new FileOutputStream(file1);
				    	
				    	out.write(Crypter.encrypte(String.valueOf(this.score)).getBytes());
						out.flush();
						out.close();

			    	}
			    	
			    	
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
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
	
	public void setScore(int l){
		this.score = l;
	}

}

