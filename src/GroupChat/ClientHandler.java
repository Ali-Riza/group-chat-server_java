package GroupChat;

import java.io.IOException;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
	
	private ArrayList<ClientHandler> ClientHandler = new ArrayList<>();
	private Socket socket;
	private String clientUserName;
	
	public ClientHandler(Socket socket) {
		try {
			this.socket = socket;
			this.clientUserName = socket.getInBR().readLine();
			ClientHandler.add(this);
			broadcastMessage("SERVER: "+ clientUserName + " hat den Gruppenchat beigetreten");
		} catch (IOException e) {
			closeEveryThing(socket);
		}
	}

	public void run() {
		String messageFromClient;
		while(socket.isConnected()) {
			try {
				messageFromClient = socket.getInBR().readLine();
				broadcastMessage(messageFromClient);
			} catch (IOException e) {
				closeEveryThing(socket);
				break;
			}
		}
	}
	
	public void broadcastMessage(String messageToSend) {
		for (ClientHandler clientHandler : ClientHandler) {
			try {
				if (!clientHandler.clientUserName.equals(clientUserName)) {
					clientHandler.socket.getOutBR().write(messageToSend);
					clientHandler.socket.getOutBR().newLine();
					clientHandler.socket.getOutBR().flush();
				}
			} catch (IOException e) {
				closeEveryThing(socket);
			}
		}
		
	}
	
	public void removeClientHandler() {
		ClientHandler.remove(this);
		broadcastMessage("Server: " + clientUserName + " hat den Gruppenchat verlassen");
	}
	
	public void closeEveryThing(Socket socket) {
		try {
			if (socket.getInBR() != null ) {
				socket.getInBR().close();
			}
			if (socket.getOutBR() != null ) {
				socket.getOutBR().close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}