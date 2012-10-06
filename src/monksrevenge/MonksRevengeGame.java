package monksrevenge;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import states.Campain1State;
import states.FinishedGameState;
import states.MainMenuState;
import states.SinglePlayerMenuState;
import states.SurvivalModState;

public class MonksRevengeGame extends StateBasedGame {

	
	// Différents states qui correspondent aux différents états du jeu
    public static final int MAINMENUSTATE			= 0;
    public static final int SINGLEPLAYERMENUSTATE	= 1;
    public static final int SURVIVALMODSTATE		= 2;
    public static final int FINISHEDGAMEMODSTATE	= 3; 
    public static final int CAMPAIN1STATE 			= 4;
    public static final int CAMPAIN2STATE 			= 5;
    public static final int CAMPAIN3STATE 			= 6;
    
    // Instance du GameContainer
    public static AppGameContainer appInstance = null;
    
    public static float scale;
	
    
    // Constructeur du jeu qui charge les states
	public MonksRevengeGame(String name) {
		super(name);
		
		//On ajoute les states
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new SinglePlayerMenuState(SINGLEPLAYERMENUSTATE));
		this.addState(new SurvivalModState(SURVIVALMODSTATE));
		this.addState(new FinishedGameState(FINISHEDGAMEMODSTATE));
		this.addState(new Campain1State(CAMPAIN1STATE));
		//this.addState(new );	Campain2State
		//this.addState(new );	Campain3State 
		
		//On entre dans le Menu Principal
        this.enterState(MAINMENUSTATE);
	}

	
	// (Ré)-Initialisation des States
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
        this.getState(MAINMENUSTATE).init(container, this);
        this.getState(SINGLEPLAYERMENUSTATE).init(container, this);
        this.getState(SURVIVALMODSTATE).init(container, this);
        this.getState(CAMPAIN1STATE).init(container, this);
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

    	if(args.length == 3){//Reglage sur les paramètres argss
    		String[] res = args[0].split("[|]");
  
    		int width = (Integer.valueOf(res[0]) <= app.getScreenWidth()) ? Integer.valueOf(res[0]) : app.getScreenWidth();
    		int height = (Integer.valueOf(res[1]) <= app.getScreenHeight()) ? Integer.valueOf(res[1]) : app.getScreenWidth();
    		
	        app.setDisplayMode(width, height, true);
	        app.setFullscreen(Boolean.valueOf(args[2]));
	        //TODO 4ème paramètre : pseudo
    	}else{
	        app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
	        app.setFullscreen(true);
    	}
	    app.setMinimumLogicUpdateInterval(20);
	    app.setMaximumLogicUpdateInterval(20);
	    app.setSmoothDeltas(true);
	    app.setTargetFrameRate(60);
	    
	    //Gestion du scale
	    MonksRevengeGame.scale = (float) (app.getHeight() / 1080f * 0.15);
	    
	    //On passe l'instance en static pour pouvoir y acceder pour les Settings
        MonksRevengeGame.appInstance = app;
        
    	app.start();
    }
}
