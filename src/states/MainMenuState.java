package states;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import monksrevenge.FakeGameContainer;
import monksrevenge.MonksRevengeGame;
import network.Crypter;
import network.SingletonConnection;
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


public class MainMenuState extends BasicGameState implements ComponentListener {
	
	private int stateId;
	
	private MouseOverArea singlePlayerString;
	private MouseOverArea multiPlayerString;
	private MouseOverArea exit;
	private MouseOverArea uploadString;
	
	private StateBasedGame sbg;
	private GameContainer container;

	private GamePackage gp = GamePackage.getInstance();

	private Image fond;
	
	public MainMenuState(int id){
		this.stateId = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		int posX = container.getWidth() / 3;
		int posY = container.getHeight() / 3;
		
		this.gp.init(false);
		
		Image temp = null;//Image de sauvegarde temporaire pour les instanciations
		
		this.fond = this.gp.getResourceLoader().getImage("img/fond menu.jpg");
		
		exit = new MouseOverArea(container, temp = this.gp.getResourceLoader().getImage("img/exit.png"), 9, 25);
		exit.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		exit.setMouseOverColor(new Color(1f,1f,1f,1f));
		exit.addListener(this);
		
		singlePlayerString = new MouseOverArea(container, temp = this.gp.getResourceLoader().getImage("img/single.png"), (posX - temp.getWidth()/2), posY);
		singlePlayerString.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		singlePlayerString.setMouseOverColor(new Color(1f,1f,1f,1f));
		singlePlayerString.addListener(this);

		multiPlayerString = new MouseOverArea(container,temp = this.gp.getResourceLoader().getImage("img/multi.png"), (posX*2 - temp.getWidth()/2), posY);
		multiPlayerString.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		multiPlayerString.setMouseOverColor(new Color(1f,1f,1f,1f));
		multiPlayerString.addListener(this);
		
		
		uploadString = new MouseOverArea(container,temp = this.gp.getResourceLoader().getImage("img/upload.png"), (posX*3/2 - temp.getWidth()/2), posY*2 + 100);
		uploadString.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		uploadString.setMouseOverColor(new Color(1f,1f,1f,1f));
		uploadString.addListener(this);
		
		
		this.container = container;
		this.sbg = sbg;
		
	}

	public void render(GameContainer container, StateBasedGame sgb, Graphics g)
			throws SlickException {
		float scale = (float)FakeGameContainer.getInstance().getHeight() / (float)this.fond.getHeight();
		this.fond.draw((FakeGameContainer.getInstance().getWidth() /2) - ((this.fond.getWidth()*scale) / 2) , 0, scale);
		exit.render(container, g);
		singlePlayerString.render(container, g);
		multiPlayerString.render(container, g);
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
	    	
	    	
			if(in.available() > 1 && MonksRevengeGame.online){
				uploadString.render(container, g);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
				((MonksRevengeGame) sbg).initState(container, MonksRevengeGame.MULTIPLAYERGAMESTATE);
				sbg.enterState(MonksRevengeGame.MULTIPLAYERGAMESTATE);
			}
			if (source.equals(uploadString)) {
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




					FileReader fr = new FileReader(file1);
					BufferedReader br = new BufferedReader(fr);



					String score = (Crypter.decrypte(br.readLine()));

					System.out.println(score);

					if (MonksRevengeGame.online){
						SingletonConnection sc = SingletonConnection.getInstance();
						sc.writeHTTPRequest("pseudocompte="+MonksRevengeGame.username+"&mot_de_passe="+MonksRevengeGame.password+"&highscore=" + score);

						System.out.println(sc.readHTTPRequest());
						sc.destroy();
						FileOutputStream out = new FileOutputStream(file1);
						out.write(0);
					}
				}
				catch (IOException io){
					System.out.println(io.getMessage());
				}
			}
		}
	}

	@Override
	public int getID() {
		return this.stateId;
	}

}
