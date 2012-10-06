package objects;

import monksrevenge.MonksRevengeGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.ShapeRenderer;

/** Classe dont héritent tous les objets pouvant être représentés en jeu */
abstract class GameObject {


	// ATTRIBUTS

	/**      * X du coin supérieur gauche de l'image */
	private int xPos;

	/**      * Y du coin supérieur gauche de l'image */
	private int yPos;

	/**      * Vitesse actuelle horizontale de l'objet */
	private int xSpeed;

	/**      * Vitesse actuelle verticale de l'objet */
	private int ySpeed;

	/**      * Accélération horizontale de l'objet 
	 * 
	 * (100 constant; <100 décélération; >100 accélération;) */
	private int xAcceleration;

	/**      * Accélération verticale de l'objet
	 * 
	 * (100 constant; <100 décélération; >100 accélération;) */
	private int yAcceleration;

	/**      * Vitesse maximale horizontale de l'objet */
	private int xSpeedMax;

	/**      * Vitesse maximale verticale de l'objet */
	private int ySpeedMax;

	/**      * Fichier image représentant l'objet */
	private Image image;

	/**      * Objet amical ou non */
	private boolean friendly;

	/** 		* Temps avant acceleration en ms */
	private int time_before_acceleration = 100;
	
	/** Shape de l'object représentant la surface physique */
	private Shape shape;

	// METHODES


	/**		* Updater de time_before_acceleration */
	public int adjustTimeBeforeAcceleration(int delta){
		this.setTimeBeforeAcceleration(this.getTimeBeforeAcceleration() - delta);
		return this.getTimeBeforeAcceleration();
	}

	/**      * Getter friendly */
	public boolean getFriendly() {
		return this.friendly;
	}


	/**      * Hauteur de l'objet */
	public int getHeigth() {
		return this.image.getHeight();
	}

	/**      * Getter image */
	public Image getImage() {
		return this.image;
	}

	/**      * Getter time_before */
	public int getTimeBeforeAcceleration(){
		return this.time_before_acceleration;
	}

	/**      * Largeur de l'objet */
	public int getWidth() {
		return this.image.getWidth();
	}

	/**      * Getter xAcceleration */
	public int getXAcceleration() {
		return this.xAcceleration;
	}

	/**      * Getter xPos */
	public int getXPos() {
		return this.xPos;
	}

	/**      * Getter xSpeed */
	public int getXSpeed() {
		return this.xSpeed;
	}

	/**      * Getter xSpeedMax */
	public int getXSpeedMax() {
		return this.xSpeedMax;
	}

	/**      * Getter yAcceleration */
	public int getYAcceleration() {
		return this.yAcceleration;

	}

	/**      * Getter yPos */
	public int getYPos() {
		return this.yPos;
	}

	/**      * Getter ySpeed */
	public int getYSpeed() {
		return this.ySpeed;
	}

	/**      * Getter ySpeedMax */
	public int getYSpeedMax() {
		return this.ySpeedMax;
	}

	/**      * Méthode traitant le cas oà¹ il y a collision avec l'objet o*/
	public abstract void onCollision(GameObject o) throws SlickException;

	/** Implémentation de render */
	public void render(GameContainer container, Graphics g) throws SlickException {
		this.getImage().draw(this.getXPos(), this.getYPos(), MonksRevengeGame.scale);
		ShapeRenderer.draw(this.getShape());
	}

	/**      * Setter friendly */
	public void setFriendly(boolean bool){
		this.friendly = bool;
	}

	/**      * Setter image */
	public void setImage(Image img) {
		this.image = img;
	}

	/**      * Setter deltaCounter */
	public void setTimeBeforeAcceleration(int delta){
		this.time_before_acceleration = delta ;
	}

	/**      * Setter xAcceleration */
	public void setXAcceleration(int xacceleration) {
		this.xAcceleration = xacceleration;
	}

	/**      * Setter xPos */
	public void setXPos(int x) {
		this.xPos = x;
	}

	/**      * Setter xSpeed */
	public void setXSpeed(int xspeed) {
		this.xSpeed = xspeed;
	}

	/**      * Setter xSpeedMax  */
	public void setXSpeedMax(int xspeedmax) {
		this.xSpeedMax = xspeedmax;
	}

	/**      * Setter yAcceleration */
	public void setYAcceleration(int yacceleration) {
		this.yAcceleration = yacceleration;
	}

	/**      * Setter yPos */
	public void setYPos(int y) {
		this.yPos = y;
	}

	/**      * Setter ySpeed */
	public void setYSpeed(int yspeed) {
		this.ySpeed = yspeed;
	}

	/**      * Setter ySpeedMax */
	public void setYSpeedMax(int yspeedmax) {
		this.ySpeedMax = yspeedmax;
	}

	/**      * Méthode servant à  mettre à  jour l'objet */
	public abstract void update(GameContainer container, int delta) throws SlickException;

	/**      * Met à  jour l'attribut xSpeed, xPos et yPos */
	public void updateLocation(int delta) {
		if((this.adjustTimeBeforeAcceleration(delta)) <= 0){
			this.setXSpeed((this.getXSpeed() * this.getXAcceleration() / 100) < this.getXSpeedMax() ? (this.getXSpeed() * this.getXAcceleration() / 100) : this.getXSpeedMax());
			this.setYSpeed((this.getYSpeed() * this.getYAcceleration() / 100) < this.getYSpeedMax() ? (this.getYSpeed() * this.getYAcceleration() / 100) : this.getYSpeedMax());
			this.setTimeBeforeAcceleration(100);
		}
		this.setXPos(this.getXPos() + (int)(this.getXSpeed() * delta / 1000f));
		this.setYPos(this.getYPos() + (int)(this.getYSpeed() * delta / 1000f));
	}
	
	//Renvoi true si l'enemy est sorti completement de l'écran
	public boolean isOutOfRender(){
		return !(this.getXPos() > -this.getImage().getWidth()*MonksRevengeGame.scale && this.getYPos() > -this.getImage().getHeight()*MonksRevengeGame.scale && this.getXPos() + this.getImage().getWidth() * MonksRevengeGame.scale < MonksRevengeGame.appInstance.getWidth()+this.getImage().getWidth() * MonksRevengeGame.scale && this.getYPos() + this.getImage().getHeight() * MonksRevengeGame.scale < MonksRevengeGame.appInstance.getHeight()+this.getImage().getHeight() * MonksRevengeGame.scale);

	}
	
	/* Renvoi True si le gameObject est en dehors de l'écran */
	public boolean isOutOfScreen(){
		return !(this.getXPos() > 0 && this.getYPos() > 0 && this.getXPos() + this.getImage().getWidth() * MonksRevengeGame.scale < MonksRevengeGame.appInstance.getWidth() && this.getYPos() + this.getImage().getHeight() * MonksRevengeGame.scale < MonksRevengeGame.appInstance.getHeight());
	}
	
	//Direction entre 0 et 7 Correspondant au point cardinaux en partant du nord et dans le sens des aiguilles d'une montre.
	protected void moveByDirection(int direction, int delta){
		switch(direction){
			case 0:
				this.setYPos(this.getYPos() + -1 * ((int)(this.getYSpeed() * delta / 1000f)));
				break;
			case 1:
				this.setXPos(this.getXPos() + (int)(this.getXSpeed() * delta / 1000f));
				this.setYPos(this.getYPos() + -1 * ((int)(this.getYSpeed() * delta / 1000f)));
				break;
			case 2:
				this.setXPos(this.getXPos() + (int)(this.getXSpeed() * delta / 1000f));
				break;
			case 3:
				this.setXPos(this.getXPos() + (int)(this.getXSpeed() * delta / 1000f));
				this.setYPos(this.getYPos() + (int)(this.getYSpeed() * delta / 1000f));
				break;
			case 4:
				this.setYPos(this.getYPos() + (int)(this.getYSpeed() * delta / 1000f));
				break;
			case 5:
				this.setXPos(this.getXPos() + -1 * ((int)(this.getXSpeed() * delta / 1000f)));
				this.setYPos(this.getYPos() + (int)(this.getYSpeed() * delta / 1000f));
				break;
			case 6:
				this.setXPos(this.getXPos() + -1 * ((int)(this.getXSpeed() * delta / 1000f)));
				break;
			case 7:
				this.setXPos(this.getXPos() + -1 * ((int)(this.getXSpeed() * delta / 1000f)));
				this.setYPos(this.getYPos() + -1 * ((int)(this.getYSpeed() * delta / 1000f)));
				break;
			default:
				break;
		}
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	//Methode qui permet de tester s'il y a collision
	public void collide(GameObject go) throws SlickException{
		if(this.shape.contains(go.shape) || this.shape.intersects(go.shape)){
			this.onCollision(go);
		}
	}
	
	protected Shape getShapeByImage(Image img){
		Polygon res = new Polygon();
		if(img != null){
			switch(img.getResourceReference()){
				case "img/battleship.png":
					res.addPoint((img.getWidth()/2)*MonksRevengeGame.scale, 0);
					res.addPoint((img.getWidth())*MonksRevengeGame.scale, (img.getHeight()/2)*MonksRevengeGame.scale);
					res.addPoint((img.getWidth()/2)*MonksRevengeGame.scale, (img.getHeight())*MonksRevengeGame.scale);
					res.addPoint(0,(img.getHeight()/2)*MonksRevengeGame.scale);
					break;
				default:
					res.addPoint(0, 0);
					res.addPoint(this.getImage().getWidth()*MonksRevengeGame.scale, 0);
					res.addPoint(this.getImage().getWidth()*MonksRevengeGame.scale, this.getImage().getHeight()*MonksRevengeGame.scale);
					res.addPoint(0f,this.getImage().getHeight()*MonksRevengeGame.scale);
			}
		}
		return res;
	}

}