package GroupChat;

import java.io.IOException;
import java.util.ArrayList;

public class ServerThread extends Thread {
	
	public static ArrayList<ServerThread> clientHandler = new ArrayList<>();
	private Socket clientSocket;
	private Server server;
	private String clientUserName;

	public ServerThread(Socket socket, Server server) {
		try {
			this.server = server;
			this.clientSocket = socket;
			this.clientUserName = clientSocket.getInBR().readLine();
			clientHandler.add(this);
			versendeNachrichtAnJeden("SERVER: " + clientUserName + " hat den Gruppenchat betreten!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		String nachrichtVomClient = new String();
		while (clientSocket.isConnected()) {
			try {
				nachrichtVomClient = clientSocket.getInBR().readLine();
				versendeNachrichtAnJeden(nachrichtVomClient);
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public void versendeNachrichtAnJeden(String nachricht) {
		
		for(int i = 0; i < clientHandler.size(); i++) {
			ServerThread mServerThread = clientHandler.get(i);
			try {
				if (!(mServerThread.clientUserName.equals(clientUserName))) {
					mServerThread.clientSocket.getOutBR().write(nachricht);
					mServerThread.clientSocket.getOutBR().newLine();
					mServerThread.clientSocket.getOutBR().flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void entferneClient() {
		clientHandler.remove(this);
		versendeNachrichtAnJeden("Server: " + clientUserName + " hat den Gruppenchat verlassen.");
	}
}