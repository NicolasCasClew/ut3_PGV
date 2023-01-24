package chatPrivate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class ServerCom extends Thread {

	static MulticastSocket multi;
	static SocketAddress sockaddr;

	public static void main(String[] args) throws IOException {

		Canal mycomm = new Canal();
		//Puedes usar una clase para atributos de comunicación
		//o declarar variables
		//En ESTE MODELO, la primera parte es idéntica para servidor o cliente

		String username = "Server";

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
		//En el hilo Main, siempre se lee de teclado y se reenvía por el multicast

		new ServerCom().start(); // La escucha de mensajes al grupo en un hilo

		// El servidor lee teclado

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String cad = "";
		System.out.println("<Server> te escucha ;-) : ");

		while (!(cad=(br.readLine()).trim()).equals("/")) {
			try {
				//Si es un privado, quita la info privada y prepara canal SocketStream
				if (cad.contains("Privado")) {
					int pos = cad.indexOf(":");
					pos = cad.indexOf(":", pos + 1);
					String mens = cad.substring(pos + 1);
					cad = cad.substring(0, pos);
					
					new PrivateCanal(mycomm, mens).start();
				}
				
				packOut = new DatagramPacket(cad.getBytes(), cad.length(),
						mycomm.getMygroup(), mycomm.getMyport());

				multi.send(packOut);
				System.out.println("<Server> te escucha ;-) : ");

			} catch (IOException e) {
			}
		}
		multi.close();
		System.out.println("Socket cerrado definitivamente");

	}

	public void run() {
		String mens = "";
		try {
			while (!mens.trim().equals("/")) {
				byte[] buf = new byte[1000];
				DatagramPacket packIn = new DatagramPacket(buf, buf.length);

				multi.receive(packIn);

				mens = new String(packIn.getData()).trim();
				System.out.println(mens);
			}

		} catch (IOException e) { // gestionar
			
		}
	}
}
