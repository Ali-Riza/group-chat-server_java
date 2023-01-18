package GroupChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class Client {

	private Socket socket;
	private String userName;

	public Client(Socket socket, String userName) {
		this.socket = socket;
		this.userName = userName;
	}

	public void sendMessage() {
		try {
			socket.getOutBR().write(userName);
			socket.getOutBR().newLine();
			socket.getOutBR().flush();

			Scanner scanner = new Scanner(System.in);
			while (socket.isConnected()) {
				String messageToSend = scanner.nextLine();
				socket.getOutBR().write(userName + ": " + messageToSend);
				socket.getOutBR().newLine();
				socket.getOutBR().flush();
			}
		} catch (IOException e) {
			closeEveryThing(socket);
		}
	}

	public void ListenForMessage() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String msgFromGroupChat;

				while (socket.isConnected()) {
					try {
						msgFromGroupChat = socket.getInBR().readLine();
						System.out.println(msgFromGroupChat);
					} catch (IOException e) {
						closeEveryThing(socket);
					}

				}
			}
		}).start();
	}

	public void closeEveryThing(Socket socket) {
		try {
			if (socket.getInBR() != null) {
				socket.getInBR().close();
			}
			if (socket.getOutBR() != null) {
				socket.getOutBR().close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);	
		System.out.println("Enter your username for the chat groupe ");
		String username = scanner.nextLine();	
		Socket socket = new Socket("localhost", 8002);
		Client client = new Client(socket, username);
		client.ListenForMessage();
		client.sendMessage();
	}

}