package Examen;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Creando socket cliente");
            Socket clientSocket = new Socket();

            System.out.println("Estableciendo la conexión");
            InetSocketAddress addr = new InetSocketAddress("localhost", 26555);
            clientSocket.connect(addr);

            OutputStream os;
            String mensuser = "";

            do {
                System.out.print("Dime: ");
                mensuser = sc.nextLine();
                os = clientSocket.getOutputStream();
                os.write((mensuser+"\n").getBytes());
                os.flush();
                //Thread.sleep(5000);

            } while (!mensuser.contains("###"));
            System.out.println("Conexion terminada");
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}