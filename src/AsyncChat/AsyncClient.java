package AsyncChat;



import java.io.*;
import java.net.*;
import java.util.Scanner;

public class AsyncClient {

    public static void main(String args[]) throws IOException, InterruptedException {
        int puertoser = 26555;
        String direccion = "localhost";
        // create DatagramSocket and get ip
        Socket cs = new Socket();
       InetSocketAddress addr =new InetSocketAddress("localhost", 26555);

        System.out.println("Running Client");
        cs.connect(addr);
        System.out.println("Client is Up....");

        // create a sender thread with a nested
        // runnable class definition
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
                                System.out.println("client "
                                        + "exiting... ");
                                //System.exit(0);
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