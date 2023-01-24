package chatPrivate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class PrivateCanal extends Thread{
	
	Canal comm;
	String mens;
	
	
	public PrivateCanal(Canal mycomm, String mens) {
		this.comm=mycomm;
		this.mens=mens;
	}

	public void run(){
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket();
			InetSocketAddress addr=new InetSocketAddress(comm.getPrivateaddr(),comm.getPrivateport());
			serverSocket.bind(addr);
			Socket newSocket= serverSocket.accept();
			
			String respuesta = "Privado " + mens;
			OutputStream os=newSocket.getOutputStream();
			os.write(respuesta.getBytes());
			os.flush();
			serverSocket.close();
				
} catch (IOException e) {
			
		}
		
	}
}
