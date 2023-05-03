/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package DecenteralisedElectionDeferentMachine1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Random;

/**
 *
 * @author FAYAW
 */
public class DecentralizedElectionClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 9000)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            // Send a request to the server to get the list of candidates
            objectOutputStream.writeObject("get_candidates");
            objectOutputStream.flush();

            // Read the list of candidates from the server
            List<String> candidates = (List<String>) objectInputStream.readObject();
            System.out.println("Candidates: " + candidates);

            // Vote for a random candidate
            Random random = new Random();
            String candidate = candidates.get(random.nextInt(candidates.size()));
            System.out.println("Voting for candidate: " + candidate);
            objectOutputStream.writeObject(candidate);
            objectOutputStream.flush();

            // Wait for the server to respond with the winner
            String winner = (String) objectInputStream.readObject();
            System.out.println("Winner: " + winner);

            objectInputStream.close();
            objectOutputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client exception: " + e.getMessage());
        }
    }
}
