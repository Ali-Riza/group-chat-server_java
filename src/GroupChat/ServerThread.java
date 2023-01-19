package GroupChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ServerThread extends Thread {
	public static ArrayList<ServerThread> ServerThreads = new ArrayList<>();
	private String clientUserName;
	private Socket cs;
	private Server s;

	public ServerThread(Socket cs, Server s) {
		try {
			this.s = s;
			this.cs = cs;
			this.clientUserName = cs.getInBR().readLine();
			ServerThreads.add(this);
			broadcastMessage("SERVER: " + clientUserName + " has entred the chat!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		String messageFromClient;
		while (cs.isConnected()) {
			try {
				messageFromClient = cs.getInBR().readLine();
				broadcastMessage(messageFromClient);
			} catch (IOException e) {
				closeEveryThing(cs, cs.getInBR(), cs.getOutBR());
				break;
			}
		}
	}

	public void broadcastMessage(String messageToSend) {
		for (ServerThread clientHandler : ServerThreads) {
			try {
				if (!clientHandler.clientUserName.equals(clientUserName)) {
					clientHandler.cs.getOutBR().write(messageToSend);
					clientHandler.cs.getOutBR().newLine();
					clientHandler.cs.getOutBR().flush();
				}
			} catch (IOException e) {
				closeEveryThing(cs, cs.getInBR(), cs.getOutBR());
			}
		}
	}

	public void removeClientHandler() {
		ServerThreads.remove(this);
		broadcastMessage("Server: " + clientUserName + "has left the chat");
	}

	public void closeEveryThing(Socket cs, BufferedReader bufferedReader, BufferedWriter buffredWriter) {
		try {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (cs.getOutBR() != null) {
				cs.getOutBR().close();
			}
			if (cs != null) {
				cs.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
