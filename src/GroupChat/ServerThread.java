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
>>>>>>> 383e5119506e731e7d6c0def6e7a0518ce7e8dc7
	private String clientUserName;
	private Socket clientSocket;
	private Server server;

	public ServerThread(Socket socket, Server server) {
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
>>>>>>> 383e5119506e731e7d6c0def6e7a0518ce7e8dc7
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		String nachrichtVomClient = new String();
		while (clientSocket.isConnected()) {
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
>>>>>>> 383e5119506e731e7d6c0def6e7a0518ce7e8dc7
				break;
			}
		}
	}

	public void nachrichtAnJedenVersenden(String nachricht) {
		
		for(int i = 0; i < serverThreads.size(); i++) {
			ServerThread mServerThread = serverThreads.get(i);
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
>>>>>>> 383e5119506e731e7d6c0def6e7a0518ce7e8dc7
			}
		}
		
//		for (ServerThread clientHandler : ServerThreads) {
//			
//			try {
//				if (!clientHandler.clientUserName.equals(clientUserName)) {
//					clientHandler.bufferedWriter.write(nachricht);
//					clientHandler.bufferedWriter.newLine();
//					clientHandler.bufferedWriter.flush();
//				}
//			} catch (IOException e) {
//				allesSchliessen(clientSocket, bufferedReader, bufferedWriter);
//			}
//		}
	}

	public void removeClientHandler() {
		serverThreads.remove(this);
		nachrichtAnJedenVersenden("Server: " + clientUserName + " hat den Gruppenchat verlassen.");
	}

	public void allesSchliessen(Socket socket, BufferedReader bufferedReader, BufferedWriter buffredWriter) {
		try {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (cs.getOutBR() != null) {
				cs.getOutBR().close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}