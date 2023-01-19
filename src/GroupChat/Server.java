package GroupChat;

import java.io.IOException;

public class Server {

	private ServerSocket serverSocket;

	public Server() {
		serverSocket = null;
	}

	public void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(8002);
		Thread t = new Thread(() -> startServer());
		t.start();
	}

	public void startServer() {

		while (true) {
			try {
				Socket socket = serverSocket.accept();
				System.out.println("A new client has connected ");
				ClientHandler clientHandler = new ClientHandler(socket);
				Thread thread = new Thread(clientHandler);
				thread.start();
			} catch (IOException e) {

			}
		}
	}

	public void closeServerSocket() {
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {

		Server server = new Server(serverSocket);
		server.startServer();
	}
}