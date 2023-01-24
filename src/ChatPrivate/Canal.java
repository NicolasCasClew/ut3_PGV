package chatPrivate;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;


public class Canal {
	
	String multiaddr="225.10.10.10";
	String privateaddr="localhost";
	int myport=6998;
	int privateport=6666;
	InetAddress mygroup;
	SocketAddress sockaddr;
	MulticastSocket multisock;
	
	
	public Canal() throws IOException{
		this.mygroup = InetAddress.getByName(multiaddr);
	}
	
	protected int getMyport() {
		return myport;
	}
	protected InetAddress getMygroup() {
		return mygroup;
	}
	
	protected SocketAddress getSockaddr() {
		return sockaddr;
	}
	protected MulticastSocket getMultiaddr() {
		return multisock;
	}

	protected MulticastSocket getMultisock() {
		return multisock;
	}
	
	protected int getPrivateport() {
		return privateport;
	}

	protected String getPrivateaddr() {
		return privateaddr;
	}

}
