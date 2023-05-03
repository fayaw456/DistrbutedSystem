/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package DecenteralisedElectionDeferentMachine01;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author FAYAW
 */
public class DecentralizedElectionServer {

    private static int portNumber = 1234;
    private static List<String> candidates = new ArrayList<>();
    private static List<String> votes = new ArrayList<>();
    private static int totalVotes = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Populate the list of candidates
        candidates.add("Alice");
        candidates.add("Bob");
        candidates.add("Charlie");
        candidates.add("Dave");

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server is running and listening to port " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

                            // Send list of candidates to the client
                            outputStream.writeObject(candidates);

                            // Receive the client's vote
                            String vote = (String) inputStream.readObject();
                            System.out.println("Received vote: " + vote);
                            votes.add(vote);
                            totalVotes++;

                            // If all clients have voted, calculate the winner
                            if (totalVotes == 3) {
                                System.out.println("All clients have voted. Calculating winner...");
                                String winner = calculateWinner();
                                System.out.println("The winner is: " + winner);
                            }

                            // Close the streams and socket
                            outputStream.close();
                            inputStream.close();
                            clientSocket.close();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String calculateWinner() {
        int maxVotes = 0;
        String winner = "";
        for (String candidate : candidates) {
            int candidateVotes = 0;
            for (String vote : votes) {
                if (vote.equals(candidate)) {
                    candidateVotes++;
                }
            }
            System.out.println(candidate + " received " + candidateVotes + " votes.");
            if (candidateVotes > maxVotes) {
                maxVotes = candidateVotes;
                winner = candidate;
            }
        }
        return winner;
    }
}
