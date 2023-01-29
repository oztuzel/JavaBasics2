import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // main method start in the same way that the one for the server did except we create Socket not a ServerSocket
        try(Socket socket = new Socket("localhost",5000)){
            // Socket constructor 1. parameter is the adress of the host we want to connect , 2. port number
            // if "localhost" don't work, we can type  "127.0.0.1"

             socket.setSoTimeout(6000);

            BufferedReader echoes = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter stringToEcho = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            String echoString;
            String response;

            do {
                // asking string
                System.out.println("Enter string to be echoed: ");
                echoString = scanner.nextLine();

                //sending to the server
                stringToEcho.println(echoString);

                if(!echoString.equals("exit")){
                    // reading data coming from the server
                    response = echoes.readLine();
                    System.out.println(response);
                }
            }while(!echoString.equals("exit"));

        } catch (SocketTimeoutException e){
            System.out.println("The socket timed out");
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}