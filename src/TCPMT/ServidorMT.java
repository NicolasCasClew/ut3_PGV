package TCPMT;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Calendar;
import java.util.Scanner;

public class ServidorMT extends Thread {
	Socket s = null;
	Scanner sc = new Scanner(System.in);
	public static int server_port = 26555;
	public ServidorMT(Socket socket) {
		this.s = socket;
	}

	public static void main(String args[]) throws IOException,
			InterruptedException {

		System.out.println("Creando socket del servidor");
		ServerSocket serverSocket = new ServerSocket(server_port);
		while (true) {
			try {
				System.out.println("Acepta conexiones");
				Socket newSocket = serverSocket.accept();

				new ServidorMT(newSocket).start();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}


	public void run() {
		boolean seguir = true;

		try {
			InetAddress origen = s.getInetAddress();
			System.out.println("Conexión recibida desde la IP " + origen
					+ " con el hilo: " + Thread.currentThread().getName());

			InputStream is = s.getInputStream();
			OutputStream os;
			InputStreamReader isr = new InputStreamReader(is);
			//OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedReader br = new BufferedReader(isr);
			//BufferedWriter bw = new BufferedWriter(osw);

			String mensaje = br.readLine();
			os = s.getOutputStream();
			String enviar = sc.nextLine();
			os.write(("Servidor dice = "+enviar).getBytes());

			while (seguir) {
			
				switch (mensaje.substring(0, 3)) {
				case "Nom":
					System.out.println("Hola " +mensaje.substring(4, mensaje.length()));
					break;
				case "Eco":
					System.out.println("Linea OK: " + mensaje.substring(4, mensaje.length()));
					break;
				case "Fin":
					System.out.println("El cliente "+Thread.currentThread().getName()+" se desconecta, "
							+ Calendar.getInstance().getTime());
					seguir = false;
					break;
				default:
					System.out.println("No conocemos este mensaje");
					break;
				}
				mensaje = br.readLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
