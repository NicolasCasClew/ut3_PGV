package Multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ClienteMulticast {
	public static void main(String args[]) throws IOException {
		String direccion="225.1.1.1";
		int puerto=6999;
		
		MulticastSocket m=new MulticastSocket(puerto);
		InetAddress grupo=InetAddress.getByName(direccion);
		m.joinGroup(grupo);
		System.out.println(InetAddress.getLocalHost()+" se ha unido al grupo");
		
		String mens="";
		
		while (!mens.trim().equals("/")){
			byte[] buf=new byte[1000];
			DatagramPacket paquete=new DatagramPacket(buf,buf.length);
			m.receive(paquete);
			mens=new String(paquete.getData());
			System.out.println("Mensaje recibido desde el servidor: " + mens.trim());
		}
		m.leaveGroup(grupo);
		m.close();
		System.out.println("Socket cerrado");
	}
}
