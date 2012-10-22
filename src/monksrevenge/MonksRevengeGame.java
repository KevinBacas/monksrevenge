package monksrevenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import states.Campain1State;
import states.Campain2State;
import states.Campain3State;
import states.FinishedGameState;
import states.MainMenuState;
import states.MultiplayerGameState;
import states.MultiplayerMenuState;
import states.SinglePlayerMenuState;
import states.SurvivalModState;

public class MonksRevengeGame extends StateBasedGame {

	
	// Différents states qui correspondent aux différents états du jeu
    public static final int MAINMENUSTATE			= 0;
    public static final int MULTIPLAYERMENUSTATE	= 1;
    public static final int SINGLEPLAYERMENUSTATE	= 2;
    public static final int SURVIVALMODSTATE		= 3;
    public static final int FINISHEDGAMEMODSTATE	= 4; 
    public static final int CAMPAIN1STATE 			= 5;
    public static final int CAMPAIN2STATE 			= 6;
    public static final int CAMPAIN3STATE 			= 7;
    public static final int MULTIPLAYERGAMESTATE 	= 8;
	private static FakeGameContainer appInstance = FakeGameContainer.getInstance();
	
	public static boolean online;
	public static String username;
	public static String password;
	public static String ipmulti;
    
    // Constructeur du jeu qui charge les states
	public MonksRevengeGame(String name) {
		super(name);
		
		//On ajoute les states
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new MultiplayerMenuState(MULTIPLAYERMENUSTATE));
		this.addState(new SinglePlayerMenuState(SINGLEPLAYERMENUSTATE));
		this.addState(new SurvivalModState(SURVIVALMODSTATE));
		this.addState(new FinishedGameState(FINISHEDGAMEMODSTATE));
		this.addState(new Campain1State(CAMPAIN1STATE));
		this.addState(new Campain2State(CAMPAIN2STATE));
		this.addState(new Campain3State(CAMPAIN3STATE));
		this.addState(new MultiplayerGameState(MULTIPLAYERGAMESTATE, "localhost"));
		//On entre dans le Menu Principal
        this.enterState(MAINMENUSTATE);
	}

	
	// (Ré)-Initialisation des States
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
        this.getState(MAINMENUSTATE).init(container, this);
        this.getState(MULTIPLAYERMENUSTATE).init(container, this);
        this.getState(SINGLEPLAYERMENUSTATE).init(container, this);
        this.getState(SURVIVALMODSTATE).init(container, this);
        this.getState(CAMPAIN1STATE).init(container, this);
        this.getState(CAMPAIN2STATE).init(container, this);
        this.getState(FINISHEDGAMEMODSTATE).init(container, this);
	}
	
	public void initState(GameContainer container, int stateId){
		try {
			this.getState(stateId).init(container, this);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
    public static void main(String[] args) throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new MonksRevengeGame("MonksRevengeDev V0.0.1 REV 258636547 (Environ...)"));
        try {
        	File file1 = new File("settings.ini");
        	if (!file1.exists()){
        		file1.createNewFile();
        	}

        	FileReader fr = new FileReader(file1);
        	BufferedReader br = new BufferedReader(fr);
        	
        	String settings = br.readLine();
        	String[] args1;
        	if (settings == null){
        		args1 = new String[0];
        	}
        	else{
            	args1 = settings.split(" ");
        	}

        	
        	if(args1.length == 6){//Reglage sur les paramètres args
        		String[] res = args1[0].split("[x]");
      
        		int width = (Integer.valueOf(res[0]) <= app.getScreenWidth()) ? Integer.valueOf(res[0]) : app.getScreenWidth();
        		int height = (Integer.valueOf(res[1]) <= app.getScreenHeight()) ? Integer.valueOf(res[1]) : app.getScreenWidth();
        		
    	        app.setDisplayMode(width, height, true);
    	       
    		    app.setFullscreen(Boolean.valueOf(args1[2]));

    		    if (args1[1].equals("false"))
    		        MonksRevengeGame.online = false;
    		    else 
    		    	MonksRevengeGame.online = true;
    	        
    	        MonksRevengeGame.username = args1[3];
    	        
    	        MonksRevengeGame.password = args1[4];
    	        
    	        MonksRevengeGame.ipmulti = args1[5];
        	}
        	else {
        		app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
        		app.setFullscreen(true);
        	}
        }
        catch (IOException e){
		        System.out.println("Launch with Launcher");
        }

        
    	
	        
	    app.setMinimumLogicUpdateInterval(20);
	    app.setMaximumLogicUpdateInterval(20);
	    app.setSmoothDeltas(true);
	    app.setTargetFrameRate(60);
	    
	    //On passe l'instance en static pour pouvoir y acceder pour les Settings
	    FakeGameContainer.getInstance().setHeight(app.getHeight());
	    FakeGameContainer.getInstance().setWidth(app.getWidth());
     
    	app.start();
    }
}
