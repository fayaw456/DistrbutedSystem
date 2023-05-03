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
public class Voter {

    final String id;
    private final BigInteger publicKey;
    private final Map<BigInteger, BigInteger> castVotes;

    public Voter(String id, BigInteger publicKey) {
        this.id = id;
        this.publicKey = publicKey;
        this.castVotes = new HashMap<>();
    }

    public boolean castVote(BigInteger encryptedVote, BigInteger proof) {
        if (ZKP.verifyProof(encryptedVote, proof, publicKey)) {
            castVotes.put(encryptedVote, proof);
            return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public Map<BigInteger, BigInteger> getCastVotes() {
        return castVotes;
    }
}
