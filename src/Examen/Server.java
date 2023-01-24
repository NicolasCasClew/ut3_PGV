package Examen;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Scanner;

public class Server extends Thread {
    Socket s = null;
    Scanner sc = new Scanner(System.in);
    public Server(Socket socket) {
        this.s = socket;
    }

    public static void main(String args[]) throws IOException,
            InterruptedException {

        System.out.println("Creando socket del servidor");
        ServerSocket serverSocket = new ServerSocket(26555);
        while (true) {
            try {
                System.out.println("Acepta conexiones");
                Socket newSocket = serverSocket.accept();

                new Server(newSocket).start();

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
            OutputStream os = s.getOutputStream();
            InputStreamReader isr = new InputStreamReader(is);
            //OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedReader br = new BufferedReader(isr);
            //BufferedWriter bw = new BufferedWriter(osw);

            String mensaje = br.readLine();
            os = s.getOutputStream();
            String enviar = sc.nextLine();
            os.write(("Servidor dice = "+enviar).getBytes());

            while (seguir) {
                if (mensaje.contains("Dowload")){

                }
               /** switch (mensaje.substring(0, 3)) {
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
                }**/
                mensaje = br.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}