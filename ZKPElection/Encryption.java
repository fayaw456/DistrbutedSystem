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
public class Encryption {
private BigInteger privateKey;
    private BigInteger publicKey;

    public Encryption(int keySize) {
        SecureRandom random = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(keySize, random);
        BigInteger q = BigInteger.probablePrime(keySize, random);
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        publicKey = new BigInteger("65537");
        privateKey = publicKey.modInverse(phi);
    }

    public static BigInteger encrypt(BigInteger message, BigInteger publicKey) {
        // TODO: implement encryption using the provided public key
        return message.modPow(BigInteger.valueOf(2), publicKey);
    }

    /**
     * Decrypts a message using the provided private key.
     *
     * @param encryptedMessage the encrypted message to decrypt
     * @param privateKey       the private key to use for decryption
     * @return the decrypted message
     */
    public static BigInteger decrypt(BigInteger encryptedMessage, BigInteger privateKey) {
        // TODO: implement decryption using the provided private key
        return encryptedMessage.modPow(BigInteger.valueOf(1).divide(BigInteger.valueOf(2)), privateKey);
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getN() {
        return publicKey.modInverse(getPhi());
    }

    public BigInteger getPhi() {
        return getPrimeP().subtract(BigInteger.ONE).multiply(getPrimeQ().subtract(BigInteger.ONE));
    }

    public BigInteger getPrimeP() {
        return publicKey.multiply(getPrimeQ()).add(BigInteger.ONE).divide(getPrivateKey());
    }

    public BigInteger getPrimeQ() {
        return publicKey.multiply(getPrivateKey()).add(BigInteger.ONE).divide(getPublicKey());
    }
}