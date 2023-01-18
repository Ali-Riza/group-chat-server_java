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
			closeEveryThing(socket, socket.getInBR(), socket.getOutBR());
		}
	}

	public void ListenForMessage() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String msgFromGroupChat;

				while (socket.isConnected()) {
					try {
						msgFromGroupChat = bufferedReader.readLine();
						System.out.println(msgFromGroupChat);
					} catch (IOException e) {
						closeEveryThing(socket, bufferedReader, bufferedWriter);
					}

				}
			}
		}).start();
	}

	public void closeEveryThing(Socket socket, BufferedReader bufferedReader, BufferedWriter buffredWriter) {
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

	public static void main(String[] args) throws IOException {

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter your username for the chat groupe ");
			String username = scanner.nextLine();
			Socket socket = new Socket("localhost", 1234);
			Client client = new Client(socket, username);
			client.ListenForMessage();
			client.sendMessage();
		} catch (Exception e) {
			System.out.println("you are wrong !");
		}
	}

}