package Examen_PGV;



import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    public static void main(String args[]) throws IOException, InterruptedException {
        int puertoser = 26555;
        String direccion = "localhost";
        Socket cs = new Socket();
        InetSocketAddress addr =new InetSocketAddress(direccion, puertoser);

        System.out.println("Comenzando Cliente");
        cs.connect(addr);
        System.out.println("Cliente Encendidp....");


        Thread csend;
        csend = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OutputStream os;
                    String mensuser = "";
                    Scanner sc = new Scanner(System.in);
                    while (true) {
                        synchronized (this) {
                            mensuser = sc.nextLine();
                            if (mensuser.contains("###")) {
                                System.out.println("Cerrando cliente... ");
                                break;
                            }
                            os = cs.getOutputStream();
                            os.write((mensuser + "\n").getBytes());
                            os.flush();

                        }
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        });


        Thread creceive;
        creceive = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        try {
                            InputStream is = cs.getInputStream();

                            byte[] mensaje = new byte[250];
                            is.read(mensaje);
                            String msg = (new String(mensaje)).trim();
                            System.out.println("Server: " + msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        csend.start();
        creceive.start();

        csend.join();
        creceive.join();
    }
}