import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
	
	private ArrayList<Client> clients = new ArrayList<Client>();
	private int id;
	private ArrayList<String> nachrichten = new ArrayList<String>();
	private String userName;
	private Socket clientSocket;
	
	public Client(Socket socket, String userName) throws UnknownHostException, IOException {
		this.clientSocket = socket;
		id = clients.size() + 1;
		clients.add(this);
		this.userName = userName;
	}
	
	public void sendeNachricht(String nachricht) throws UnknownHostException, IOException {
		Scanner scanner = new Scanner(System.in);
		while(clientSocket.isConnected()) {
			nachricht = scanner.nextLine();
			String kNachricht = userName + ": " + nachricht;
			clientSocket.write(kNachricht);
			nachrichten.add(nachricht);
		}
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
		Socket clientSocket = new Socket("localhost", 8002);
		Client client = new Client(clientSocket, username);
		client.getNachricht(client.getId());
		String nachricht = scanner.nextLine();
		client.sendeNachricht(scanner.nextLine());
	}
}