package monksrevenge;

public final class FakeGameContainer {
	 
    private static volatile FakeGameContainer instance = null;

    private int width;
    private int height;
    private float scale;

	private float posXScale;
	private float posYScale;

    /**
     * Constructeur de l'objet.
     */
    private FakeGameContainer(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        this.scale = (float) (this.getWidth() / 1920f * 0.10);
        this.posXScale = this.getWidth() / 1920;
        this.posYScale = this.getHeight() / 1080;
    }

    /**
     * Méthode permettant de renvoyer une instance de la classe Singleton
     * @return Retourne l'instance du singleton.
     */
    public final static FakeGameContainer getInstance() {
        if (FakeGameContainer.instance == null) {
           synchronized(FakeGameContainer.class) {
             if (FakeGameContainer.instance == null) {
               FakeGameContainer.instance = new FakeGameContainer(1920, 1080);
             }
           }
        }
        return FakeGameContainer.instance;
    }
    
    public void setWidth(int w){
    	this.width = w;
    	this.posXScale = w / 1920f;
    	this.scale = (float) (this.getWidth() / 1920f * 0.10f);
    }
    
    public void setHeight(int h){
    	this.height = h;
    	this.posYScale = h / 1080f;
    }
	
	public int getHeight(){
		return this.height;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public float getScale(){
		return this.scale;
	}
	
	public float getPosXScale(){
		return this.posXScale;
	}
	
	public float getPosYScale(){
		return this.posYScale;
	}

}
