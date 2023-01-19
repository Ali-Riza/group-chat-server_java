package GroupChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ServerThread extends Thread implements Runnable {
	public static ArrayList<ServerThread> ServerThreads = new ArrayList<>();
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String clientUserName;
	private Socket cs;
	private Server s;

	public ServerThread(Socket cs, Server s) {
		try {
			this.s = s;
			this.cs = cs;
			this.bufferedWriter = cs.getOutBR();
			this.bufferedReader = cs.getInBR();
			this.clientUserName = bufferedReader.readLine();
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
				messageFromClient = bufferedReader.readLine();
				broadcastMessage(messageFromClient);
			} catch (IOException e) {
				closeEveryThing(cs, bufferedReader, bufferedWriter);
				break;
			}
		}
	}

	public void broadcastMessage(String messageToSend) {
		for (ServerThread clientHandler : ServerThreads) {
			try {
				if (!clientHandler.clientUserName.equals(clientUserName)) {
					clientHandler.bufferedWriter.write(messageToSend);
					clientHandler.bufferedWriter.newLine();
					clientHandler.bufferedWriter.flush();
				}
			} catch (IOException e) {
				closeEveryThing(cs, bufferedReader, bufferedWriter);
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
			if (bufferedWriter != null) {
				bufferedWriter.close();
			}
			if (cs != null) {
				cs.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
