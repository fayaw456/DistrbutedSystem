/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZKPElection;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author FAYAW
 */
public class Utils {
    
    public static BigInteger hash(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input);
            return new BigInteger(hash);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static BigInteger encodeProof(BigInteger c, BigInteger s) {
        return c.shiftLeft(256).add(s);
    }
    
    public static BigInteger decodeProofC(BigInteger proof) {
        return proof.shiftRight(256);
    }
    
    public static BigInteger decodeProofS(BigInteger proof) {
        return proof.and(BigInteger.valueOf(2).pow(256).subtract(BigInteger.ONE));
    }
}