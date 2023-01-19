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

	public void versendeNachricht() {
		try {
			socket.getOutBR().write(userName);
			socket.getOutBR().newLine();
			socket.getOutBR().flush();
			Scanner scanner = new Scanner(System.in);
			while (socket.isConnected()) {
				String nachricht = scanner.nextLine();
				socket.getOutBR().write(userName + ": " + nachricht);
				socket.getOutBR().newLine();
				socket.getOutBR().flush();
			}
		} catch (IOException e) {
			allesSchliessen(socket, socket.getInBR(), socket.getOutBR());
		}
	}

	public void leseNachrichten() {
		new Thread(new Runnable() {
			public void run() {
				String nachrichtVomChat = new String();
				while (socket.isConnected()) {
					try {
						nachrichtVomChat = socket.getInBR().readLine();
						System.out.println(nachrichtVomChat);
					} catch (IOException e) {
						allesSchliessen(socket, socket.getInBR(), socket.getOutBR());
					}
				}
			}
		}).start();
	}

	public void allesSchliessen(Socket socket, BufferedReader bufferedReader, BufferedWriter buffredWriter) {
		try {
			if (bufferedReader != null) {
				bufferedReader.close();
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
		try {
			System.out.println("Bitte gebe deinen Usernamen ein!");
			Scanner scanner = new Scanner(System.in);
			String username = scanner.nextLine();
			Socket socket = new Socket("localhost", 8002);
			Client client = new Client(socket, username);
			client.leseNachrichten();
			client.versendeNachricht();
		} catch (Exception e) {
			System.out.println("Das funktioniert nicht");
		}
	}
}