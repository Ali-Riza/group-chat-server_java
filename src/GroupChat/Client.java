package GroupChat;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Client {
	private Socket clientSocket;
	private String userName;

	public Client(Socket socket, String userName) {
		this.clientSocket = socket;
		this.userName = userName;
	}

	public void versendeNachricht() {
		try {
			clientSocket.getOutBR().write(userName);
			clientSocket.getOutBR().newLine();
			clientSocket.getOutBR().flush();
			Scanner scanner = new Scanner(System.in);
			while (clientSocket.isConnected()) {
				String nachricht = scanner.nextLine();
				DateFormat df = new SimpleDateFormat("HH:mm");
				Date dateobj = new Date();
				String aktuelleUhrzeit = df.format(dateobj);
				clientSocket.getOutBR().write(userName + " [" + aktuelleUhrzeit + "]: " + nachricht);
				clientSocket.getOutBR().newLine();
				clientSocket.getOutBR().flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void leseNachrichten() {
		Thread clientThread = new Thread(){ 
			public void run() {
				String nachrichtVomChat = new String();
				while (clientSocket.isConnected()) {
					try {
						nachrichtVomChat = clientSocket.getInBR().readLine();
						System.out.println(nachrichtVomChat);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		clientThread.start();
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