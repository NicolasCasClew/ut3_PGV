package bad;
import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        // Crear socket de cliente
        MulticastSocket client = new MulticastSocket(5555);

        // Unirse al grupo de multicast
        InetAddress group = InetAddress.getByName("230.0.0.1");
        client.joinGroup(group);

        // Crear buffer de mensaje


        // Crear hilo para enviar mensajes
        Thread sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        byte[] buffer = new byte[1024];

                        // Leer mensaje del usuario
                        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                        String msg;
                        try {
                            msg = in.readLine();

                            // Enviar mensaje al servidor
                            buffer = msg.getBytes();
                            DatagramPacket message = new DatagramPacket(buffer, buffer.length, group, 5555);
                            client.send(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        // Crear hilo para recibir mensajes
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        byte[] buffer = new byte[1024];

                        try {
                            // Recibir mensaje del servidor
                            DatagramPacket recv = new DatagramPacket(buffer, buffer.length);
                            client.receive(recv);

                            // Mostrar mensaje
                            System.out.println(new String(recv.getData()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        // Iniciar hilos
        sendThread.start();
        receiveThread.start();
    }
}
