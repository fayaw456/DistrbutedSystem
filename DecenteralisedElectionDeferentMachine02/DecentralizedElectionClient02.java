/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package DecenteralisedElectionDeferentMachine02;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author FAYAW
 */
public class DecentralizedElectionClient02 {

    private static String serverAddress = "localhost";
    private static int portNumber = 1234;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(serverAddress, portNumber);
            System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            // Receive the list of candidates from the server
            List<String> candidates = (List<String>) inputStream.readObject();
            System.out.println("List of candidates:");
            for (String candidate : candidates) {
                System.out.println("- " + candidate);
            }

            // Prompt the user to vote for a candidate
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the name of the candidate you want to vote for: ");
            String vote = scanner.nextLine();

            // Send the vote to the server
            outputStream.writeObject(vote);
            System.out.println("Your vote for " + vote + " has been sent to the server.");

            // Close the streams and socket
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
