package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public final class SingletonConnection {

	
    private static volatile SingletonConnection instance = null;
 
    
	private String host = "178.170.115.122";
	private int port = 80;
	
	private Socket s = null;
	private BufferedWriter writer = null;
	private BufferedReader reader = null;	
 
     /**
      * Constructeur de l'objet.
     * @throws IOException 
      */
    private SingletonConnection() throws IOException {
        s = new Socket(host, port);
		writer =  new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		reader = new BufferedReader(new InputStreamReader(s.getInputStream()));	
		
    }
 
    /**
     * Méthode permettant de renvoyer une instance de la classe Singleton
     * @return Retourne l'instance du singleton.
     * @throws IOException 
     */
    public final static SingletonConnection getInstance() throws IOException {
        if (SingletonConnection.instance == null) {
           synchronized(SingletonConnection.class) {
             if (SingletonConnection.instance == null) {
				SingletonConnection.instance = new SingletonConnection();
             }
           }
        }
        return SingletonConnection.instance;
    }

	public String readLine() throws IOException{
		String ligne;
		if ((ligne = reader.readLine()) != null){
			return ligne;
		}
		return "";
	}

	public void write(String str) throws IOException{
		if (str != null){
			writer.write(str);
			writer.flush();
		}
	}
	
	public void writeHTTPRequest(String strings) throws IOException{
		String str = "POST /uploadscore.php HTTP/1.1\n"+
					 "Host:"+this.host+":"+this.port + "\n" +
					 "Connection: Close" + "\n" +
					 "Content-type: application/x-www-form-urlencoded"+ "\n" +
					 "Content-Length: "+strings.length()+ "\n" +
					 "\n"+
					 strings+"\n";
		this.write(str);

	}
	
	public String readHTTPRequest() throws IOException{
		for (int i = 0; i<8; i++)
			this.readLine();
		return this.readLine();
	}	
	
	
	

 	public void destroy() throws IOException{
		s.close();
		writer.close();
		reader.close();
		SingletonConnection.instance = null;
	}
 	
 	
 	
 	public static void main(String[] args){
 		try {
 			SingletonConnection sc = SingletonConnection.getInstance();
 			sc.writeHTTPRequest("pseudocompte=test&mot_de_passe=test&highscore=210000000");
 			System.out.println(sc.readHTTPRequest());
 			sc.destroy();
 		}
 		catch (IOException io){
 			System.out.println(io.getMessage());
 		}
 	}
 	
 }

