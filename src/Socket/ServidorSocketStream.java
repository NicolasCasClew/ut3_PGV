package Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;

public class ServidorSocketStream {
	public static void main(String args[]) throws IOException {

		System.out.println("Creando socket del servidor");
		ServerSocket serverSocket= new ServerSocket();
		

		System.out.println("Realizando el bind");			
		InetSocketAddress addr=new InetSocketAddress("localhost",5544);
		serverSocket.bind(addr);

boolean seguir=true;
while (seguir){
		System.out.println("Acepta conexiones");
		Socket newSocket= serverSocket.accept();

		System.out.println("Conexión recibida");
		InputStream is = newSocket.getInputStream();

		byte[] mensaje=new byte[25];
		is.read(mensaje);
		
		String smens=(new String(mensaje)).trim();

		System.out.println("Mensaje recibido: "+ smens);
		System.out.println("Cerramos el socket para escuchar al cliente");
		newSocket.close();
			
		
		if (smens.equals("exit")) seguir=false;
}

		System.out.println("Y cerrando el socket del servidor");
		serverSocket.close();
	}
}
