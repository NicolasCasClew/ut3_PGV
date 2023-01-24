package Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClienteSocketStream {
	public static void main(String args[]) throws IOException {

		String[] vecmens = { "Hi 1", "Simulo el 2", "exit" };

		for (int i = 0; i < vecmens.length; i++) {
			System.out.println("Creando nuevo socket cliente");
			Socket clientSocket = new Socket();

			System.out.println("Estableciendo la conexión");
			InetSocketAddress addr = new InetSocketAddress("localhost", 5544);

			clientSocket.connect(addr);

			InputStream is = clientSocket.getInputStream();
			OutputStream os = clientSocket.getOutputStream();

			System.out.println("Enviando mensaje " + i);

			os.write(vecmens[i].getBytes());

			System.out.println("Mensaje enviado");
			System.out.println("Cerrando socket");
			clientSocket.close();
		}
	}
}
