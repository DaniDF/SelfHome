package it.dani.selfhomeapp.middle.cryptography;

import androidx.annotation.IntRange;

import org.jetbrains.annotations.NotNull;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Asymmetric {

    public static @NotNull KeyPair createRSAKey(@IntRange(from = 1, to = Long.MAX_VALUE) int dim)
    {
        KeyPairGenerator keyPairGenerator = null;
        try { keyPairGenerator = KeyPairGenerator.getInstance("RSA"); }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        keyPairGenerator.initialize(dim);
        return keyPairGenerator.generateKeyPair();
    }

    public static @NotNull String privateKeyToPem(@NotNull PrivateKey privateKey)
    {
        StringBuilder result = new StringBuilder("-----BEGIN PRIVATE KEY-----\r\n");
        String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        while(privateKeyBase64.length() > 0)
        {
            result.append(privateKeyBase64.substring(0,(privateKeyBase64.length() > 64)? 64 : privateKeyBase64.length()) + "\n");
            privateKeyBase64 = privateKeyBase64.substring((privateKeyBase64.length() > 64)? 64 : privateKeyBase64.length());
        }

        result.append("-----END PRIVATE KEY-----\n");
        return result.toString();
    }

    public static @NotNull String publicKeyToPem(@NotNull PublicKey publicKey)
    {
        StringBuilder result = new StringBuilder("-----BEGIN PUBLIC KEY-----\r\n");
        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        while(publicKeyBase64.length() > 0)
        {
            result.append(publicKeyBase64.substring(0,(publicKeyBase64.length() > 64)? 64 : publicKeyBase64.length()) + "\n");
            publicKeyBase64 = publicKeyBase64.substring((publicKeyBase64.length() > 64)? 64 : publicKeyBase64.length());
        }

        result.append("-----END PUBLIC KEY-----\n");
        return result.toString();
    }

    public static @NotNull PrivateKey getPrivateKey(@NotNull String privateKeyString) throws InvalidKeySpecException
    {
        byte[] privateKeyByte = Base64.getDecoder().decode(privateKeyString.replace("-----BEGIN PRIVATE KEY-----","")
                .replaceAll(System.lineSeparator(),"")
                .replace("-----END PRIVATE KEY-----",""));
        KeyFactory keyFactory = null;
        try { keyFactory = KeyFactory.getInstance("RSA"); }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyByte));
    }

    public static @NotNull PrivateKey getPrivateKey(@NotNull File privateKeyFile) throws InvalidKeySpecException, IOException
    {
        String pkey = "";
        try(BufferedReader fileIn = new BufferedReader(new FileReader(privateKeyFile)))
        {
            String line = null;
            while((line = fileIn.readLine()) != null) pkey += line;
        }

        return Asymmetric.getPrivateKey(pkey);
    }

    public static @NotNull PublicKey getPublicKey(@NotNull String publicKeyString) throws InvalidKeySpecException
    {
        byte[] publicKeyByte = Base64.getDecoder().decode(publicKeyString.replace("-----BEGIN PUBLIC KEY-----","")
                .replaceAll(System.lineSeparator(),"")
                .replace("-----END PUBLIC KEY-----",""));
        KeyFactory keyFactory = null;
        try { keyFactory = KeyFactory.getInstance("RSA"); }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        return keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyByte));
    }

    public static @NotNull PublicKey getPublicKey(@NotNull File publicKeyFile) throws InvalidKeySpecException, IOException
    {
        String pkey = "";
        try(BufferedReader fileIn = new BufferedReader(new FileReader(publicKeyFile)))
        {
            String line = null;
            while((line = fileIn.readLine()) != null) pkey += line;
        }

        return Asymmetric.getPublicKey(pkey);
    }

    public static @NotNull byte[] encrypt(@NotNull PublicKey publicKey, @NotNull byte[] value) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        Cipher cipher = null;
        try { cipher = Cipher.getInstance("RSA"); }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) { e.printStackTrace(); }

        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        return cipher.doFinal(value);
    }

    public static @NotNull String encrypt(@NotNull PublicKey publicKey, @NotNull String value) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        return Base64.getEncoder().encodeToString(encrypt(publicKey,Base64.getDecoder().decode(value)));
    }

    public static @NotNull byte[] decrypt(@NotNull PrivateKey privateKey, @NotNull byte[] value) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        Cipher cipher = null;
        try { cipher = Cipher.getInstance("RSA"); }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) { e.printStackTrace(); }

        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        return cipher.doFinal(value);
    }

    public static @NotNull String decrypt(@NotNull PrivateKey privateKey, @NotNull String value) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        return new String(decrypt(privateKey,Base64.getDecoder().decode(value)));
    }

    public static @NotNull byte[] sign(@NotNull PrivateKey privateKey, @NotNull byte[] value) throws InvalidKeyException, SignatureException {
        Signature signature = null;
        try { signature = Signature.getInstance("SHA512withRSA"); }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        signature.initSign(privateKey);
        signature.update(value);
        return signature.sign();
    }

    public static @NotNull String sign(@NotNull PrivateKey privateKey, @NotNull String value) throws InvalidKeyException, SignatureException {
        return Base64.getEncoder().encodeToString(sign(privateKey,Base64.getDecoder().decode(value)));
    }

    public static boolean verify(@NotNull PublicKey publicKey, @NotNull byte[] sign, @NotNull byte[] value) throws InvalidKeyException, SignatureException {
        Signature signature = null;
        try { signature = Signature.getInstance("SHA512withRSA"); }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        signature.initVerify(publicKey);
        signature.update(value);
        return signature.verify(sign);
    }

    public static boolean verify(@NotNull PublicKey publicKey, @NotNull String sign, @NotNull String value) throws InvalidKeyException, SignatureException {
        return verify(publicKey,Base64.getDecoder().decode(sign), Base64.getDecoder().decode(value));
    }
}
