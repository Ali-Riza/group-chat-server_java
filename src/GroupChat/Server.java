package GroupChat;

import java.io.IOException;

public class Server {

	private ServerSocket serverSocket;
	private int port;
	private boolean isRunning;

	public Server() {
		serverSocket = null;
		isRunning = false;
	}

	public void startServer(int port) {
		this.port = port;
		isRunning = true;
		Thread t = new Thread(() -> {
			try {
				runServer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		t.start();
	}
	
	public void stopServer() {
		isRunning = false;
	}

	public void runServer() throws IOException {
		serverSocket = new ServerSocket(port);
		while (isRunning) {
			try {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Ein neuer Client hat sich verbunden");
				ClientHandler clientHandler = new ClientHandler(clientSocket);
				Thread thread = new Thread(clientHandler);
				thread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.startServer(8002);
	}
}