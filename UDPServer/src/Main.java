import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Main {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(5000);

            while(true) {
                byte[] buffer = new byte[50];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                System.out.println("Text received is: " + new String(buffer,0, packet.getLength()));
                // new String(buffer) is contains null character in the buffer

//                // if we send data to client, we can do this
//                String returnString = "echo: " + new String(buffer,0, packet.getLength());
//                byte[] buffer2 = returnString.getBytes();
//                // we extract address and port from packet which client sending
//                InetAddress address = packet.getAddress();
//                int port = packet.getPort();
//                // we create packet it contains data and we send packet(data) to client
//                DatagramPacket packet2 = new DatagramPacket(buffer2,buffer2.length, address, port);
//                socket.send(packet2);
            }
        }catch (SocketException e){
            System.out.println("Socket exception : " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO exception: " + e.getMessage());
        }
    }
}