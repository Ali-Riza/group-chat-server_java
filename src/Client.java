import java.util.ArrayList;

public class Client {
	
	private ArrayList<Client> clients = new ArrayList<Client>();
	private int id;
	private int port;
	private ArrayList<String> nachrichten = new ArrayList<String>();
	
	public Client(int port) {
		id = clients.size() + 1;
		clients.add(this);
		this.port = port;
	}
	
	public void sendeNachricht(String nachricht) {
		Socket clientSocket = new Socket("localhost",port);
		clientSocket.write(nachricht);
		
		if(nachricht.contains(":")) {
			String[] arr = nachricht.split(":");
		}
	}
	
}
