package objects;

import monksrevenge.FakeGameContainer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

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
	private String image;

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

	/**      * Getter image */
	public Image getImage() {
		return GamePackage.getInstance().getResourceLoader().getImage(this.image);
	}

	/**      * Getter time_before */
	public int getTimeBeforeAcceleration(){
		return this.time_before_acceleration;
	}

	/**      * Largeur de l'objet */
	public int getWidth() {
		return (int) (1024*FakeGameContainer.getInstance().getScale());
	}
	
	/** 	* Hauteur de l'objet */
	public int getHeight(){
		return (int) (1024*FakeGameContainer.getInstance().getScale());
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
		this.getImage().draw(this.getXPos(), this.getYPos(), FakeGameContainer.getInstance().getScale());
		//ShapeRenderer.draw(this.getShape());
	}

	/**      * Setter friendly */
	public void setFriendly(boolean bool){
		this.friendly = bool;
	}

	/**      * Setter image */
	public void setImage(String img) {
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
	public void setXPos(int x){
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
	public abstract void update(int delta) throws SlickException;

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
		return !(this.getXPos() > -this.getWidth() && this.getYPos() > -this.getHeight() && this.getXPos() + this.getWidth() < FakeGameContainer.getInstance().getWidth()+this.getWidth() && this.getYPos() + this.getHeight() < FakeGameContainer.getInstance().getHeight()+this.getHeight());

	}
	
	/* Renvoi True si le gameObject est en dehors de l'écran */
	public boolean isOutOfScreen(){
		return !(this.getXPos() > 0 && this.getYPos() > 0 && this.getXPos() + this.getWidth() < FakeGameContainer.getInstance().getWidth() && this.getYPos() + this.getHeight() < FakeGameContainer.getInstance().getHeight());
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
	
	protected Shape getShapeByImage(String img){
		Polygon res = new Polygon();
		float scale = FakeGameContainer.getInstance().getScale();
		if(img == null){
			res.addPoint(0, 0);
			res.addPoint(0, this.getWidth());
			res.addPoint(this.getHeight(), this.getWidth());
			res.addPoint(this.getHeight(), 0);
		}else{
			switch(img){
			case "img/Hammerheadred.png":
			case "img/Hammerheadblue.png":
				res.addPoint(147 * scale, 175 * scale);
				res.addPoint(598 * scale, 151 * scale);
				res.addPoint(955 * scale , 335 * scale);
				res.addPoint(955 * scale , 636 * scale);
				res.addPoint(594 * scale , 811 * scale);
				res.addPoint(147 * scale, 727 * scale);
				break;		
			case "img/vaisseau1.png":
				res.addPoint(147 * scale, 175 * scale);
				res.addPoint(598 * scale, 151 * scale);
				res.addPoint(955 * scale , 335 * scale);
				res.addPoint(955 * scale , 636 * scale);
				res.addPoint(594 * scale , 811 * scale);
				res.addPoint(147 * scale, 727 * scale);
				break;
			case "img/vaisseau2.png":
				res.addPoint(162 * scale, 158 * scale);
				res.addPoint(582 * scale, 182 * scale);
				res.addPoint(1002 * scale , 280 * scale);
				res.addPoint(1020 * scale , 528 * scale);
				res.addPoint(968 * scale , 760 * scale);
				res.addPoint(600 * scale, 864 * scale);
				res.addPoint(110 * scale, 708 * scale);
				res.addPoint(32 * scale, 520 * scale);
				break;			
			case "img/vaisseau3.png":
				res.addPoint(202 * scale, 10 * scale);
				res.addPoint(970 * scale, 218 * scale);
				res.addPoint(1002 * scale , 366* scale);
				res.addPoint(948 * scale , 624 * scale);
				res.addPoint(692 * scale , 848 * scale);
				res.addPoint(18 * scale, 522 * scale);
				break;		
			case "img/vaisseau4.png":
				res.addPoint(16 * scale, 554 * scale);
				res.addPoint(446 * scale, 46 * scale);
				res.addPoint(962 * scale , 502 * scale);
				res.addPoint(578 * scale , 974 * scale);
				res.addPoint(90 * scale , 830 * scale);
				res.addPoint(147 * scale, 727 * scale);
				break;
			case "img/vaisseau5.png":
				res.addPoint(12 * scale, 472 * scale);
				res.addPoint(350 * scale, 184 * scale);
				res.addPoint(950 * scale , 332 * scale);
				res.addPoint(1018 * scale , 506 * scale);
				res.addPoint(972 * scale , 666 * scale);
				res.addPoint(466 * scale, 804 * scale);
				res.addPoint(94 * scale, 696 * scale);
				break;
			default:
				res.addPoint(0, 0);
				res.addPoint(0, this.getWidth());
				res.addPoint(this.getHeight(), this.getWidth());
				res.addPoint(this.getHeight(), 0);
				break;
			}
		}
		return res;
	}
	
	public String getImageName(){
		return this.image;
	}

}