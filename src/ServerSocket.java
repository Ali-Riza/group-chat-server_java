import java.io.IOException;

public class ServerSocket {
	
	private java.net.ServerSocket mServerSocket;
	
	public ServerSocket(int port) throws IOException {
			mServerSocket = new java.net.ServerSocket(port);
	}
	
	public ServerSocket(int port, int timeout) throws IOException {
		mServerSocket = new java.net.ServerSocket(port);
		
		mServerSocket.setSoTimeout(timeout);
    }
	
	public Socket accept() throws IOException {

			Socket mSocket = new Socket(mServerSocket.accept());
			return mSocket;
	}
	
	public void close() throws IOException{
			mServerSocket.close();
	}
	


}
