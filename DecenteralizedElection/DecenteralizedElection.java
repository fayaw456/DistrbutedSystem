/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package DecenteralizedElection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author FAYAW
 */
public class DecenteralizedElection {

    private ArrayList<String> candidates;
    private Map<String, Integer> candidateVotes;
    private int totalVotes;
    private int quorum;

    /**
     * @param args the command line arguments
     */

    public DecenteralizedElection(ArrayList<String> candidates, int quorum) {
        this.candidates = candidates;
        this.quorum = quorum;
        this.candidateVotes = new HashMap<>();
        for (String candidate : candidates) {
            candidateVotes.put(candidate, 0);
        }
    }

    public void vote(String candidate) {
        if (candidates.contains(candidate)) {
            candidateVotes.put(candidate, candidateVotes.get(candidate) + 1);
            totalVotes++;
        }
    }

    public String getWinner() {
        String winner = null;
        int maxVotes = 0;
        for (String candidate : candidates) {
            int candidateVotes = this.candidateVotes.get(candidate);
            if (candidateVotes > maxVotes) {
                maxVotes = candidateVotes;
                winner = candidate;
            }
        }
        return winner;
    }

    public boolean isQuorumMet() {
        return totalVotes >= quorum;
    }

    public static void main(String[] args) {
        ArrayList<String> candidates = new ArrayList<>();
        candidates.add("Candidate A");
        candidates.add("Candidate B");
        candidates.add("Candidate C");

        DecenteralizedElection election = new DecenteralizedElection(candidates, 100);

        Random random = new Random();
        while (!election.isQuorumMet()) {
            int randomCandidateIndex = random.nextInt(candidates.size());
            String randomCandidate = candidates.get(randomCandidateIndex);
            election.vote(randomCandidate);
        }

        String winner = election.getWinner();
        System.out.println("The winner of the election is " + winner);
    }
}
