/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZKPElection;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author FAYAW
 */
public class Tally {

    private Map<Candidate, BigInteger> votes;

    public Tally() {
        votes = new HashMap<Candidate, BigInteger>();
    }

    public void addVote(Candidate candidate, BigInteger encryptedVote) {
        if (votes.containsKey(candidate)) {
            votes.put(candidate, votes.get(candidate).multiply(encryptedVote));
        } else {
            votes.put(candidate, encryptedVote);
        }
    }

    public Map<Candidate, BigInteger> getVotes() {
        return votes;
    }

    public BigInteger computeTally(BigInteger publicKey, BigInteger q) {
        BigInteger tally = BigInteger.ONE;
        for (Map.Entry<Candidate, BigInteger> entry : votes.entrySet()) {
            Candidate candidate = entry.getKey();
            BigInteger encryptedVote = entry.getValue();
            BigInteger decryption = candidate.getEncryption().decrypt(encryptedVote);
            if (decryption.compareTo(BigInteger.ZERO) < 0) {
                decryption = decryption.add(q);
            }
            BigInteger partialTally = decryption.modPow(candidate.getPublicKey(), publicKey);
            tally = tally.multiply(partialTally).mod(publicKey);
        }
        return tally;
    }

    public String getWinner(ElectionAuthority authority) {
        BigInteger publicKey = authority.getPublicKey();
        BigInteger q = authority.getQ();
        Map<Candidate, BigInteger> votes = getVotes();
        BigInteger maxTally = BigInteger.ZERO;
        Candidate winner = null;

        for (Candidate candidate : authority.getCandidates()) {
            BigInteger partialTally = computePartialTally(candidate, publicKey, q, votes);
            if (partialTally.compareTo(maxTally) > 0) {
                maxTally = partialTally;
                winner = candidate;
            }
        }

        return winner.getName();
    }

    private BigInteger computePartialTally(Candidate candidate, BigInteger publicKey, BigInteger q, Map<Candidate, BigInteger> votes) {
        BigInteger encryptedVote = votes.getOrDefault(candidate, BigInteger.ONE);
        BigInteger decryption = candidate.getEncryption().decrypt(encryptedVote);
        if (decryption.compareTo(BigInteger.ZERO) < 0) {
            decryption = decryption.add(q);
        }
        return decryption.modPow(candidate.getPublicKey(), publicKey);
    }

    String getWinner(ElectionAuthority electionAuthority) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
