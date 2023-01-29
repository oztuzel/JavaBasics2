import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // we are getting the address we will send data to. we are getting localhost because server on same machine
            InetAddress address = InetAddress.getLocalHost(); // diffrent machine  .getByName()
            DatagramSocket datagramSocket = new DatagramSocket();

            Scanner scanner = new Scanner(System.in);
            String echoString;

            do {
                System.out.println("Enter string to be echoed: ");
                echoString = scanner.nextLine();

                byte[] buffer = echoString.getBytes();

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 5000);
                datagramSocket.send(packet);

            }while(!echoString.equals("exit"));


        } catch (SocketTimeoutException e){
            System.out.println("The socket timed out");
        } catch (IOException e){
            System.out.println("Client error: " + e.getMessage());
        }
    }
}