package GroupChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Socket {
	private java.net.Socket mSocket;
	private String remoteHostIP;
	private int remotePort;
	private BufferedReader inBR;
	private BufferedWriter outBR;

	private PrintWriter out;

	public Socket(String remoteHostIP, int remotePort) {
		this.remoteHostIP = remoteHostIP;
		this.remotePort = remotePort;

		try {
			mSocket = new java.net.Socket(remoteHostIP, remotePort);
			inBR = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			out = new PrintWriter(mSocket.getOutputStream(), true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public BufferedReader getInBR() {
		return inBR;
	}

	public BufferedWriter getOutBR() {
		return outBR;
	}

	public Socket(java.net.Socket jSocket) throws IOException {
		mSocket = jSocket;

		inBR = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
		outBR = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream()));
		out = new PrintWriter(mSocket.getOutputStream(), true);

	}

	public boolean connect() throws UnknownHostException, IOException {
		mSocket = new java.net.Socket(remoteHostIP, remotePort);
		return true;
	}

	public int dataAvailable() throws IOException {
		return mSocket.getInputStream().available();
	}

	public int read() throws IOException {
		return inBR.read();
	}

	public int read(byte[] b, int len) throws IOException {
		char[] cbuf = new char[len];
		int anzahl = inBR.read(cbuf, 0, len);
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) cbuf[i];

		}
		return anzahl;
	}

	public String readLine() throws IOException {

		return inBR.readLine();
	}

	public String readLine(int max) throws IOException {
		byte[] bytes = new byte[max];
		read(bytes, max);
		return new String(bytes);
	}

	public void write(int b) {
		out.print(b);
	}

	public void write(byte[] b, int len) {
		char[] mChar = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			mChar[i] = (char) b[i];
		}
		out.print(mChar);

	}

	public void write(String s) {
		out.println(s);
	}

	public void close() throws IOException {
		mSocket.close();
	}

	public boolean isConnected() {
		return !mSocket.isClosed();
	}

	public void setTimeout(int milliseconds) throws SocketException {
		mSocket.setSoTimeout(milliseconds);
	}

	public String getRemoteHostIP() {
		String remoteSocket = mSocket.getRemoteSocketAddress().toString();
		remoteSocket = remoteSocket.substring(1);
		remoteSocket = remoteSocket.split(":")[0];
		return remoteSocket;
	}

	public String getLocalAddress() {

		return mSocket.getLocalAddress().toString();
	}
}