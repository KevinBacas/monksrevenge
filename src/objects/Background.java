package objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import monksrevenge.FakeGameContainer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Background {
	
	private Image background;
	private int xRender;
	private ArrayList<Star> starArray = new ArrayList<Star>();;
	private int starTimer = 2000;
	
	private LinkedList<Star> removeStar = new LinkedList<Star>();
	
	public Background(Image img){
		this.background=img;
		this.xRender = 0;
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		this.updateStarTimer(delta);
		if(this.starTimer <= 0){
			this.starArray.add(new Star((int)(Math.random() * FakeGameContainer.getInstance().getWidth()), (int)(Math.random() * FakeGameContainer.getInstance().getHeight())));
			this.resetStarTimer();
		}
		for(int i=0; i < this.starArray.size(); i++){
			this.starArray.get(i).update(delta);
		}
		//On supprime les Star
		if(this.removeStar.size() > 0){
			Iterator<Star> it = this.removeStar.iterator();
	   		while(it.hasNext()){
	   			Star s = it.next();
	   			this.starArray.remove(s);
	   		}
	   		this.removeStar.clear();
		}
	}
	
	public void render(GameContainer container, StateBasedGame sgb, Graphics g) throws SlickException {
		this.xRender = (this.xRender+1<=this.getImage().getWidth()) ? (this.xRender+1) : 0;
		//Dessin de la partie supérieur
		this.background.getSubImage(this.getXRender(), 0, this.getImage().getWidth()-this.getXRender(), this.getImage().getWidth()).draw(0,0);
		//Dessin de la partie inférieur
		this.background.getSubImage(0, 0, this.getXRender(), this.getImage().getWidth()).draw(this.getImage().getWidth()-this.getXRender(),0);
		for(int i=0 ; i < this.starArray.size() ; i++){
			this.starArray.get(i).render(container, g);
		}
	}
	
	public Image getImage(){
		return this.background;
	}
	
	public int getXRender(){
		return this.xRender;
	}
	
	public void updateStarTimer(int delta){
		this.starTimer -= delta;
	}
	
	public void resetStarTimer(){
		this.starTimer = 500;
	}
	
	//Permet d'ajouter une Star dans la liste de suppression
	public void removeStar(Star s){
		this.removeStar.add(s);
	}

}
