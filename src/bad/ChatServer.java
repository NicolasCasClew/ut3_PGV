package bad;

import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        // Crear socket de servidor
        MulticastSocket server = new MulticastSocket(5555);

        // Unirse al grupo de multicast
        InetAddress group = InetAddress.getByName("230.0.0.1");
        server.joinGroup(group);

        // Crear buffer de mensaje
        byte[] buffer = new byte[1024];

        while (true) {
            // Recibir mensaje
            DatagramPacket message = new DatagramPacket(buffer, buffer.length);
            server.receive(message);

            // Obtener contenido del mensaje
            String msg = new String(message.getData());

            // Reenviar mensaje a todos los clientes
            server.send(new DatagramPacket(buffer, buffer.length, group, 5555));
        }
    }
}

