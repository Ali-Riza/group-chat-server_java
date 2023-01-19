package GroupChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ServerThread extends Thread {
<<<<<<< HEAD
	public static ArrayList<ServerThread> ServerThreads = new ArrayList<>();
=======
	
	public static ArrayList<ServerThread> serverThreads = new ArrayList<ServerThread>();
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
>>>>>>> 07b9a8c93f725127e2f4d2dcdd997757fe58bc34
	private String clientUserName;
	private Socket cs;
	private Server s;

	public ServerThread(Socket cs, Server s) {
		try {
<<<<<<< HEAD
			this.s = s;
			this.cs = cs;
			this.clientUserName = cs.getInBR().readLine();
			ServerThreads.add(this);
			broadcastMessage("SERVER: " + clientUserName + " has entred the chat!");
=======
			this.server = server;
			this.clientSocket = socket;
			this.bufferedWriter = clientSocket.getOutBR();
			this.bufferedReader = clientSocket.getInBR();
			this.clientUserName = bufferedReader.readLine();
			serverThreads.add(this);
			nachrichtAnJedenVersenden("SERVER: " + clientUserName + " hat den Gruppenchat betreten!");
>>>>>>> 07b9a8c93f725127e2f4d2dcdd997757fe58bc34
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		String messageFromClient;
		while (cs.isConnected()) {
			try {
<<<<<<< HEAD
				messageFromClient = cs.getInBR().readLine();
				broadcastMessage(messageFromClient);
			} catch (IOException e) {
				closeEveryThing(cs, cs.getInBR(), cs.getOutBR());
=======
				nachrichtVomClient = bufferedReader.readLine();
				nachrichtAnJedenVersenden(nachrichtVomClient);
			} catch (IOException e) {
				allesSchliessen(clientSocket, bufferedReader, bufferedWriter);
>>>>>>> 07b9a8c93f725127e2f4d2dcdd997757fe58bc34
				break;
			}
		}
	}

	public void broadcastMessage(String messageToSend) {
		for (ServerThread clientHandler : ServerThreads) {
			try {
<<<<<<< HEAD
				if (!clientHandler.clientUserName.equals(clientUserName)) {
					clientHandler.cs.getOutBR().write(messageToSend);
					clientHandler.cs.getOutBR().newLine();
					clientHandler.cs.getOutBR().flush();
				}
			} catch (IOException e) {
				closeEveryThing(cs, cs.getInBR(), cs.getOutBR());
=======
				if(!(mServerThread.clientUserName.equals(clientUserName))) {
					mServerThread.bufferedWriter.write(nachricht);
					mServerThread.bufferedWriter.newLine();;
					mServerThread.bufferedWriter.flush();
				}
			} catch (IOException e) {
				allesSchliessen(clientSocket, bufferedReader, bufferedWriter);
>>>>>>> 07b9a8c93f725127e2f4d2dcdd997757fe58bc34
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
