/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZKPElection;

import java.math.BigInteger;
import static java.math.BigInteger.ONE;
import java.security.SecureRandom;

/**
 *
 * @author FAYAW
 */
public class ZKP {

    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger THREE = BigInteger.valueOf(3);

    public static BigInteger generateProof(BigInteger value, BigInteger p, BigInteger q, BigInteger publicKey) {
        BigInteger r = new BigInteger(publicKey.bitLength(), new SecureRandom());
        BigInteger g = p.modPow(r, publicKey);
        BigInteger h = q.modPow(r, publicKey);
        BigInteger c = hash(value, g, h);
        BigInteger s = r.add(c.multiply(privateKey)).mod(publicKey.subtract(ONE));
        return c.shiftLeft(publicKey.bitLength()).add(s);
    }

    public static boolean verifyProof(BigInteger value, BigInteger proof, BigInteger p, BigInteger q, BigInteger publicKey) {
        BigInteger c = proof.shiftRight(publicKey.bitLength());
        BigInteger s = proof.mod(publicKey.shiftLeft(1));
        BigInteger g = p.modPow(s, publicKey).multiply(publicKey.modPow(c.negate(), publicKey)).mod(publicKey);
        BigInteger h = q.modPow(s, publicKey).multiply(publicKey.modPow(c.negate().multiply(value), publicKey)).mod(publicKey);
        return hash(value, g, h).equals(c);
    }

    private static BigInteger hash(BigInteger... values) {
        BigInteger result = BigInteger.ONE;
        for (BigInteger value : values) {
            result = result.multiply(value.modPow(THREE, TWO)).mod(TWO);
        }
        return result;
    }

}
