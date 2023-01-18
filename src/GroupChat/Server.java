package GroupChat;
import java.io.IOException;

public class Server {
	
	private int port;
	private boolean isRunning = false;
	private ServerSocket serverSocket;

	public Server() {
		
	}
	
	public void startServer(int port) {
		this.port = port;
		isRunning = true;
		Thread serverThread = new Thread(()-> {
			try {
				serverLoop();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		serverThread.start();
	}
	
	public void stoppServer() {
		isRunning = false;
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void serverLoop() throws IOException {
		serverSocket = new ServerSocket(port);
		System.out.println("Server gestartet");
		
		while(isRunning) {
			System.out.println("Warte auf Client");
			try {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Ein neuer Client hat sich verbunden");
				ClientHandler clientHandler = new ClientHandler(clientSocket); 
				Thread thread = new Thread(clientHandler);
				thread.start();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	public static void main(String [] args) {
		Server s = new Server();
		s.startServer(8002);
	}

}
