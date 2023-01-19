package GroupChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ServerThread extends Thread {
	
	public static ArrayList<ServerThread> serverThreads = new ArrayList<ServerThread>();
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String clientUserName;
	private Socket clientSocket;
	private Server server;

	public ServerThread(Socket socket, Server server) {
		try {
			this.server = server;
			this.clientSocket = socket;
			this.bufferedWriter = clientSocket.getOutBR();
			this.bufferedReader = clientSocket.getInBR();
			this.clientUserName = bufferedReader.readLine();
			serverThreads.add(this);
			nachrichtAnJedenVersenden("SERVER: " + clientUserName + " hat den Gruppenchat betreten!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		String nachrichtVomClient = new String();
		while (clientSocket.isConnected()) {
			try {
				nachrichtVomClient = bufferedReader.readLine();
				nachrichtAnJedenVersenden(nachrichtVomClient);
			} catch (IOException e) {
				allesSchliessen(clientSocket, bufferedReader, bufferedWriter);
				break;
			}
		}
	}

	public void nachrichtAnJedenVersenden(String nachricht) {
		
		for(int i = 0; i < serverThreads.size(); i++) {
			ServerThread mServerThread = serverThreads.get(i);
			try {
				if(!(mServerThread.clientUserName.equals(clientUserName))) {
					mServerThread.bufferedWriter.write(nachricht);
					mServerThread.bufferedWriter.newLine();;
					mServerThread.bufferedWriter.flush();
				}
			} catch (IOException e) {
				allesSchliessen(clientSocket, bufferedReader, bufferedWriter);
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
			if (bufferedWriter != null) {
				bufferedWriter.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}