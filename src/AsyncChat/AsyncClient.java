package AsyncChat;



import java.io.*;
import java.net.*;
import java.util.Scanner;

public class AsyncClient {

    public static void main(String args[]) throws IOException, InterruptedException {
        int puertoser = 5999;
        String direccion = "255.1.1.1";
        // create DatagramSocket and get ip
        MulticastSocket cs = new MulticastSocket(puertoser);
        InetAddress ip=InetAddress.getByName(direccion);
        cs.joinGroup(ip);
        System.out.println("Running AsyncClient.java");

        System.out.println("Client is Up....");

        // create a sender thread with a nested
        // runnable class definition
        Thread csend;
        csend = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    Scanner sc = new Scanner(System.in);
                    while (true) {
                        synchronized (this)
                        {
                            byte[] sd = new byte[1000];

                            // scan new message to send
                            sd = sc.nextLine().getBytes();

                            // create datagram packet
                            // for new message
                            DatagramPacket sp
                                    = new DatagramPacket(
                                    sd,
                                    sd.length,
                                    ip,
                                    1234);

                            // send the new packet
                            cs.send(sp);

                            String msg = new String(sd);
                            System.out.println("Client says: "
                                    + msg);
                            // exit condition
                            if (msg.equals("bye")) {
                                System.out.println("client "
                                        + "exiting... ");
                                break;
                            }
                            System.out.println("Waiting for "
                                    + "server response...");
                        }
                    }
                }
                catch (IOException e) {
                    System.out.println("Exception occurred");
                }
            }
        });

        // create a receiver thread with a nested
        // runnable class definition
        Thread creceive;
        creceive = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    while (true) {
                        synchronized (this)
                        {

                            byte[] rd = new byte[1000];

                            // receive new message
                            DatagramPacket sp1
                                    = new DatagramPacket(
                                    rd,
                                    rd.length);
                            cs.receive(sp1);

                            // convert byte data to string
                            String msg = (new String(rd)).trim();
                            System.out.println("Server: " + msg);

                            // exit condition
                            if (msg.equals("bye")) {
                                System.out.println("Server"
                                        + " Stopped....");
                                break;
                            }
                        }
                    }
                }
                catch (IOException e) {
                    System.out.println("Exception occurred");
                }
            }
        });

        csend.start();
        creceive.start();

        csend.join();
        creceive.join();
    }
}