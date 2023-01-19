package GroupChat;

import java.io.IOException;
import java.util.ArrayList;

public class ServerThread extends Thread {

	public static ArrayList<ServerThread> serverThreads = new ArrayList<>();
	private Socket clientSocket;
	private Server server;
	private String clientUserName;

	public ServerThread(Socket socket, Server server) {
		try {
			this.server = server;
			this.clientSocket = socket;
			this.clientUserName = clientSocket.getInBR().readLine();
			serverThreads.add(this);
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
				if (clientSocket.getInBR().readLine().contains("h")) {
					clientSocket.getOutBR().write("hallöchen");
					clientSocket.getOutBR().newLine();
					clientSocket.getOutBR().flush();
				} // Wer ist onlne

			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public void versendeNachrichtAnJeden(String messageToSend) {

		for (int i = 0; i < serverThreads.size(); i++) {
			ServerThread mServerThread = serverThreads.get(i);
			try {
				if (!(mServerThread.clientUserName.equals(clientUserName))) {
					mServerThread.clientSocket.getOutBR().write(messageToSend);
					mServerThread.clientSocket.getOutBR().newLine();
					mServerThread.clientSocket.getOutBR().flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void entferneClient() {
		serverThreads.remove(this);
		versendeNachrichtAnJeden("Server: " + clientUserName + " hat den Gruppenchat verlassen.");
	}
}