package Ej1;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, RuntimeException {


        Thread socket ;
        socket = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Creando socket del servidor");
                ServerSocket serverSocket;
                try {
                    serverSocket = new ServerSocket();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Realizando el bind");
                InetSocketAddress addr = new InetSocketAddress("localhost", 5544);
                try {
                    serverSocket.bind(addr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                boolean seguir=true;
                while (seguir){
                    System.out.println("Acepta conexiones");
                    Socket newSocket= null;
                    try {
                        newSocket = serverSocket.accept();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("Conexión recibida");
                    InputStream is = null;
                    try {
                        is = newSocket.getInputStream();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    byte[] mensaje=new byte[25];
                    try {
                        is.read(mensaje);
                        if(!(is.read(mensaje) == 0)){

                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    String smens=(new String(mensaje)).trim();

                    System.out.println("Mensaje recibido: "+ smens);
                    System.out.println("Cerramos el socket para escuchar al cliente");
                    try {
                        newSocket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                    if (smens.equals("exit")) seguir=false;
                }

                System.out.println("Y cerrando el socket del servidor");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
    });

        Thread datagram;
        datagram = new Thread(new Runnable() {
            @Override
            public void run()
            {

            }
        });
        socket.start();
    }
}
