/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package DecenteralisedElectionDeferentMachine0;

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

    private static final int PORT = 9000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<String> candidates = new ArrayList<>();
        candidates.add("Alice");
        candidates.add("Bob");
        candidates.add("Charlie");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());

                // Create a new thread to handle the client connection
                Thread thread = new Thread(() -> {
                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                        // Read the client's request
                        String request = (String) objectInputStream.readObject();
                        System.out.println("Received request: " + request);

                        // Send the list of candidates
                        if (request.equals("get_candidates")) {
                            System.out.println("Sending candidates: " + candidates);
                            objectOutputStream.writeObject(candidates);
                            objectOutputStream.flush();
                        }

                        // Read the client's vote
                        String vote = (String) objectInputStream.readObject();
                        System.out.println("Received vote: " + vote);

                        // Count the votes and determine the winner
                        int votesForAlice = 0;
                        int votesForBob = 0;
                        int votesForCharlie = 0;

                        for (String v : candidates) {
                            if (v.equals("Alice")) {
                                votesForAlice++;
                            } else if (v.equals("Bob")) {
                                votesForBob++;
                            } else if (v.equals("Charlie")) {
                                votesForCharlie++;
                            }
                        }

                        String winner;
                        if (votesForAlice >= votesForBob && votesForAlice >= votesForCharlie) {
                            winner = "Alice";
                        } else if (votesForBob >= votesForAlice && votesForBob >= votesForCharlie) {
                            winner = "Bob";
                        } else {
                            winner = "Charlie";
                        }

                        // Send the name of the winner to the client
                        System.out.println("Sending winner: " + winner);
                        objectOutputStream.writeObject(winner);
                        objectOutputStream.flush();

                        objectInputStream.close();
                        objectOutputStream.close();
                        socket.close();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Server exception: " + e.getMessage());
                    }
                });

                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }
}
