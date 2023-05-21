package it.dani.provacifratura;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;

public class Asymmetric {

    public static KeyPair createRSAKey(@Range(from = 1, to = Long.MAX_VALUE) int dim)
    {
        KeyPairGenerator keyPairGenerator = null;
        try { keyPairGenerator = KeyPairGenerator.getInstance("RSA"); }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        keyPairGenerator.initialize(dim);
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] encrypt(@NotNull PublicKey publicKey, @NotNull byte[] value) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        Cipher cipher = null;
        try { cipher = Cipher.getInstance("RSA"); }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) { e.printStackTrace(); }

        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        return cipher.doFinal(value);
    }

    public static String encrypt(@NotNull PublicKey publicKey, @NotNull String value) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        return Base64.getEncoder().encodeToString(encrypt(publicKey,Base64.getDecoder().decode(value)));
    }

    public static byte[] decrypt(@NotNull PrivateKey privateKey, @NotNull byte[] value) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        Cipher cipher = null;
        try { cipher = Cipher.getInstance("RSA"); }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) { e.printStackTrace(); }

        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        return cipher.doFinal(value);
    }

    public static String decrypt(@NotNull PrivateKey privateKey, @NotNull String value) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        return new String(decrypt(privateKey,Base64.getDecoder().decode(value)));
    }

    public static byte[] sign(@NotNull PrivateKey privateKey, @NotNull byte[] value) throws InvalidKeyException, SignatureException {
        Signature signature = null;
        try { signature = Signature.getInstance("SHA256withRSA"); }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        signature.initSign(privateKey);
        signature.update(value);
        return signature.sign();
    }

    public static String sign(@NotNull PrivateKey privateKey, @NotNull String value) throws InvalidKeyException, SignatureException {
        return Base64.getEncoder().encodeToString(sign(privateKey,Base64.getDecoder().decode(value)));
    }

    public static boolean verify(@NotNull PublicKey publicKey, @NotNull byte[] sign, @NotNull byte[] value) throws InvalidKeyException, SignatureException {
        Signature signature = null;
        try { signature = Signature.getInstance("SHA256withRSA"); }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        signature.initVerify(publicKey);
        signature.update(value);
        return signature.verify(sign);
    }

    public static boolean verify(@NotNull PublicKey publicKey, @NotNull String sign, @NotNull String value) throws InvalidKeyException, SignatureException {
        return verify(publicKey,Base64.getDecoder().decode(sign), Base64.getDecoder().decode(value));
    }
}
