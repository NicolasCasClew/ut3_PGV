package chatPrivate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientCom extends Thread {

	static Canal mycomm;
	static MulticastSocket multi;
	static SocketAddress sockaddr;

	public static void main(String[] args) throws IOException {

		mycomm = new Canal();

		Scanner sc = new Scanner(System.in);
		System.out.print("Indica tu nombre: ");
		String username = sc.nextLine();

		sockaddr = new InetSocketAddress(mycomm.getMygroup(),
				mycomm.getMyport());

		multi = new MulticastSocket(mycomm.getMyport());

		multi.joinGroup(sockaddr, null); // para manejar conexiones por
											// diferentes interfaces

		String msg = username + "[" + InetAddress.getLocalHost() + "]"
				+ " se ha unido al grupo";

		DatagramPacket packOut = new DatagramPacket(msg.getBytes(),
				msg.length(), mycomm.getMygroup(), mycomm.getMyport());
		multi.send(packOut);

		String mens = "";
		
		//El Main del cliente sólo escucha 
		try {
			while (!mens.trim().equals("/")) {
				byte[] buf = new byte[1000];
				DatagramPacket packIn = new DatagramPacket(buf, buf.length);

				multi.receive(packIn);

				mens = new String(packIn.getData()).trim();
				System.out.println(mens);
				
				//Si detecta un Privado con su nombre lanza hilo y conecta por SocketStream

				if (mens.contains("Privado:" + username)) {
					new ClientCom().start();
				}

			}

			multi.leaveGroup(sockaddr, null);
		} catch (IOException e) { // gestionar
			System.err.println("Revisa el Host???");
		}
		multi.close();
		System.out.println("Socket cerrado");

	}

	public void run() {
		try {

			Socket clientSocket = new Socket();
			InetSocketAddress addr = new InetSocketAddress(
					mycomm.getPrivateaddr(), mycomm.getPrivateport());

			clientSocket.connect(addr);
			InputStream is = clientSocket.getInputStream();
			byte[] mens=new byte[100];
			is.read(mens);
			System.out.println((new String(mens)).trim());
			clientSocket.close();
		} catch (IOException e) {
			
		}

	}
}
