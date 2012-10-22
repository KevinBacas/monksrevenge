package objects;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class ResourceLoader {

	private HashMap<String,Image> hashIMG = new HashMap<String,Image>();
	private HashMap<String,Sound> hashSS = new HashMap<String,Sound>();
	
	public ResourceLoader(){
		
		ArrayList<String> iResources = this.getArrayImageResource();
		for (String s: iResources){
			try {
				this.hashIMG.put(s, new Image(s));
			} catch (SlickException e) {
				System.out.println("Image invalide");
			}
		}
		
		ArrayList<String> sResources = this.getArraySoundResource();
		for (String s: sResources){
			try {
				this.hashSS.put(s, new Sound(s));
			} catch (SlickException e) {
				System.out.println("Son invalide");
			}
		}
		
		
	}

	private  ArrayList<String> getArrayImageResource() { 
		
		ArrayList<String> listfinale = new ArrayList<String>();
		
		File f = new File("img");
		String [] listefichiers;
		int i; 
		listefichiers = f.list();
		for(i=0;i<listefichiers.length;i++){
			if(listefichiers[i].endsWith(".png") || listefichiers[i].endsWith(".jpg")){ 
				listfinale.add("img/"+listefichiers[i]);
			}
		}
		return listfinale;
	}
	
	private  ArrayList<String> getArraySoundResource() { 
		
		ArrayList<String> listfinale = new ArrayList<String>();
		
		File f = new File("sound");
		String [] listefichiers;
		int i; 
		listefichiers = f.list();
		for(i=0;i<listefichiers.length;i++){
			if(listefichiers[i].endsWith(".wav") || listefichiers[i].endsWith(".ogg")){ 
				listfinale.add("sound/"+listefichiers[i]);
			}
		}
		return listfinale;
	}
	
	public Image getImage(String s){
		return this.hashIMG.get(s);
	}
	
	
	public Sound getSound(String sound){
		return this.hashSS.get(sound);
	}
	
	
}
