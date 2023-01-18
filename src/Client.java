import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
	
	private ArrayList<Client> clients = new ArrayList<Client>();
	private int id;
	private int port;
	private ArrayList<String> nachrichten = new ArrayList<String>();
	private String userName;
	
	public Client(int port, String userName) {
		id = clients.size() + 1;
		clients.add(this);
		this.port = port;
		this.userName = userName;
	}
	
	public void sendeNachricht(String nachricht) throws UnknownHostException, IOException {
		Socket clientSocket = new Socket("localhost",port);
		Scanner scanner = new Scanner(System.in);
		nachricht = scanner.nextLine();
		//clientSocket.write(userName + ": " + nachricht);
	}

	public int getId() {
		return id;
	}

	public ArrayList<String> getNachrichten() {
		return nachrichten;
	}
	
	public void getNachricht(int id) {
		for(int i = 0; i < clients.size(); i++) {
			if(clients.get(i).getId() == id) {
				clients.get(i).getNachrichten();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your username for the chat groupe ");
		String username = scanner.nextLine();
		Client client = new Client(8002, username);
		client.getNachricht(client.getId());
		client.sendeNachricht("Hallo");
	}
}