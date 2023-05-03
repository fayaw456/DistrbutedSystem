/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZKPElection;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author FAYAW
 */
public class ElectionAuthority {

    private final List<Candidate> candidates;
    private final List<Voter> voters;
    private final BigInteger q;
    private final BigInteger g;
    private final BigInteger p;
    private final BigInteger h;
    private final BigInteger f;
    private final BigInteger k;
    private final BigInteger x;
    private final BigInteger y;

    public ElectionAuthority(List<Candidate> candidates, List<Voter> voters) {
        this.candidates = candidates;
        this.voters = voters;

        // Generate random primes q and p, where p = 2q + 1
        this.q = BigInteger.probablePrime(256, new SecureRandom());
        this.p = this.q.multiply(BigInteger.valueOf(2)).add(BigInteger.ONE);

        // Generate random generator g of Z*p
        this.g = Utils.findGenerator(p);

        // Generate random private key x
        this.x = new BigInteger(p.bitLength() - 1, new SecureRandom());

        // Compute corresponding public key y
        this.y = g.modPow(x, p);

        // Compute commitment parameter h = g^x mod p
        this.h = g.modPow(x, p);

        // Generate random secret value k
        this.k = new BigInteger(p.bitLength() - 1, new SecureRandom());

        // Compute blinding factor f = g^k mod p
        this.f = g.modPow(k, p);
    }

    public void castVotes() {
        for (Voter voter : voters) {
            Map<BigInteger, BigInteger> castVotes = voter.getCastVotes();
            for (Candidate candidate : candidates) {
                if (castVotes.containsKey(candidate.getPublicKey())) {
                    BigInteger encryptedVote = castVotes.get(candidate.getPublicKey());
                    BigInteger proof = ZKP.generateProof(encryptedVote, candidate.getPublicKey(), q, g, h, k, x);
                    candidate.addVote(encryptedVote.multiply(f.modPow(candidate.getPublicKey(), p)).mod(p), proof);
                }
            }
        }
    }

    public Tally getTally() {
        Tally tally = new Tally();
        for (Candidate candidate : candidates) {
            tally.addCandidate(candidate);
        }
        castVotes();
        return tally;
    }

    public Map<String, Integer> getResults() {
        Map<String, Integer> results = new HashMap<>();
        for (Candidate candidate : candidates) {
            results.put("Candidate " + candidate.getId(), candidate.getVoteCount());
        }
        return results;
    }

    public BigInteger getPublicKey() {
        return y;
    }
}