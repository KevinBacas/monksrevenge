package network;

import objects.GamePackage;

public class ServerLauncher extends Thread {
	
	private GamePackage gp;
	
	
	public ServerLauncher(){
		
	}
	
	
	public void run(){
		
		this.sendInfossurlesjoueurspourlesjoueursparlesjoueursmaispaspourGiltaire();
		
	}
	
	
	private void sendInfossurlesjoueurspourlesjoueursparlesjoueursmaispaspourGiltaire() {
		
		
	}


	public static void main(String[] args){
		ServerLauncher sl = new ServerLauncher();
		sl.start();
	}
	
	

}
