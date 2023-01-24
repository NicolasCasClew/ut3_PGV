package Examen_PGV;

import Examen.Server;

import java.net.*;
import java.io.*;
import java.util.*;

public class Servidor {

    public static void main(String args[]) throws IOException, InterruptedException {

        int puertoser = 26555;
        String direccion = "localhost";

        ServerSocket cs = new ServerSocket(puertoser);
        InetSocketAddress addr =new InetSocketAddress(direccion, puertoser);

        System.out.println("Running Server");
        Socket newSocket = cs.accept();
        new Server(newSocket).start();

        System.out.println("Server is Up....");


        Thread sreceive;
        sreceive = new Thread(new Runnable() {
            @Override
            public void run()
            {
                while (true) {
                    synchronized (this) {
                        try {
                            InputStream is = newSocket.getInputStream();

                            byte[] mensaje = new byte[250];
                            is.read(mensaje);
                            String msg = (new String(mensaje)).trim();

                            autoRespuesta(msg, newSocket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        //ssend.start();
        sreceive.start();

        //ssend.join();
        sreceive.join();
    }

    public static void autoRespuesta(String msg, Socket newSocket){
        try {

            OutputStream os;
            Scanner sc = new Scanner(System.in);

            while (true) {

                String auto = "OK Entendido";

                if (msg.contains("Download")) {
                    String[] down = msg.split(":");

                    auto = "OK Entendido, descargando "+ down[1]+ " ahora \nSe han descargado "+random()+" archivos";
                    os = newSocket.getOutputStream();
                    os.write((auto + "\n").getBytes());
                    os.flush();
                }else {

                    auto = "OK Entendido";
                    os = newSocket.getOutputStream();
                    os.write((auto + "\n").getBytes());
                    os.flush();
                }

                return;

            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static int random(){
        Random r = new Random();
        int low = 5;
        int high = 1000;
        int result = r.nextInt(high-low) + low;


        return result;
    }

}

