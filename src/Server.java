import java.io.IOException;
import java.util.ArrayList;

public class Server {
	private ArrayList<String> nachrichten = new ArrayList<>();

	public void start() {
		Thread t = new Thread(() -> {
			try {
				serverLoop();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		t.start();
	}

	public void serverLoop() throws IOException {

		ServerSocket Ss = new ServerSocket(8002);
		System.out.println("Server gestartet");
		;
		while (true) {
			System.out.println("Warte auf Client");
			try {
				Socket client = Ss.accept();
				System.out.println("Client empfangen");
				Thread t = new Thread(() -> handClient(client));
				t.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void handClient(Socket client) {
		try {
			String anfrage = client.readLine();
			System.out.println(anfrage);
			client.write(anfrage);

			while (true) {
				if (anfrage.startsWith("sendeNachricht")) {
					handleSendeNachricht(client, anfrage);
					break;
				}
				if (anfrage.startsWith("GetNeueNachrichten")) {
					handleGetNeueNachrichten(client, anfrage);
					break;
				}
				if (anfrage.startsWith("bye")) {
					handleQuit(client);
					break;
				}
				client.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void handleQuit(Socket client) throws IOException {
		System.out.println("close");
		client.close();
	}

	private void handleGetNeueNachrichten(Socket client, String anfrage) {
		if (nachrichten.size() >= 3) {
			for (int i = 0; i < 3; i++) {
				client.write(nachrichten.get(i));
			}
		}
	}

	private void handleSendeNachricht(Socket client, String anfrage) {
		String n = anfrage;
		ArrayList<String> messages = new ArrayList<>();

		if (anfrage.contains(":")) {
			for (int i = 0; i < 2; i++) {
				String[] arr = n.split(":");
				String nach = arr[1];

				messages.add(nach);
				nachrichten.add(nach);
			}
		}
	}
}
