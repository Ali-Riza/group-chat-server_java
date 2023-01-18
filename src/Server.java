import java.io.IOException;

public class Server {

	public void start() {
		Thread t = new Thread(() -> serverLoop());
		t.start();

	}

	public void serverLoop() {

		ServerSocket Ss = null;
		try {
			Ss = new ServerSocket(8002);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (true) {
			try {
				System.out.println("Warte auf Client");
				Socket s = Ss.accept();
				s.setTimeout(1000); // --> Das ist wichtig, nicht zu viele Threads!
				Thread t = new Thread(() -> handClient(s));
				t.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void handClient(Socket s) {
		try {
			System.out.println("Client verbunden");

			String request = s.readLine();
			System.out.println("Nachricht vom Client: " + request);
			s.write(request);
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
