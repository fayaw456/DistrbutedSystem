/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ZKPElection;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author FAYAW
 */
public class ZKPElection {

    /**
     * @param args the command line arguments
     */
    private static final SecureRandom random = new SecureRandom();

    private final ElectionAuthority electionAuthority;
    private final List<Voter> voters;

    public ZKPElection() {
        List<Candidate> candidates = new ArrayList<>();
        List<Voter> voters = new ArrayList<>();
        this.electionAuthority = new ElectionAuthority(candidates, voters);
        this.voters = new ArrayList<>();
    }

    public void addVoter(String id, BigInteger publicKey) {
        voters.add(new Voter(id, publicKey));
    }

    public boolean castVote(String voterId, BigInteger encryptedVote, BigInteger proof) {
        Voter voter = findVoter(voterId);
        if (voter == null) {
            return false;
        }
        return voter.castVote(encryptedVote, proof);
    }

    public String getWinner() {
        return electionAuthority.getTally().getWinner(electionAuthority);
    }

    private Voter findVoter(String id) {
        for (Voter voter : voters) {
            if (voter.id.equals(id)) {
                return voter;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ZKPElection election = new ZKPElection();

        // Add voters
        election.addVoter("voter1", new BigInteger("12"));
        election.addVoter("voter2", new BigInteger("24"));

        // Cast votes
        BigInteger encryptedVote1 = Encryption.encrypt(new BigInteger("1"), election.electionAuthority.getPublicKey());
        BigInteger proof1 = ZKP.generateProof(new BigInteger("1"), new BigInteger("3"), new BigInteger("7"), election.electionAuthority.getPublicKey());
        election.castVote("voter1", encryptedVote1, proof1);

        BigInteger encryptedVote2 = Encryption.encrypt(new BigInteger("2"), election.electionAuthority.getPublicKey());
        BigInteger proof2 = ZKP.generateProof(new BigInteger("2"), new BigInteger("3"), new BigInteger("7"), election.electionAuthority.getPublicKey());
        election.castVote("voter2", encryptedVote2, proof2);

        // Determine winner
        System.out.println("Winner: " + election.getWinner());
    }
}
