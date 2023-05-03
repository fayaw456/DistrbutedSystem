/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZKPElection;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author FAYAW
 */
public class Candidate {

    private static final SecureRandom random = new SecureRandom();

    private final BigInteger publicKey;
    private final BigInteger privateKey;

    public Candidate() {
        BigInteger[] keys = Encryption.generateKeys();
        publicKey = keys[0];
        privateKey = keys[1];
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }
}
